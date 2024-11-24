<template>
  <div>
    <!-- 顶部导航栏 -->
    <header>
      <nav class="navbar">
        <div class="logo">S T R E A M</div>
        <ul class="nav-links">
          <RouterLink to="/home">首页</RouterLink>
          <!-- 游戏按钮 -->
          <button class="butt" @click="navigateTo('/games')">游戏</button>

          <!-- 推文按钮 -->
          <button class="butt" @click="navigateTo('/blogs')">推文</button>
          <RouterLink to="/about">关于我们</RouterLink>
          <RouterLink v-if="!isLoggedIn" to="/login">登录</RouterLink>
          <RouterLink v-else :to="userCenterLink">{{ userCenterText }}</RouterLink>
        </ul>
      </nav>
    </header>
    <RouterView></RouterView>

    <!-- 页脚 -->
    <footer>
      <p>&copy; 2024 GameBlog. All Rights Reserved.</p>
    </footer>
  </div>
</template>

<script>
import { RouterLink } from 'vue-router'
import { RouterView } from 'vue-router';
import mainContent from '@/views/mainContent.vue';
import eventBus from '@/eventBus';
export default {
  inject:['postKeyValueRequest','postRequest','putRequest','getRequest','deleteRequest'],
  name: 'homepage',
  components: { mainContent },
  data() {
    return {
      gamesData: [],
      blogsData: [],
      currentPage: 1,
      pagesize: 4,
      total: 0,
      // 用于保存用户数据
      user: null
    };
  },
  computed: {
    // 动态判断用户是否登录
    isLoggedIn() {
      return !!this.user; // 如果 user 不为空，表示已经登录
    },
    // 根据角色动态设置个人中心的链接
    userCenterLink() {
      if (!this.user) return '/login'; // 未登录
      if (this.user.role === 1) return '/admin/home'; // 管理员中心
      if (this.user.role === 2) return '/writer/home'; // 作者中心
      return '/user/home'; // 普通用户中心
    },
    // 根据角色动态设置显示文本
    userCenterText() {
      if (!this.user) return '登录';
      if (this.user.role === 1) return '管理员中心';
      if (this.user.role === 2) return '作者中心';
      return '用户中心';
    }
  },
  created() {
  // 检查是否已经登录
  const storedUser = window.sessionStorage.getItem("user");
    if (storedUser) {
      this.user = JSON.parse(storedUser).data;
    }

    // 监听登录事件
    eventBus.on('login', (user) => {
      this.user = user.data; // 更新用户信息
    });

    // 监听注销事件
    eventBus.on('logout', () => {
      this.user = null; // 清除用户信息
    });
  },
  mounted() {
    if(this.isMobile()){
        this.$router.replace("phome")
      }
    this.initGames();
    this.initBlogs();
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
    navigateTo(path) {
      this.$router.push(path);
      this.setContextData("currentPage",1);
      this.setContextData("currentBPage",1);
    },
    isMobile() {
        this.flag = navigator.userAgent.match(
  /(phone|pad|pod|iPhone|iPod|ios|iPad|Android|Mobile|BlackBerry|IEMobile|MQQBrowser|JUC|Fennec|wOSBrowser|BrowserNG|WebOS|Symbian|Windows Phone)/i
        );
        return this.flag;
      },
     // 从 sessionStorage 获取用户信息
     loadUser() {
      const userData = window.sessionStorage.getItem('user');
      if (userData) {
        this.user = JSON.parse(userData).data; // 将字符串解析为对象
      } else {
        this.user = null;
      }
    },
    initGames() {
      const baseurl = `/game/getByPage?current=${this.currentPage}&size=${this.pagesize}&published=1&share_statement=1&is_delete=0`;
      this.getRequest(baseurl).then(resp => {
        if (resp) {
          this.gamesData = resp.data.records;
          this.total = resp.data.total;
        }
       
      });
    },
    initBlogs() {
      const baseurl = `/blog/getByPage?current=${this.currentPage}&size=${this.pagesize}&processed=1&published=1&share_statement=1&is_delete=0`;
      this.getRequest(baseurl).then(resp => {
        if (resp) {
          this.blogsData = resp.data.records;
          this.total = resp.data.total;
        }
        //console.log(resp)
      });
    },
    viewGame(id) {
      this.$router.push(`/game/${id}`);
    },
    viewBlog(id) {
      this.$router.push(`/blog/${id}`);
    },
    goToGames() {
      this.$router.push('/games');
    }
  }
};
</script>

<style>
/* 全局导航栏样式 */
.navbar {
  background-color: #2c3e50; /* 导航栏背景颜色 */
  display: flex;
  justify-content: space-between; /* 使Logo靠左，导航链接靠右 */
  align-items: center;
  padding: 10px 20px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  font-size: 1.8rem;
  color: #ecf0f1; /* 文字颜色 */
  font-weight: bold;
  text-transform: uppercase;
  cursor: pointer;
}

/* Logo 样式 */
.logo {
  font-size: 1.8rem;
  color: #ecf0f1; /* 文字颜色 */
  font-weight: bold;
  text-transform: uppercase;
  cursor: pointer;
  margin-right: 30%;
  width: 30%;
}

/* 导航链接容器样式 */
.nav-links {
  width:100px;
}

/* 每个导航链接样式 */
.nav-links a {
  color: #ecf0f1;
  font-size: 1rem;
  text-decoration: none;
  padding: 10px 15px;
  border-radius: 5px;
  transition: background-color 0.3s ease, color 0.3s ease;
}
.butt{
  color: #ecf0f1;
  font-size: 1rem;
  text-decoration: none;
  font-weight: bold;
  padding: 10px 15px;
  text-transform: uppercase;
  cursor: pointer;
  transition: background-color 0.3s ease, color 0.3s ease;
  background-color:  #2c3e50;
  color: #fff;
  border: None;
  border-radius: 5px;
}

.butt:hover{
  background-color: #3498db;
  color: #fff;
}

/* 悬停时的导航链接样式 */
.nav-links a:hover {
  background-color: #3498db;
  color: #fff;
}

/* 路由视图容器 */
.router-view-container {
  padding: 2rem;
}


</style>
