<template>
    <div>
      <div>
          <el-table
              :data="usersData"
              stripe
              style="width: 100%">
              <el-table-column label="用户列表" min-width="180">
              <template #default=scope>
               
                  <el-card  v-if="scope.row.username!= 'admin'" class="box-card">
                  <div style="font-size: 18px;">                 
                      <el-image style=" height: 60px" :src="scope.row.avatar" :fit="fit" />
                      <el-text size="large" tag="h1" style="margin-right: 10px;margin-left: 10px;font-size: 24px;transform: translateY(-15px);" truncated>{{scope.row.username}}</el-text>
                      <el-tag style="margin-right: 20px;" v-if="scope.row.want" size="small" type="success">申请成为推荐家</el-tag>
                      <i style="margin-right: 20px;"><el-icon style="transform: translateY(-18px); margin-right: 4px;"><Calendar /></el-icon>  
                        <el-text size="large" style="margin-right: 10px;margin-left: 10px;transform: translateY(-15px);" truncated>{{scope.row.createTime}}</el-text></i>
            
                            <el-button v-if="scope.row.want" style="float: right;transform: translateY(13px);" type="primary" size="mini" @click="AccessUser(scope.row.id)">批准</el-button>
                          <el-button v-if="scope.row.role==2" style="margin-right:10px;float: right; transform: translateY(13px);" type="primary" size="mini" @click="DemoteUser(scope.row.id)"> 降级 </el-button>
                        <el-button   style="float: right;margin-right:10px;transform: translateY(13px);" type="danger" size="mini" @click="deleteUser(scope.row.id)">删除</el-button>  
                  </div>
                  </el-card>
              </template>
                  
              </el-table-column>
          </el-table>
    </div>
  
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
    name: 'UserListCom',
    data () {
     return {
      usersData: [], //文章数据
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
      this.initUsers(); // 调用初始化游戏数据
      
    },
    
    methods:{
      AccessUser(id){
      const _this = this
      this.$confirm('此操作将使得该用户可以发表文章, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          //console.log("逻辑删除",id);
          this.postRequest('/user/accessUser?id=' + id).then(resp=>{
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
    DemoteUser(id){
      const _this = this
      this.$confirm('此操作将使得该作者变为普通用户, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          //console.log("逻辑删除",id);
          this.postRequest('/user/demoteUser?id=' + id).then(resp=>{
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
    deleteUser(id){
      const _this = this
      this.$confirm('此操作将彻底删除该用户, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          //console.log("逻辑删除",id);
          this.postRequest('/user/deleteUser?id=' + id).then(resp=>{
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
    //组件的index
      initIndex(){
          this.t2index = this.tindex  //保存父组件传过来的值
      },
      // 初始化【全部】游戏的数据
      initUsers(){
        const _this = this
        // 通用路由
        var baseurl = '/user/getByPage?current=' + this.currentPage + '&size=' + this.pagesize
        //通过条件拼接路由
        if(this.t2index == "0"){ //全部
        }
        else if(this.t2index == "1"){  //草稿
          baseurl +=' &want=1'
        }
        //console.log(baseurl)
        this.getRequest(baseurl).then(resp=>{
          if(resp){
            console.log(resp)
            
            _this.usersData = resp.data.records  //将获取到的后端的值赋值给usersData
            _this.total = resp.data.total   // 保存一下总记录数，用于前端展示
          }
        })
      },
      // 分页的当前页
      handleCurrentChange(val) {
        //console.log(`当前页: ${val}`);
        this.currentPage = val
        this.initUsers()
      },
      //每页多少条
      handleSizeChange(val) {
          //console.log(`每页 ${val} 条`);
          this.pagesize = val
          this.initUsers()
      },
   
    }
  }
  </script>
  
  <style scoped>
  
  </style>