<template>
<div>
    <!-- 游戏文章 -->
    <div class="game-cards-container">
        <div v-for="(game, index) in gamesData" :key="index" class="game-card">
            <el-card :body-style="{ padding: '10px' }" class="box-card">
                <div class="image-container">
                    <el-image :src="game.data.firstPicture" fit="cover" style="width: 100%; height: 150px;"></el-image>
                </div>
                <div class="game-info">
                    <el-text size="medium" class="game-name" style="font-weight:bold;">{{ game.data.name }}</el-text>
                    <div class="tags">
                        <el-tag v-for="tag in game.data.tags" :key="tag" size="small">{{ tag }}</el-tag>
                    </div>
                    <div class="meta-info">
                        <el-icon><Calendar /></el-icon>
                        <span style="margin-left: 5px;">{{ game.data.sellTime }}</span>
                    </div>
                    <router-link :to=" '/pgames/' + game.data.id ">
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
    inject: ['postKeyValueRequest', 'postRequest', 'putRequest', 'getRequest', 'deleteRequest'],
    name: 'pMyfavorGame',
    props: ["id"],
    data() {
    return {
        gamesData: [], // 文章数据
        currentPage: 1,  //当前页
        total:0, //总记录数
        pagesize:5, //页面大小
    };
    },
    mounted() {
    },
    async created() {
        try {
            // 从 sessionStorage 获取用户ID
            let uId = eval("(" + window.sessionStorage.getItem("user") + ")").data.id;

            // 获取收藏游戏的ID列表
            let favorResp = await this.getRequest(`/favors-game/getfavor?userId=${uId}`);
            this.favorList = favorResp.data;

            // 遍历favorList，逐个获取游戏信息
            for (let gameId of this.favorList) {
                let response = await this.getRequest(`/game/getByGameId?id=${gameId}`);
                console.log(response)
                this.gamesData.push(response.data); // 将每个游戏信息逐一添加到 gamesData
            }

            // 设置总记录数
            this.total = this.gamesData.length;

            console.log(this.gamesData)

        } catch (error) {
            console.error('Error fetching game details or comments:', error);
        }
        },
    methods: {
    formatDate(dateString) {
        const options = { year: 'numeric', month: 'long', day: 'numeric' };
        return new Date(dateString).toLocaleDateString(undefined, options);
    },
    goBack() {
        this.$router.go(-1);
    },

    }
};
</script>


<style scoped>
.game-cards-container {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
  justify-content: center;
}

.game-card {
  flex: 0 0 calc(25% - 10px); /* 默认每行4个卡片 */
  max-width: calc(25% - 10px);
  margin-bottom: 10px;
}

/* 适应手机浏览 */
@media (max-width: 1200px) {
  .game-card {
    flex: 0 0 calc(33.33% - 10px); /* 屏幕较窄时每行显示3个 */
    max-width: calc(33.33% - 10px);
  }
}

@media (max-width: 768px) {
  .game-card {
    flex: 0 0 calc(50% - 10px); /* 平板设备每行显示2个 */
    max-width: calc(50% - 10px);
  }
}

@media (max-width: 480px) {
  .game-card {
    flex: 0 0 100%; /* 手机端每行显示1个 */
    max-width: 100%;
  }
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
  flex-grow: 1;
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