<template>
    <div>
     <header>
      <nav class="navbar">
        <div class="logo">S T R E A M</div>
      </nav>
    </header>

      <!-- 顶部区域显示主要内容 -->
      <RouterView></RouterView>
    
      <!-- 底部固定导航栏 -->
      <footer class="bottom-nav">
        <ul class="nav-links">
          <li><RouterLink to="/phome"><el-icon><i class="el-icon-house"></i></el-icon><span>首页</span></RouterLink></li>
          <li><RouterLink to="/pgames"><el-icon><i class="el-icon-gamepad"></i></el-icon><span>游戏</span></RouterLink></li>
          <li><RouterLink to="/pblogs"><el-icon><i class="el-icon-edit-outline"></i></el-icon><span>推文</span></RouterLink></li>
          <li>
            <RouterLink v-if="!isLoggedIn" to="/plogin">
              <el-icon><i class="el-icon-user"></i></el-icon><span>登录</span>
            </RouterLink>
            <RouterLink v-else :to="userCenterLink">
              <el-icon><i class="el-icon-user-solid"></i></el-icon><span>{{ userCenterText }}</span>
            </RouterLink>
          </li>
        </ul>
      </footer>
    </div>
  </template>
  
  <script>
  import { RouterLink } from 'vue-router'
  import { RouterView } from 'vue-router';
  import pmainContent from '@/views/pmainContent.vue';
  import eventBus from '@/eventBus';
  export default {
    inject:['postKeyValueRequest','postRequest','putRequest','getRequest','deleteRequest'],
    name: 'phomepage',
    components: { pmainContent },
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
        // if (!this.user) return '/login'; // 未登录
        // if (this.user.role === 1) return '/admin/home'; // 管理员中心
        // if (this.user.role === 2) return '/writer/home'; // 作者中心
        return '/puser/home'; // 普通用户中心
      },
      // 根据角色动态设置显示文本
      userCenterText() {
        // if (!this.user) return '登录';
        // if (this.user.role === 1) return '管理员中心';
        // if (this.user.role === 2) return '作者中心';
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
      if(!this.isMobile()){
        this.$router.replace("home")
      }
  
      this.initGames();
      this.initBlogs();
    },
    methods: {
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
/* 底部导航栏样式 */
.bottom-nav {
  position: fixed;
  bottom: 0;
  left: 0;
  width: 100%;
  height: 60px; /* 固定高度，确保导航栏大小一致 */
  background-color: #2c3e50;
  box-shadow: 0 -2px 10px rgba(0, 0, 0, 0.2);
  z-index: 1000;
  display: flex;
  justify-content: space-around;
  align-items: center; /* 确保图标和文本居中对齐 */
  padding: 0; /* 清除内边距 */
  box-sizing: border-box;
}

/* 导航链接容器 */
.nav-links {
  display: flex;
  justify-content: space-around;
  align-items: center;
  padding: 0;
  margin: 0;
  list-style: none;
  width: 100%; /* 铺满整个宽度 */
}

/* 导航项样式 */
.nav-links li {
  text-align: center;
  flex-grow: 1; /* 保证每个导航项等宽 */
}

/* 导航链接样式 */
.nav-links a {
  color: #ecf0f1;
  font-size: 0.9rem;
  text-decoration: none;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 100%; /* 链接高度占满整个导航栏 */
  padding-top: 5px; /* 调整图标与文字的间距 */
}

/* 悬停效果 */
.nav-links a:hover {
  color: #3498db;
}

/* 图标样式 */
.el-icon {
  font-size: 1.5rem;
  margin-bottom: 5px; /* 图标与文字之间的间距 */
}
/* Logo 样式 */
.logo {
  font-size: 1.8rem;
  color: #ecf0f1; /* 文字颜色 */
  font-weight: bold;
  text-transform: uppercase;
  cursor: pointer;
  width: 50%;
}
  </style>