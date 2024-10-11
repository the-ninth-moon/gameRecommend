<template>
     <div>
          <el-table
              :data="usersData"
              stripe
              style="width: 100%">
              <el-table-column label="用户列表" min-width="180">
              <template #default=scope>
               
                  <el-card  class="box-card">
                  <div style="font-size: 18px;">
                      <!-- v-if="!scope.row.shareStatement" 这里根据shareStatement属性判断文章是否为草稿 -->
                      
                      <el-image style=" height: 60px" :src="scope.row.firstPicture" :fit="fit" />
                      <el-text size="large" tag="h1" style="margin-right: 10px;margin-left: 10px;font-size: 24px;transform: translateY(-15px);" truncated>{{scope.row.userName}}</el-text>
                      <el-tag style="margin-right: 20px;" v-if="scope.row.want" size="small" type="success">申请成为推荐家</el-tag>
                      <i style="margin-right: 20px;"><el-icon style="transform: translateY(-18px); margin-right: 4px;"><Calendar /></el-icon>  
                        <el-text size="large" style="margin-right: 10px;margin-left: 10px;transform: translateY(-15px);" truncated>{{scope.row.create_time}}</el-text></i>

                      
   
  
                      
                            <el-button v-if="scope.row.want" style="float: right;transform: translateY(13px);" type="danger" size="mini" @click="logicDeleteUser(scope.row.id)">批准</el-button>
                          <el-button v-if="scope.row.role==2" style="float: right; margin-right: 10px;transform: translateY(13px);" type="primary" size="mini">降级</el-button>
                        <el-button v-if="scope.row.isDelete" style="float: right;transform: translateY(13px);" type="danger" size="mini" @click="deleteUser(scope.row.id)">删除</el-button>
                        <el-button v-if="scope.row.isDelete" style="float: right; margin-right: 10px;transform: translateY(13px);" @click="recoveryUser(scope.row.id)" type="primary" size="mini">还原</el-button>
                      

                      
                  
                  </div>
  
                  </el-card>
              </template>
                  
              </el-table-column>
          </el-table>
  
   
    </div>
  </template>
  
  <script>
  export default {
    inject:['postKeyValueRequest','postRequest','putRequest','getRequest','deleteRequest'],
    name: 'SearchUserCom',
    data () {
     return {
        usersData: [], //文章数据
  
     }
    },
    props:["usersData"], //接收父组件传过来的值
    methods:{
    }
  }
  </script>
  
  <style scoped>
  
  </style>