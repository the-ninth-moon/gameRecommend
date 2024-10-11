<template>
    <div>
      <el-tabs v-model="activeName" @tab-click="handleClick" style="margin-top: 10px;">
        
        <el-tab-pane label="全部" name="zero">
            <!-- 搜索 -->
        <el-input size="small" v-model="input_title" placeholder="请输入标题，可回车搜索..." prefix-icon="el-icon-search"
        style="width: 400px;margin-right: 10px;" @keydown.enter.native="search_title"></el-input>
        <el-button size="small" type="primary"  @click="search_title" icon="el-icon-search">搜索</el-button>
        <el-button size="small" type="primary">高级搜索</el-button>
           <!-- 通用博客列表组件 -->
            <WriterBlogListCom :tindex="tabindex" v-if="tabindex == 0 && hidden_searchcom == false"></WriterBlogListCom>
            <!-- 搜索专用组件 -->
            <SearchBlogCom v-if="tabindex == 0 && hidden_searchcom == true" :blogsData="blogsData"></SearchBlogCom>
        </el-tab-pane>


        <el-tab-pane label="待审核" name="first">
        <WriterBlogListCom :tindex="tabindex" v-if="tabindex == 1"></WriterBlogListCom>
      </el-tab-pane>
      <el-tab-pane label="草稿" name="second">
        <WriterBlogListCom :tindex="tabindex" v-if="tabindex == 2"></WriterBlogListCom>
      </el-tab-pane>
      <el-tab-pane label="公开" name="third">
        <WriterBlogListCom :tindex="tabindex" v-if="tabindex == 3"></WriterBlogListCom>
      </el-tab-pane>
      <el-tab-pane label="私密" name="fourth">
        <WriterBlogListCom :tindex="tabindex" v-if="tabindex == 4"></WriterBlogListCom>
      </el-tab-pane>
      <el-tab-pane label="回收站" name="fifth">
        <WriterBlogListCom :tindex="tabindex" v-if="tabindex == 5"></WriterBlogListCom>
      </el-tab-pane>

      </el-tabs>
  
      
   
    </div>
  </template>
  
  <script>
  import WriterBlogListCom from '@/views/blogs/WriterBlogListCom.vue';
  import SearchBlogCom from '@/views/blogs/SearchBlogCom.vue';
  export default {
    inject:['postKeyValueRequest','postRequest','putRequest','getRequest','deleteRequest'],
    name: 'WriterAllBlogs',
    components:{WriterBlogListCom,SearchBlogCom},
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
    // 获取标签栏的index,就是原创，转载什么的
    handleClick(tab, event) {
        this.tabindex = tab.index
        //console.log("tabindex = " + this.tabindex);
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