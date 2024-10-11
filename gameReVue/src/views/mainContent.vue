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
    <div class="game-list">
      <div v-for="game in gamesData" :key="game.id" class="game-card">
        <div class="game-image-wrapper">
          <img :src="game.firstPicture" alt="Game Image" class="game-image" />
        </div>
        <div class="game-info">
          <h3 class="game-title">{{ game.name }}</h3>
          <p class="game-score">⭐ Score: {{ game.score }}</p>
          <button @click="viewGame(game.id)" class="view-details-btn">了解详情</button>
        </div>
      </div>
    </div>
  </section>
  
  
      <!-- 最新博文展示区 -->
      <section class="latest-blogs">
    <h2>最新博客</h2>
    <div class="blog-list">
      <div v-for="blog in blogsData" :key="blog.id" class="blog-card">
        <div class="blog-image-wrapper">
          <img :src="blog.first_picture" alt="Blog Image" class="blog-image" />
        </div>
        <div class="blog-info">
          <h3 class="blog-title">{{ blog.title }}</h3>
          <p class="blog-description">{{ blog.description }}</p>
          <button @click="viewBlog(blog.id)" class="read-more-btn">阅读更多</button>
        </div>
      </div>
    </div>
  </section>
  
    </div>
  </template>
  
  <script>
  export default {
    inject:['postKeyValueRequest','postRequest','putRequest','getRequest','deleteRequest'],
    name: 'mainContent',
    data() {
      return {
        gamesData: [],
        blogsData: [],
        currentPage: 1,
        pagesize: 4,
        total: 0,
        banner:[],
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
        });
      },
      viewGame(id) {
        this.$router.push(`/games/${id}`);
      },
      viewBlog(id) {
        this.$router.push(`/blogs/${id}`);
      },
      goToGames() {
        this.$router.push('/games');
      },
      async initBanner(){
        let resp = await this.getRequest(`/game-image/gameImage/${1}`);
        this.banner = resp.data;
        console.log(this.banner)
      },
    }
  };
  </script>
  
<style>
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

.hero .hero-content h1 {
  margin: 0 0 10px;
}

.hero .hero-content p {
  margin-bottom: 20px;
}
  .hero button {
    background-color: rgba(122, 62, 118, 0.5);
    color: white;
    padding: 10px 20px;
    border: none;
    cursor: pointer;
    border-radius: 30%;
  }
  
  /* 游戏和博客展示区 */
  .game-list, .blog-list {
    display: flex;
    justify-content: space-around;
    flex-wrap: wrap;
  }
  
  .game-card, .blog-card {
    width: 300px;
    margin: 20px;
    text-align: center;
  }
  
  .game-card img, .blog-card img {
    width: 100%;
    height: auto;
  }
  
  footer {
    text-align: center;
    padding: 20px;
    background-color: #333;
    color: white;
  }
  
  .social-links img {
    width: 30px;
    margin: 0 10px;
  }
  
  .featured-games {
    margin: 0 auto;
    padding: 40px 20px;
    text-align: center;
    background-color: #f4f4f9;
  }
  
  .featured-games h2 {
    font-size: 2.5rem;
    margin-bottom: 20px;
    color: #333;
  }
  
  .game-list {
    display: grid;
    grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
    gap: 20px;
  }
  
  .game-card {
    background-color: #fff;
    border-radius: 10px;
    overflow: hidden;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    transition: transform 0.3s ease, box-shadow 0.3s ease;
    text-align: left;
  }
  
  .game-card:hover {
    transform: translateY(-10px);
    box-shadow: 0 10px 20px rgba(0, 0, 0, 0.15);
  }
  
  .game-image-wrapper {
    height: 180px;
    overflow: hidden;
  }
  
  .game-image {
    width: 100%;
    height: 100%;
    object-fit: cover;
    transition: transform 0.3s ease;
  }
  
  .game-card:hover .game-image {
    transform: scale(1.1);
  }
  
  .game-info {
    padding: 20px;
  }
  
  .game-title {
    font-size: 1.5rem;
    margin-bottom: 10px;
    color: #333;
  }
  
  .game-score {
    font-size: 1rem;
    color: #666;
  }
  
  .view-details-btn {
    display: inline-block;
    margin-top: 15px;
    padding: 10px 20px;
    background-color: #42b983;
    color: #fff;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    font-size: 1rem;
    transition: background-color 0.3s ease;
  }
  
  .view-details-btn:hover {
    background-color: #2a7859;
  }
  .latest-blogs {
    margin: 0 auto;
    padding: 40px 20px;
    text-align: center;
    background-color: #f4f4f9;
  }
  
  .latest-blogs h2 {
    font-size: 2.5rem;
    margin-bottom: 20px;
    color: #333;
  }
  
  .blog-list {
    display: grid;
    grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
    gap: 20px;
  }
  
  .blog-card {
    background-color: #fff;
    border-radius: 10px;
    overflow: hidden;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    transition: transform 0.3s ease, box-shadow 0.3s ease;
    text-align: left;
  }
  
  .blog-card:hover {
    transform: translateY(-10px);
    box-shadow: 0 10px 20px rgba(0, 0, 0, 0.15);
  }
  
  .blog-image-wrapper {
    height: 180px;
    overflow: hidden;
  }
  
  .blog-image {
    width: 100%;
    height: 100%;
    object-fit: cover;
    transition: transform 0.3s ease;
  }
  
  .blog-card:hover .blog-image {
    transform: scale(1.1);
  }
  
  .blog-info {
    padding: 20px;
  }
  
  .blog-title {
    font-size: 1.5rem;
    margin-bottom: 10px;
    color: #333;
  }
  
  .blog-description {
    font-size: 1rem;
    color: #666;
    margin-bottom: 15px;
  }
  
  .read-more-btn {
    display: inline-block;
    padding: 10px 20px;
    background-color: #42b983;
    color: #fff;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    font-size: 1rem;
    transition: background-color 0.3s ease;
  }
  
  .read-more-btn:hover {
    background-color: #2a7859;
  }
  
  </style>
  