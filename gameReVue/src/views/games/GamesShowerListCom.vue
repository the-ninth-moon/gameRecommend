<template>
  <div>
      <!-- 游戏文章 -->
      <div class="game-cards-container">
          <div v-for="(game, index) in gamesData" :key="index" class="game-card">
              <el-card :body-style="{ padding: '10px' }" class="box-card">
                  <div class="image-container">
                      <el-image :src="game.firstPicture" fit="cover" style="width: 100%; height: 150px;"></el-image>
                  </div>
                  <div class="game-info">
                      <el-text size="medium" class="game-name" style="font-weight:bold;">{{ game.name }}</el-text>
                      <div class="tags">
                          <el-tag v-for="tag in game.tags" :key="tag" size="small">{{ tag }}</el-tag>
                      </div>
                      <div class="meta-info">
                          <el-icon><Calendar /></el-icon>
                          <span style="margin-left: 5px;">{{ game.sellTime }}</span>
                      </div>
                      <router-link :to=" '/games/' + game.id ">
                      <el-button type="primary">阅读更多</el-button>
                    </router-link>
                  </div>
              </el-card>
          </div>
      </div>

      <!-- 分页 -->
      <div v-if="!hidden_page" style="margin-top: 20px;">
          <el-pagination
              background
              @size-change="handleSizeChange"
              @current-change="handleCurrentChange"
              :current-page="currentPage"
              :page-sizes="pagesizes"
              :page-size="pagesize"
              layout="total, sizes, prev, pager, next, jumper"
              :total="total">
          </el-pagination>
      </div>
  </div>
</template>

<script>
export default {
  inject:['postKeyValueRequest','postRequest','putRequest','getRequest','deleteRequest'],
  name: 'GameListCom',
  data () {
      return {
          gamesData: [], // 文章数据
          currentPage: 1,  // 当前页
          total: 0,       // 总记录数
          pagesize: 12,   // 页面大小
          pagesizes: [12, 24, 36], // 页面数组
          t2index: 0,     // 选项卡index
          fit: 'cover'    // 图片填充方式
      }
  },
  props: ["tindex"],
  mounted() {
      this.initIndex();
      this.initGames(); // 调用初始化游戏数据
  },
  methods: {
      initIndex(){
          this.t2index = this.tindex; // 保存父组件传过来的值
      },
      initGames(){
          const _this = this;
          // 通用路由
          let baseurl = `/game/getByPage?current=${this.currentPage}&size=${this.pagesize}&published=1&share_statement=1&is_delete=0`;
          // 通过条件拼接路由
          if(this.t2index !== "0"){ // 全部以外的情况
              baseurl += `&typeId=${this.t2index}`;
          }
          console.log(baseurl);
          this.getRequest(baseurl).then(resp => {
              if(resp){
                  _this.gamesData = resp.data.records; // 将获取到的后端的值赋值给gamesData
                  _this.total = resp.data.total;       // 保存一下总记录数，用于前端展示
              }
          });
      },
      handleCurrentChange(val) {
          this.currentPage = val;
          this.initGames();
      },
      handleSizeChange(val) {
          this.pagesize = val;
          this.initGames();
      },
      viewMore(game) {
          // 这里可以跳转到游戏详情页或执行其他操作
          console.log(`View more details for ${game.name}`);
      }
  }
}
</script>

<style scoped>
.game-cards-container {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
  justify-content: center;
}
.game-card {
  flex: 0 0 calc(25% - 10px); /* 每个卡片占25%，减去左右边距 */
  max-width: calc(25% - 10px); /* 最大宽度 */
  margin-bottom: 10px;
}
.box-card {
  display: flex;
  flex-direction: column;
  height: 100%;
}
.image-container {
  flex: 1;
  overflow: hidden;
}
.game-info {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  flex-grow: 1; /* 让信息区充满卡片剩余空间 */
  padding: 10px 0;
}
.game-name {
  margin-bottom: 5px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}
.tags {
  margin-bottom: 5px;
}
.meta-info {
  display: flex;
  align-items: center;
  margin-bottom: 5px;
}
.meta-info span {
  margin-left: 5px;
}
</style>