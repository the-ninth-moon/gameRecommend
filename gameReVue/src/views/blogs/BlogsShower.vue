<template>
    <div>
      <el-tabs v-model="activeName" @tab-click="handleClick" style="margin-top: 10px;">
        
        <el-tab-pane label="全部" name="zero">
            <!-- 搜索 -->
        <el-input size="small" v-model="input_title" placeholder="请输入标题，可回车搜索..." prefix-icon="el-icon-search"
        style="width: 400px;margin-right: 10px;" @keydown.enter.native="search_title"></el-input>
        <el-button size="small" type="primary"  @click="search_title" icon="el-icon-search">搜索</el-button>

        <!-- 排序按钮 -->
        <el-button
          size="small"
          :type="sortType === 0 ? 'primary' : 'default'"
          @click="sortGames(0)"
        >
          按时间排序
        </el-button>
        <el-button
          size="small"
          :type="sortType === 9 ? 'primary' : 'default'"
          @click="sortGames(9)"
        >
          按热度排序
        </el-button>
          <!-- 通用博客列表组件 -->
          <BlogsShowerListCom :tindex="tabindex" v-if="tabindex == 9 && hidden_searchcom == false"></BlogsShowerListCom>
          <BlogsShowerListCom :tindex="tabindex" v-if="tabindex == 0 && hidden_searchcom == false"></BlogsShowerListCom>
          <!-- 搜索专用组件 -->
          <BlogsShowerSearch v-if="tabindex == 0 && hidden_searchcom == true" :blogsData="blogsData"></BlogsShowerSearch>
        </el-tab-pane>
      </el-tabs>
   
    </div>
  </template>
  
  <script>
  import BlogsShowerListCom from '@/views/blogs/BlogsShowerListCom.vue';
  import BlogsShowerSearch from '@/views/blogs/BlogsShowerSearch.vue';

  export default {
    inject:['postKeyValueRequest','postRequest','putRequest','getRequest','deleteRequest'],
    name: 'BlogsShower',
    components:{BlogsShowerListCom,BlogsShowerSearch},
    data () {
   return {
    activeName: 'zero',
    input_title: '', //输入框的值
    //向下传递给子组件
    blogsData: [], //文章数据
   
    tabindex:0, //选项卡index
    
    hidden_searchcom: false , //是否隐藏搜索子组件

    }
  },
  mounted() {
    
  },
    methods:{
          //给sessionStorage存值
    setContextData: function (key, value) {
        if (typeof value == "string") {
            sessionStorage.setItem(key, value);
        } else {
            sessionStorage.setItem(key, JSON.stringify(value));
        }
    },
    // 获取标签栏的index,就是原创，转载什么的
    handleClick(tab, event) {
        this.tabindex = tab.index
        this.setContextData("currentBPage",1);
        this.setContextData("currentPage",1);
        //console.log("tabindex = " + this.tabindex);
    },
    sortGames(type) {
      this.sortType = type; // 更新排序类型
      this.tabindex = ''+type;
    },
    //根据标题搜索
    search_title(){
      //判断使用的是通用博客子组件还是搜索专用子组件
      if(this.input_title == ''){
        //如果搜索值为空，就隐藏搜索专用子组件，显示通用子组件
        this.hidden_searchcom = false
        return
      }else{
        //如果搜索值不为空，就显示搜索专用子组件
        this.hidden_searchcom = true
      }
      const _this = this
      //发起根据标题搜索请求
      this.getRequest('/blog/getByTitle?title=' + this.input_title).then(resp=>{
        if(resp){
          _this.blogsData = resp.data
        }
      
      })
    }

  }
  }
  </script>
  
  <style scoped>
  
  </style>