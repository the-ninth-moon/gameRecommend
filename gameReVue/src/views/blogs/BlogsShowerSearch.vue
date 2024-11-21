<template>
    <div>
      <!-- 博客文章 -->
      <div class="blog-card-container">
        <el-card
          v-for="(blog, index) in blogsData"
          :key="index"
          class="blog-card"
        >
          <img :src="blog.firstPicture" alt="Blog Cover" class="blog-cover" />
          <div class="blog-content">
            <h3>{{ blog.title }}</h3>
            <p class="blog-date">{{ formatDate(blog.updateTime) }}</p>
            <p class="blog-description">{{ blog.content.slice(0, 30) }}...</p>
            <el-button @click="viewBlog(blog.id)" type="primary" size="small">阅读更多</el-button>
          </div>
        </el-card>
      </div>
  
      <!-- 分页 -->
      <!-- <div v-if="!hidden_page" style="margin-top: 20px;">
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
      </div> -->
    </div>
  </template>

  <script>
  export default {
    inject:['postKeyValueRequest','postRequest','putRequest','getRequest','deleteRequest'],
    name: 'BlogsShowerSearch',
    data () {
     return {
      blogsData: [], //文章数据
  
     }
    },
    props:["blogsData"], //接收父组件传过来的值
    methods:{
        formatDate(dateString) {
        const options = { year: 'numeric', month: 'long', day: 'numeric' };
        return new Date(dateString).toLocaleDateString(undefined, options);
      },
      viewBlog(id) {
        this.$router.push({ path: `/blogs/${id}` });
      }
    }
  }
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