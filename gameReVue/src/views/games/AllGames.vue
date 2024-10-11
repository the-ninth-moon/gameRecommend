<template>
    <div>
      <el-tabs v-model="activeName" @tab-click="handleClick" style="margin-top: 10px;">
        <el-tab-pane label="全部" for="zero" name="zero">
            <!-- 搜索 -->
        <el-input size="small" v-model="input_title" placeholder="请输入标题，可回车搜索..." prefix-icon="el-icon-search"
        style="width: 400px;margin-right: 10px;" @keydown.enter.native="search_title"></el-input>
        <el-button size="small" type="primary"  @click="search_title" icon="el-icon-search">搜索</el-button>
           <!-- 通用游戏列表组件 -->
            <GameListCom :tindex="tabindex" v-if="tabindex == 0 && hidden_searchcom == false"></GameListCom>
            <!-- 搜索专用组件 -->
            <SearchGameCom v-if="tabindex == 0 && hidden_searchcom == true" :gamesData="gamesData"></SearchGameCom>
        </el-tab-pane>



      <el-tab-pane label="草稿" name="first">
        <GameListCom :tindex="tabindex" v-if="tabindex == 1"></GameListCom>
      </el-tab-pane>
      <el-tab-pane label="公开" name="second">
        <GameListCom :tindex="tabindex" v-if="tabindex == 2"></GameListCom>
      </el-tab-pane>
      <el-tab-pane label="私密" name="third">
        <GameListCom :tindex="tabindex" v-if="tabindex == 3"></GameListCom>
      </el-tab-pane>
      <el-tab-pane label="回收站" name="fourth">
        <GameListCom :tindex="tabindex" v-if="tabindex == 4"></GameListCom>
      </el-tab-pane>

      </el-tabs>
  
      
   
    </div>
  </template>
  
  <script>
  import GameListCom from '@/views/games/GameListCom.vue';
  import SearchGameCom from '@/views/games/SearchGameCom.vue';
  export default {
    inject:['postKeyValueRequest','postRequest','putRequest','getRequest','deleteRequest'],
    name: 'AllGames',
    components:{GameListCom,SearchGameCom},
    data () {
   return {
    activeName: 'zero',
    input_title: '', //输入框的值
    //向下传递给子组件
    gamesData: [], //文章数据
   
    tabindex:0, //选项卡index
    
    hidden_searchcom: false , //是否隐藏搜索子组件

    }
  },
  mounted() {
    
    },
    methods:{
    // 获取标签栏的index,就是原创，转载什么的
    handleClick(tab, event) {
        this.tabindex = tab.index
        //console.log("tabindex = " + this.tabindex);
    },
    //根据标题搜索
    search_title(){
      //判断使用的是通用游戏子组件还是搜索专用子组件
      if(this.input_title == ''){
        //如果搜索值为空，就隐藏搜索专用子组件，显示通用子组件
        this.hidden_searchcom = false
      }else{
        //如果搜索值不为空，就显示搜索专用子组件
        this.hidden_searchcom = true
      }
      const _this = this
      //发起根据标题搜索请求
      this.getRequest('/game/getByTitle?title=' + this.input_title).then(resp=>{
        if(resp){
          _this.gamesData = resp.data
        }
      
      })
    }

  }
  }
  </script>
  
  <style scoped>
  
  </style>