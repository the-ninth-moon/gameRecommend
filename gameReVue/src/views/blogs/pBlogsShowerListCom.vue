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
    <div  style="margin-bottom: 50px;width: 50px;">
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
name: 'pBlogsShowerListCom',
data() {
      return {
        blogsData: [], // 文章数据
        currentPage: 1, // 当前页
        total: 0, // 总记录数
        pagesize: 12, // 页面大小
        pagesizes: [12, 24, 36], // 页面数组
        t2index: 0, // 选项卡 index
        sortType: 0, // 当前排序类型（0: 按时间, 9: 按热度）
      };
    },
props: ["tindex"],
mounted() {
    this.initIndex();
    this.currentPage = this.getContextData("currentBPage") || 1;
    this.initBlogs(); // 调用初始化博客数据
},
methods: {
      //给sessionStorage存值
    setContextData: function (key, value) {
        if (typeof value == "string") {
            sessionStorage.setItem(key, value);
        } else {
            sessionStorage.setItem(key, JSON.stringify(value));
        }
    },
    // 从sessionStorage取值
    getContextData: function (key) {
        const str = sessionStorage.getItem(key);
        if (typeof str == "string") {
            try {
                return JSON.parse(str);
            } catch (e) {
                return str;
            }
        }
        return;
    },
    initIndex() {
    this.t2index = this.tindex; // 保存父组件传过来的值
    console.log(this.t2index)
    },
    initBlogs() {
        const _this = this;
        var baseurl = '/blog/getByPage?current=' + this.currentPage + '&size=' + this.pagesize + '&processed=1&published=1&share_statement=1&is_delete=0';
        // 通过条件拼接路由
        baseurl += `&typeId=${this.t2index}`;
        console.log(baseurl)
        this.getRequest(baseurl).then(resp => {
          if (resp) {
            _this.blogsData = resp.data.records; // 将获取到的后端的值赋值给 blogsData
            _this.total = resp.data.total; // 保存一下总记录数，用于前端展示
          }
        });
      },
    handleCurrentChange(val) {
    console.log(`当前页: ${val}`);
    this.currentPage = val;
    this.setContextData("currentBPage",this.currentPage);
    this.initBlogs();
    },
    handleSizeChange(val) {
    console.log(`每页 ${val} 条`);
    this.pagesize = val;
    this.initBlogs();
    },
    formatDate(dateString) {
    const options = { year: 'numeric', month: 'long', day: 'numeric' };
    return new Date(dateString).toLocaleDateString(undefined, options);
    },
    viewBlog(id) {
    this.$router.push({ path: `/pblogs/${id}` });
    }
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
