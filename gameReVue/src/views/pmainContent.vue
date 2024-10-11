<template>
    <div>
      <section class="hero">
        <div class="hero-overlay">
          <el-carousel indicator-position="outside" height="600px">
            <el-carousel-item v-for="(item, index) in banner" :key="index">
              <el-image :src="item" fit="cover" class="hero-image"></el-image>
            </el-carousel-item>
          </el-carousel>
          <div class="hero-content">
            <h1>S T R E A M</h1>
            <p>塞西工作室 巨献</p>
            <button @click="goToGames">开始探索</button>
          </div>
        </div>
      </section>
  
<!-- 精选游戏展示区 -->
<section class="featured-games">
    <h2>游戏推荐</h2>
    <el-carousel :interval="5000" arrow="always">
      <el-carousel-item v-for="game in gamesData" :key="game.id">
        <div class="game-card">
          <div class="game-image-wrapper">
            <img :src="game.firstPicture" alt="Game Image" class="game-image" />
          </div>
          <div class="game-info">
            <h3 class="game-title">{{ game.name }}</h3>
            <p class="game-score">⭐ Score: {{ game.score }}</p>
            <button @click="viewGame(game.id)" class="view-details-btn">了解{{game.id}}详情</button>
          </div>
        </div>
      </el-carousel-item>
    </el-carousel>
  </section>

  <!-- 最新博文展示区 -->
  <section class="latest-blogs">
    <h2>最新博客</h2>
    <el-carousel :interval="5000" arrow="always">
      <el-carousel-item v-for="blog in blogsData" :key="blog.id">
        <div class="blog-card">
          <div class="blog-image-wrapper">
            <img :src="blog.first_picture" alt="Blog Image" class="blog-image" />
          </div>
          <div class="blog-info">
            <h3 class="blog-title">{{ blog.title }}</h3>
            <p class="blog-description">{{ blog.description }}</p>
            <button @click="viewBlog(blog.id)" class="read-more-btn">阅读更多</button>
          </div>
        </div>
      </el-carousel-item>
    </el-carousel>
  </section>
    </div>
  </template>
  
  
  <script>
  export default {
    inject: ['postKeyValueRequest', 'postRequest', 'putRequest', 'getRequest', 'deleteRequest'],
    name: 'pmainContent',
    data() {
      return {
        gamesData: [],
        blogsData: [],
        currentPage: 1,
        pagesize: 4,
        total: 0,
        banner: [],
      };
    },
    mounted() {
      this.initGames();
      this.initBlogs();
      this.initBanner();
    },
    methods: {
      initGames() {
        const baseurl = `/game/getByPage?current=${this.currentPage}&size=${this.pagesize}&published=1&share_statement=1&is_delete=0`;
        this.getRequest(baseurl).then((resp) => {
          if (resp) {
            this.gamesData = resp.data.records;
            this.total = resp.data.total;
          }
        });
      },
      initBlogs() {
        const baseurl = `/blog/getByPage?current=${this.currentPage}&size=${this.pagesize}&processed=1&published=1&share_statement=1&is_delete=0`;
        this.getRequest(baseurl).then((resp) => {
          if (resp) {
            this.blogsData = resp.data.records;
            this.total = resp.data.total;
          }
        });
      },
      viewGame(id) {
        console.log(id)
        this.$router.push(`/pgames/${id}`);
      },
      viewBlog(id) {
        this.$router.push(`/pblogs/${id}`);
      },
      goToGames() {
        this.$router.push('/pgames');
      },
      async initBanner() {
        let resp = await this.getRequest(`/game-image/gameImage/${1}`);
        this.banner = resp.data;
        console.log(this.banner);
      },
    },
  };
  </script>
  
  <style scoped>
  /* 轮播图部分 */
  .hero {
    position: relative;
    overflow: hidden;
  }
  
  .hero .hero-overlay {
    position: relative;
    height: 600px; /* 设置与轮播图相同的高度 */
  }
  
  .hero .hero-image {
    width: 100%;
    height: 100%; /* 确保图片填满整个轮播图的高度 */
  }
  
  .hero .hero-content {
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    color: white;
    padding: 20px;
    text-align: center;
    z-index: 1; /* 确保文字层位于图片层之上 */
  }
  
  .featured-games {
  padding: 20px;
  background-color: #f9f9f9;
}

.latest-blogs{
    padding: 20px;
    background-color: #f9f9f9;
    margin-bottom: 5rem;
}

h2 {
  text-align: center;
  margin-bottom: 20px;
}

.game-list-wrapper, .blog-list-wrapper {
  overflow: hidden;
}

.game-card, .blog-card {
  display: flex;
  flex-direction: column;
  align-items: center;
  text-align: center;
  padding: 15px;
  background: #fff;
  border: 1px solid #e0e0e0;
  border-radius: 8px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}

.game-image-wrapper, .blog-image-wrapper {
  width: 100%;
  height: 100px;
  overflow: hidden;
  border-radius: 8px;
}

.game-image, .blog-image {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.game-title, .blog-title {
  font-size: 18px;
  font-weight: bold;
  margin: 10px 0;
}

.game-score {
  color: #FFA500; /* 橙色 */
  margin: 5px 0;
}

.view-details-btn, .read-more-btn {
  padding: 8px 16px;
  background-color: #007BFF; /* 蓝色 */
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.3s;
}

.view-details-btn:hover, .read-more-btn:hover {
  background-color: #0056b3; /* 深蓝色 */
}

  </style>
  