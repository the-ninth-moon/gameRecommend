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
        <p class="blog-date">{{ blog.updateTime }}</p>
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
name: 'pMyfavorBlog',
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
  justify-content: space-between;
  margin: -1rem; /* 负边距用于调整卡片间距 */
}

.blog-card {
  width: calc(33% - 2rem); /* 默认宽度，适应桌面显示 */
  margin: 1rem;
  border-radius: 10px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  transition: transform 0.2s;
}

.blog-card:hover {
  transform: translateY(-5px);
}

.blog-cover {
  width: 100%;
  height: 200px;
  object-fit: cover;
  border-radius: 10px 10px 0 0;
}

.blog-content {
  padding: 1rem;
}

.blog-content h3 {
  font-size: 1.5rem;
  margin: 0.5rem 0;
}

.blog-date {
  color: #888;
  font-size: 0.9rem;
}

.blog-description {
  margin: 0.5rem 0;
}

/* 响应式调整，适配不同屏幕尺寸 */

/* 适应平板设备 */
@media (max-width: 1200px) {
  .blog-card {
    width: calc(50% - 2rem); /* 每行2个卡片 */
  }
}

/* 适应手机设备 */
@media (max-width: 768px) {
  .blog-card {
    width: calc(100% - 2rem); /* 每行1个卡片 */
  }
}

</style>