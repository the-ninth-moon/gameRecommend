<template>
     <div>
          <!-- 游戏文章 -->
          <el-table
              :data="gamesData"
              stripe
              style="width: 100%">
              <el-table-column label="游戏列表" min-width="180">
              <template #default=scope>
               
                  <el-card  class="box-card">
                  <div style="font-size: 18px;">
                      <!-- v-if="!scope.row.shareStatement" 这里根据shareStatement属性判断文章是否为草稿 -->
                      
                      <el-image style=" height: 60px" :src="scope.row.firstPicture" :fit="fit" />
                      <el-text size="large" tag="h1" style="margin-right: 10px;margin-left: 10px;font-size: 24px;transform: translateY(-15px);" truncated>{{scope.row.name}}</el-text>
                        
                      <el-tag size="small" style="margin-right: 10px;transform: translateY(-22px);"  v-if="scope.row.isDelete" type="danger">已删除</el-tag>
                      
                      <el-tag size="small" style="margin-right: 10px;transform: translateY(-22px);"  v-if="!scope.row.shareStatement" type="warning">草稿</el-tag>
                      <el-tag style="margin-right: 20px;transform: translateY(-22px);" v-if="scope.row.shareStatement" size="small" type="info">{{scope.row.published == '0' ? '私密' : '公开'}}</el-tag>
                      <!-- <el-tag style="margin-right: 20px;" v-if="scope.row.shareStatement" size="small" type="success">{{scope.row.typeId}}</el-tag> -->
                      <i style="margin-right: 20px;"><el-icon style="transform: translateY(-18px); margin-right: 4px;"><Calendar /></el-icon>  
                        <el-text size="large" style="margin-right: 10px;margin-left: 10px;transform: translateY(-15px);" truncated>{{scope.row.sellTime}}</el-text></i>

                      
   
  
                      
                            <el-button v-if="!scope.row.isDelete" style="float: right;transform: translateY(13px);" type="danger" size="mini" @click="logicDeleteGame(scope.row.id)">移入回收站</el-button>
                            <router-link :to=" '/admin/editgame/' + scope.row.id ">
                          <el-button v-if="!scope.row.isDelete" style="float: right; margin-right: 10px;transform: translateY(13px);" type="primary" size="mini">编辑</el-button>
                        </router-link>
                        <el-button v-if="scope.row.isDelete" style="float: right;transform: translateY(13px);" type="danger" size="mini" @click="deleteGame(scope.row.id)">删除</el-button>
                        <el-button v-if="scope.row.isDelete" style="float: right; margin-right: 10px;transform: translateY(13px);" @click="recoveryGame(scope.row.id)" type="primary" size="mini">还原</el-button>
                      

                      
                  
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
    name: 'SearchGameCom',
    data () {
     return {
        gamesData: [], //文章数据
  
     }
    },
    props:["gamesData"], //接收父组件传过来的值
    methods:{
            //删除游戏(逻辑删除)
    logicDeleteGame(id){
      const _this = this
      this.$confirm('此操作将移入回收站, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          //console.log("逻辑删除",id);
          this.deleteRequest('/game/logicDeleteGame?id=' + id).then(resp=>{
            //alert("删除成功！")
            location.reload(true); // 刷新当前页面
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });          
        });
    },
    //彻底删除游戏
    deleteGame(id){
      const _this = this
      this.$confirm('此操作将彻底删除该文章, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.deleteRequest('/game/deletegame?id=' + id).then(resp=>{
            alert("删除成功！")
            location.reload(true); // 刷新当前页面
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });          
        });
    },
    //还原游戏
    recoveryGame(id){
      const _this = this
      this.$confirm('确定要还原该游戏吗?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.getRequest('/game/recoveryGame?id=' + id).then(resp=>{
            //alert("还原成功！")
            location.reload(true); // 刷新当前页面
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消还原'
          });          
        });
    },
    }
  }
  </script>
  
  <style scoped>
  
  </style>