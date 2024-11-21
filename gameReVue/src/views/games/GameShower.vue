<template>
  <div class="game-detail">
    <h1>
  <a :href="game.officialWeb" target="_blank" style="text-decoration: none; color: inherit;">
    {{ game.name }}
  </a>
</h1>

    <img class="first-image" :src="game.firstPicture" alt="First Image" />

    <div class="game-meta">
      <span class="category">{{ getCategory(game.typeId) }}</span>
      <span class="score">评分: {{ game.score }}</span>
      <span class="developer">开发商: {{ game.developer }}</span>
      <span class="release-date">发售时间: {{ formatDate(game.sellTime) }}</span>
      <span class="platform">平台: {{ game.platform }}</span>
      <span><a v-if="game.officialWeb" :href="game.officialWeb" target="_blank" class="official-link" style="text-decoration: none; color: inherit">前去官网</a></span>
      <div class="tags">
        <span v-for="tag in game.tags" :key="tag.id" class="tag">{{ tag.name }}</span>
      </div>
    </div>

    <div class="image-gallery">
      <img v-for="(image, index) in game.images" :key="index" :src="image" alt="Game Image" />
    </div>

    <div class="game-description">
      <h2>游戏介绍</h2>
      <p>{{ game.gameContent }}</p>
    </div>

    <div class="config-requirements">
      <h2>配置信息</h2>
      <p>{{ game.configRequire }}</p>
    </div>

        <!-- 评论区 -->
        <div class="comment-section">
          <h2>用户评论</h2>

          <!-- 评论列表 -->
          <div v-if="comments.length > 0" class="comment-list">
            <div v-for="comment in comments" :key="comment.id" class="comment">
              <div class="comment-header">
                <!-- 用户头像和用户名 -->
                <div class="comment-info">
                  <img :src="comment.avatar" alt="用户头像" class="comment-avatar" />
                  <strong class="comment-nickname">{{ comment.userName }}</strong>
                </div>
                <!-- 评论内容 -->
                <p class="comment-content">{{ comment.content }}</p>
                <!-- 评论时间 -->
                <span class="comment-meta">{{ formatDate(comment.createTime) }}</span>
              </div>
            </div>
          </div>
          <div v-else class="no-comments">暂时还没有评论，快来发表第一个评论吧！</div>

          <!-- 分页 -->
          <div class="pagination" v-if="comments.length > 0">
            <el-pagination
              background
              @size-change="handleSizeChange"
              @current-change="handleCurrentChange"
              :current-page.sync="currentPage"
              :page-size="pagesize"
              layout="total, prev, pager, next"
              :total="total"
            />
          </div>

          <!-- 发表评论 -->
          <div class="post-comment">
            <textarea v-model="newComment" placeholder="输入您的评论..." rows="4"></textarea>
            <button @click="submitComment" class="submit-button">发表评论</button>
          </div>
        </div>


    <div class="action-section">
      <button @click="goBack" class="back-button">返回</button>

      <div class="like-section">
        <button @click="likeGame" :class="{ 'liked': this.likeed }" class="like-button">
          {{ this.likeed ? '已点赞' : '点赞' }}
        </button>
        <span class="like-count">总点赞数: {{ likeCount }}</span>
      </div>

      <div class="favor-section">
        <button @click="favorGame" :class="{ 'favored': this.favored }" class="favor-button">
          {{ this.favored ? '已收藏' : '收藏' }}
        </button>
        <span class="favor-count">总收藏数: {{ favorCount }}</span>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  inject: ['postKeyValueRequest', 'postRequest', 'putRequest', 'getRequest', 'deleteRequest'],
  name: 'GamesShower',
  props: ["id"],
  data() {
    return {
      game: {}, // Assume this gets populated with game data
      likeCount: 0, // 新增点赞数量
      likeed :false,
      favorCount: 0, // 新增点赞数量
      favored :false,
      newComment: '', // 用户新评论的内容
      comments: [], // 存储获取的评论列表

      currentPage: 1,  //当前页
      total:0, //总记录数
      pagesize:5, //页面大小
    };
  },
  mounted() {
      this.initComment();
    },
  async created() {
    try {
      // 获取游戏详情
      let resp = await this.getRequest('/game/getByGameId?id=' + this.id);
      this.game = resp.data.data;

      // 获取点赞数量
      let likeResp = await this.getRequest(`/likes-game/game/count?gameId=${this.id}`);
      this.likeCount = likeResp.data; // 设置点赞数量
      let uId = eval("(" + window.sessionStorage.getItem("user") + ")").data.id;
      this.tmp = await this.getRequest(`/likes-game/islike?userId=${uId}&gameId=${this.id}`);
      this.likeed = this.tmp.data;
      
      // 获取收藏数量
      let favorResp = await this.getRequest(`/favors-game/game/count?gameId=${this.id}`);
      this.favorCount = favorResp.data; // 设置点赞数量
      this.tmp2 = await this.getRequest(`/favors-game/isfavor?userId=${uId}&gameId=${this.id}`);
      this.favored = this.tmp2.data;

      // 获取评论列表
      let commentResp = await this.getRequest(`/commentGame/gameComments/${this.id}?current=` + this.currentPage + '&size=' + this.pagesize);
      console.log(commentResp)
      this.comments = commentResp.data.records;
      
    } catch (error) {
      console.error('Error fetching game details or comments:', error);
    }
  },
  methods: {
    getCategory(typeId) {
      const categories = {
        1: '角色扮演（RPG）',
        2: '射击游戏（FPS）',
        3: '即时战略（RTS）',
        4: '动作冒险（AVG）',
        5: '模拟经营（SIM）',
        6: '体育竞技（SPG）',
        7: '驾驶竞速（RAC）',
        8: '音乐艺术（MUG）',
      };
      return categories[typeId] || '未知分类';
    },
    formatDate(dateString) {
      const options = { year: 'numeric', month: 'long', day: 'numeric' };
      return new Date(dateString).toLocaleDateString(undefined, options);
    },
    goBack() {
      this.$router.go(-1);
    },
    async likeGame() {
      try {
        // 调用点赞接口
        let uId = eval("(" + window.sessionStorage.getItem("user") + ")").data.id;
        let resp = await this.postRequest(`/likes-game/game?userId=${uId}&gameId=${this.id}`);
        console.log(resp);
        if (resp.code === 200) {
          this.likeCount++; // 点赞成功，增加点赞数
          this.likeed = true;
        } else {
          this.likeCount--; // 取消成功，减少点赞数
          this.likeed = false;
        }
      } catch (error) {
        console.error('Error liking game:', error);
      }
    },
    async favorGame() {
      try {
        console.log(this.id)
        // 调用收藏接口
        let uId = eval("(" + window.sessionStorage.getItem("user") + ")").data.id;
        let resp = await this.postRequest(`/favors-game/game?userId=${uId}&gameId=${this.id}`);
        console.log(resp);
        if (resp.code === 200) {
          this.favorCount++; // 收藏成功，增加收藏数
          this.favored = true;
        } else {
          this.favorCount--; // 取消成功，减少收藏数
          this.favored = false;
        }
      } catch (error) {
        console.error('Error favoring game:', error);
      }
    },
    async submitComment() {
      if (!this.newComment.trim()) {
        alert('评论内容不能为空');
        return;
      }
      try {
        let uId = eval("(" + window.sessionStorage.getItem("user") + ")").data.id;
        //console.log(new Date().toISOString());
        let now = new Date(); // 获取当前日期时间
        let offset = 8 * 60; // GMT+8的偏移量，单位为分钟
        now.setTime(now.getTime() + offset * 60 * 1000); // 转换到GMT+8时间

        let year = now.getFullYear();
        let month = String(now.getMonth() + 1).padStart(2, '0'); // 月份从0开始，所以加1
        let day = String(now.getDate()).padStart(2, '0');
        let hours = String(now.getHours()).padStart(2, '0');
        let minutes = String(now.getMinutes()).padStart(2, '0');
        let seconds = String(now.getSeconds()).padStart(2, '0');

        let formattedTime = `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;

        // 发送请求
        let resp = await this.postRequest(`/commentGame/comments`, {
          userId: uId,
          gameId: this.id,
          content: this.newComment,
          create_time: formattedTime,
        });
        console.log(resp);
        if (resp.code === 200) {
          this.comments = resp.data.records; // 更新评论列表
          this.total = resp.data.total;
          this.newComment = ''; // 清空输入框
        }
      } catch (error) {
        console.error('Error submitting comment:', error);
      }
    },
    async initComment(){ 
      // 获取评论列表
      let commentResp = await this.getRequest(`/commentGame/gameComments/${this.id}?current=` + this.currentPage + '&size=' + this.pagesize);
      //console.log(commentResp)
      this.comments = commentResp.data.records;
      this.total = commentResp.data.total
    },
    handleSizeChange(val) {
        console.log(`每页 ${val} 条`);
      },
      handleCurrentChange(val) {
        console.log(`当前页: ${val}`);
        this.currentPage = val
        this.initComment()
      }
  }
};
</script>


<style scoped>
.game-detail {
  padding: 2rem;
  max-width: 900px;
  margin: auto;
  background: linear-gradient(135deg, #f9f9f9, #e0e0e0);
  border-radius: 15px;
  box-shadow: 0 10px 20px rgba(0, 0, 0, 0.15);
}

h1 {
  font-size: 2.8rem;
  color: #222;
  margin-bottom: 1rem;
  text-align: center;
}

.first-image {
  border-radius: 15px;
  margin-bottom: 1.5rem;
  height:100%;
  width: 100%;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
}

.game-meta {
  display: flex;
  flex-wrap: wrap;
  margin-bottom: 1rem;
  gap: 1rem;
  justify-content: center;
}

.category, .score, .developer, .release-date, .platform ,.official-link{
  font-weight: bold;
  background-color: rgba(0, 123, 255, 0.1);
  padding: 0.3rem 0.6rem;
  border-radius: 5px;
}


.tags {
  display: flex;
  gap: 0.5rem;
}

.tag {
  background-color: #007bff;
  color: white;
  padding: 0.4rem 0.8rem;
  border-radius: 20px;
}

.image-gallery {
  display: flex;
  flex-wrap: wrap;
  gap: 1rem;
  margin: 1.5rem 0;
}

.image-gallery img {
  width: calc(33% - 1rem);
  border-radius: 10px;
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
}

.game-description, .config-requirements {
  margin-bottom: 1.5rem;
  background-color: #fff;
  padding: 1rem;
  border-radius: 10px;
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
}

h2 {
  font-size: 1.8rem;
  margin-bottom: 0.5rem;
}

.action-section {
  display: flex;
  justify-content: space-around; /* 平均分配空间 */
  align-items: center; /* 垂直居中 */
  margin-top: 20px; /* 上方间距 */
  padding: 1rem;
  background-color: #fff; /* 背景色 */
  border-radius: 10px; /* 圆角 */
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1); /* 阴影效果 */
}

.back-button {
  background-color: #007bff;
  color: white;
  padding: 0.5rem 1rem;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s;
}

.back-button:hover {
  background-color: #0056b3;
}

.like-section, .favor-section {
  display: flex;
  align-items: center; /* 垂直居中 */
}

.like-button, .favor-button {
  background-color: #4CAF50;
  color: white;
  border: none;
  padding: 10px 20px;
  cursor: pointer;
  transition: background-color 0.3s;
}

.like-button:hover, .favor-button:hover {
  background-color: #45a049; /* 悬停状态 */
}

.like-count, .favor-count {
  margin-left: 10px;
  font-weight: bold;
}

.liked, .favored {
  color: red; /* 点赞或收藏时的颜色 */
}

.comment-section {
  padding: 2rem;
  background-color: #f9f9f9;
  border-radius: 10px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  max-width: 800px;
  margin: 20px auto;
}

.comment-list {
  margin-bottom: 1.5rem;
}
.comment-list {
  display: flex;
  flex-direction: column;
  gap: 16px; /* 每条评论之间的间距 */
}

.comment-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.comment-info {
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100px; /* 固定头像和用户名的宽度 */
}

.comment-avatar {
  width: 60px;
  height: 60px;
  border-radius: 50%; /* 圆形头像 */
  object-fit: cover; /* 确保头像以合适的比例显示 */
}

.comment-nickname {
  margin-top: 8px;
  font-weight: bold;
  text-align: center; /* 用户名居中 */
}

.comment-content {
  flex: 1;
  margin-left: 16px; /* 评论与头像之间的间距 */
}

.comment-meta {
  white-space: nowrap;
  margin-left: 16px; /* 确保时间显示在右侧 */
}

.comment {
  padding: 1rem;
  background-color: white;
  border-radius: 8px;
  margin-bottom: 1rem;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.comment-header {
  display: flex;
  justify-content: space-between;
  margin-bottom: 0.5rem;
}

.comment-nickname {
  font-weight: bold;
  color: #007bff;
  margin: 10px;;
}

.comment-meta {
  color: #666;
  font-size: 0.875rem;
}

.comment-content {
  color: #333;
  line-height: 1.5;
}

.no-comments {
  text-align: center;
  font-size: 1.2rem;
  color: #666;
}

.post-comment {
  margin-top: 2rem;
}

textarea {
  width: 100%;
  padding: 1rem;
  border-radius: 8px;
  border: 1px solid #ccc;
  margin-bottom: 1rem;
}

.submit-button {
  background-color: #007bff;
  color: white;
  border: none;
  padding: 0.75rem 1.5rem;
  border-radius: 8px;
  cursor: pointer;
  transition: background-color 0.3s;
}

.submit-button:hover {
  background-color: #0056b3;
}

.pagination {
  display: flex;
  justify-content: center;
  margin-top: 2rem;
}

</style>