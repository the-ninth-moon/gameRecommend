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
    name: 'BlogsShowerListCom',
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
        this.$router.push({ path: `/blogs/${id}` });
      }
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
  