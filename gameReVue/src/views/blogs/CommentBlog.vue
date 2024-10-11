<template>
  <div>
      <div style="margin-top: 20px;">
          <!-- 搜索 -->
          <el-input size="small" v-model="input_name" placeholder="请输入评论人名称，可回车搜索..." prefix-icon="el-icon-search"
          style="width: 400px;margin-right: 10px;" @keydown.enter.native="search_name"></el-input>
          <el-button size="small" type="primary"  @click="search_name" icon="el-icon-search">搜索</el-button>
          <!-- <el-button size="small" type="success"  @click="dialog_add = true" icon="el-icon-plus">新增</el-button> -->
      </div>
      <div>
          <el-table
          :data="commentData"
          style="width: 100%">
          <el-table-column
            label="编号"
            width="70">
            <template #default=scope> 
              <span style="margin-left: 10px">{{ scope.row.id }}</span>
            </template>
          </el-table-column>
          <el-table-column
            label="昵称"
            width="150">
            <template #default=scope>
              <el-tag size="medium">{{ scope.row.nickname }}</el-tag>
            </template>
          </el-table-column>
          <el-table-column
            label="身份"
            width="100">
            <template #default=scope>
              <el-tag v-if="scope.row.adminComment" type="danger" size="medium">管理员</el-tag>
              <el-tag v-if="!scope.row.adminComment" size="medium">普通用户</el-tag>
            </template>
          </el-table-column>
          <el-table-column
            label="评论内容"
            width="300">
            <template #default=scope>
              <el-tag size="medium">{{ scope.row.content }}</el-tag>
            </template>
          </el-table-column>
          <el-table-column
            label="发表时间"
            width="230">
            <template #default=scope>
              <el-tag size="medium">{{ scope.row.createTime }}</el-tag>
            </template>
          </el-table-column>
          <el-table-column
            label="推文名"
            width="200">
            <template #default=scope>
              <el-tag size="medium">{{ scope.row.parentNickname }}</el-tag>
            </template>
          </el-table-column>


          <el-table-column label="操作"  fixed="right" width="180">
            <template #default=scope>
              <el-button
                size="mini"
                type="primary"
                @click="handleLook(scope.$index, scope.row)">回复</el-button>
              <!-- <el-button
                size="mini"
                @click="handleEdit(scope.$index, scope.row)">编辑</el-button> -->
              <el-button
                size="mini"
                type="danger"
                @click="handleDelete(scope.$index, scope.row)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>

      <!-- 分页 -->
      <div style="margin-top: 20px;" v-if="showPage">
        <el-pagination
        background
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page.sync="currentPage"
        :page-size="pagesize"
        layout="total, prev, pager, next"
        :total="total">
      </el-pagination>
      </div>

      <!-- 对话框 回复评论 -->
      <el-dialog
        title="回复评论"
        v-model="dialog_look"
        width="30%"
      >
      <el-form status-icon  label-width="80px" :model="subComment" ref="subComment">
        <el-form-item label="推文：">
          {{BackComment.blogTitle}}
        </el-form-item>
        <el-form-item label="昵称：">
          {{BackComment.nickname}}
        </el-form-item>
        <el-form-item label="内容：">
          {{BackComment.content}}
        </el-form-item>
        <el-form-item label="日期：">
          {{BackComment.time}}
        </el-form-item>
        <el-form-item label="回复：" prop="content"
         :rules=" { required: true, message: '请输入评论内容', trigger: 'blur' }">
          <el-input type="textarea" v-model="subComment.content"></el-input>
        </el-form-item>
      </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button @click="dialog_look = false">取 消</el-button>
          <el-button type="primary" @click="submitBackComment('subComment')">确 定</el-button>
        </span>
      </el-dialog>


  </div>
</template>

<script>
export default {
  inject:['postKeyValueRequest','postRequest','putRequest','getRequest','deleteRequest'],
  name: 'CommentBlog',
  data () {
   return {
      commentData:[{id:"1",userId:"1",role:"1",content:"原神真是太好玩啦！",createTime:"2023-11-11",blogId:"1"},],  //评论数据
      currentPage: 1,  //当前页
      total:0, //总记录数
      pagesize:5, //页面大小
      input_name:'', //搜索框值
      dialog_add: false, //添加评论的对话框
      dialog_edit: false, //编辑评论的对话框
      type_edit:{
        id:'',
        name:''
      },
      showPage: true, //是否显示分页
      dialog_look: false, //显示回复的对话框
      BackComment: { 
        blogTitle: '',
        nickname: '',
        content: '',
        time: '',
      }, 
      subComment:{ 
        blogId:'',
        parentCommentId:'',
        adminComment:'',
        nickname:'小L-Admin',
        content:'', //回复评论的内容
        email:'1556187290@qq.com'
      }
   }
  },
  mounted() {
    this.initComment();
  },
  methods:{
    //初始化评论数据
    async initComment(){ 
      const _this = this
      let resp = await this.getRequest('/commentBlog/getCommentByPage?current=' + this.currentPage + '&size=' + this.pagesize)
        console.log(resp)
        _this.commentData = resp.records
        _this.total = resp.total

    },
    //查看评论
    async handleLook(index, row){
      console.log(row)
      const _this = this
      _this.dialog_look = true
      let resp = await this.getRequest('/blog/getByBlogId?id=' + row.blogId)
        console.log(resp)

        _this.BackComment.blogTitle = resp.data.name
      _this.BackComment.nickname = row.nickname
      _this.BackComment.content = row.content
      _this.BackComment.time = row.createTime

      _this.subComment.blogId = row.blogId
      _this.subComment.parentCommentId = row.parentCommentId
      _this.subComment.adminComment = true
    },
    //提交回复
    submitBackComment(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          const _this = this
          this.postRequest('/commentBlog/comments',this.subComment).then(resp=>{
            console.log(resp)
            _this.initComment()
            _this.BackComment.backComment = ''
            _this.dialog_look = false
            this.$message({ message: '回复评论成功！',type: 'success' });
          })

        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    //删除评论
    handleDelete(index, row) {
      console.log(index, row);
      const _this = this
      this.$confirm('此操作将永久删除该评论, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.deleteRequest('/commentBlog/deleteComment?id=' + row.id).then(resp=>{
            if(resp){
              this.initComment()
              this.$message({
                type: 'success',
                message: '已删除该评论'
              });     
            }
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });          
        });
     
    },
    //搜索评论
    search_name(){
       const _this = this
       this.getRequest('/commentBlog/comments?nickname=' + this.input_name).then(resp=>{
         console.log(resp)
         if(_this.input_name == ''){
           _this.initComment();
           _this.showPage = true
         }else{
          _this.commentData = resp
          _this.showPage = false
          _this.input_name = ''
         }
         

       })
    },
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
    },
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
      this.currentPage = val
      this.initComment()
    }
  }
}
</script>

<style scoped>

</style>