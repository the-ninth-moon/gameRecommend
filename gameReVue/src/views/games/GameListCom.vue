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
  
         <!-- 分页 -->
         <div v-if="!hidden_page" style="margin-top: 20px;">
                <el-pagination
                  background
                  @size-change="handleSizeChange"
                  @current-change="handleCurrentChange"
                  :current-page="currentPage"
                  :page-sizes="pagesizes"
                  :page-size="pagesize"
                  layout="total, sizes, prev, pager, next, jumper"
                  :total="total">
                </el-pagination>
         </div>
   
    </div>
  </template>
  
  <script>
  export default {
    inject:['postKeyValueRequest','postRequest','putRequest','getRequest','deleteRequest'],
    name: 'GameListCom',
    data () {
     return {
      gamesData: [], //文章数据
      currentPage: 1,  //当前页
      total:0, //总记录数
      pagesize:10, //页面大小
      pagesizes:[10,20,30], //页面数组
      t2index:0, //选项卡index
     }
    },
    props:["tindex"],
    mounted() {
      this.initIndex();
      this.initGames(); // 调用初始化游戏数据
      
    },
    
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
    //组件的index
      initIndex(){
          this.t2index = this.tindex  //保存父组件传过来的值
      },
      // 初始化【全部】游戏的数据
      initGames(){
        const _this = this
        // 通用路由
        var baseurl = '/game/getByPage?current=' + this.currentPage + '&size=' + this.pagesize
        //通过条件拼接路由
        if(this.t2index == "0"){ //全部
          baseurl +=' &is_delete=0'
        }
        else if(this.t2index == "1"){  //草稿
          baseurl +=' &share_statement=0  &is_delete=0'
        }
        else if(this.t2index == "2"){ //公开
          baseurl +=' &published=1 &share_statement=1  &is_delete=0'
        }
        else if(this.t2index == "3"){ //私密
          baseurl +=' &published=0 &share_statement=1 &is_delete=0'
        }
        else if(this.t2index == "4"){ //回收站
          baseurl +=' &is_delete=1'
        }
        //console.log(baseurl)
        this.getRequest(baseurl).then(resp=>{
          if(resp){
            //console.log(resp)
            
            _this.gamesData = resp.data.records  //将获取到的后端的值赋值给gamesData
            _this.total = resp.data.total   // 保存一下总记录数，用于前端展示
          }
        })
      },
      // 分页的当前页
      handleCurrentChange(val) {
        //console.log(`当前页: ${val}`);
        this.currentPage = val
        this.initGames()
      },
      //每页多少条
      handleSizeChange(val) {
          //console.log(`每页 ${val} 条`);
          this.pagesize = val
          this.initGames()
      },
   
    }
  }
  </script>
  
  <style scoped>
  
  </style>