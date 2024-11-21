<template>
    <div>
      <!-- 博客文章 -->
      <div class="blog-card-container">
        <el-card
          v-for="(blog, index) in blogsData"
          :key="index"
          class="blog-card"
        >
          <img :src="blog.data.firstPicture" alt="Blog Cover" class="blog-cover" />
          <div class="blog-content">
            <h3>{{ blog.data.title }}</h3>
            <p class="blog-date">{{ formatDate(blog.updateTime) }}</p>
            <p class="blog-description">{{ blog.data.content.slice(0, 30) }}...</p>
            <el-button @click="viewBlog(blog.data.id)" type="primary" size="small">阅读更多</el-button>
          </div>
        </el-card>
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
          :total="total"
        ></el-pagination>
      </div>
    </div>
  </template>
  
  <script>
  export default {
    inject: ['postKeyValueRequest', 'postRequest', 'putRequest', 'getRequest', 'deleteRequest'],
    name: 'MyfavorBlog',
    props: ["id"],
    data() {
      return {
        blogsData: [], // 文章数据
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
            let favorResp = await this.getRequest(`/favors-blog/getfavor?userId=${uId}`);
            this.favorList = favorResp.data;

            // 遍历favorList，逐个获取游戏信息
            for (let blogId of this.favorList) {
                let response = await this.getRequest(`/blog/getByBlogId?id=${blogId}`);
                console.log(response)
                this.blogsData.push(response.data); // 将每个游戏信息逐一添加到 gamesData
            }

            // 设置总记录数
            this.total = this.blogsData.length;


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
.blog-card-container {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
  justify-content: center;
}
.blog-card {
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
.blog-info {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  flex-grow: 1; /* 让信息区充满卡片剩余空间 */
  padding: 10px 0;
}
.blog-name {
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