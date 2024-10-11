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
            <UserListCom :tindex="tabindex" v-if="tabindex == 0 && hidden_searchcom == false"></UserListCom>
            <!-- 搜索专用组件 -->
            <SearchUserCom v-if="tabindex == 0 && hidden_searchcom == true" :usersData="usersData"></SearchUserCom>
        </el-tab-pane>


        <el-tab-pane label="待批准" name="first">
        <UserListCom :tindex="tabindex" v-if="tabindex == 1"></UserListCom>
      </el-tab-pane>


      </el-tabs>
  
      
   
    </div>
  </template>
  
  <script>
  import UserListCom from '@/views/users/UserListCom.vue';
  import SearchUserCom from '@/views/users/SearchUserCom.vue'; 
  export default {
    inject:['postKeyValueRequest','postRequest','putRequest','getRequest','deleteRequest'],
    name: 'AllUsers',
    components:{UserListCom,SearchUserCom},
    data () {
   return {
    activeName: 'zero',
    input_title: '', //输入框的值
    //向下传递给子组件
    usersData: [], //文章数据
   
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


  }
  }
  </script>
  
  <style scoped>
  
  </style>