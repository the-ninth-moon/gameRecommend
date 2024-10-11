<template>
    <div class="m_container">
     
     <!-- 博客内容 -->
     <div class="m_content">
       <el-form ref="editForm" status-icon :model="editForm" :rules="rules1"  label-width="80px">
         <el-form-item label="标题" prop="title">
           <el-input v-model="editForm.title"></el-input> 
         </el-form-item>
         <el-form-item label="游戏名" prop="gameName">
           <el-input v-model="editForm.gameName"></el-input> 
         </el-form-item>
         <el-form-item label="描述" prop="description">
           <el-input v-model="editForm.description"></el-input>
         </el-form-item>
         <el-form-item label="首页图片" prop="first_picture">
           <el-input v-model="editForm.first_picture"></el-input>
         </el-form-item>
         <el-form-item label="文章类型" prop="original" >
           <el-select v-model="editForm.original" placeholder="请选择文章类型，默认为原创">
             <el-option label="原创" value="true"></el-option>
             <el-option label="转载" value="false"></el-option>
           </el-select>
         </el-form-item>
         <el-form-item label="是否发布" prop="share_statement">
            <el-select v-model="editForm.share_statement" placeholder="选择是否发布，默认发布">
              <el-option label="发布" value="true"></el-option>
              <el-option label="草稿" value="false"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="发布形式" prop="published">
            <el-select v-model="editForm.published" placeholder="请选择发布形式，默认为私密">
              <el-option label="私密" value="false"></el-option>
              <el-option label="公开" value="true"></el-option>
            </el-select>
          </el-form-item>
         <!-- mavon-editor代码在vue3中有问题 -->
         <el-form-item label="内容" prop="content">
           <!-- <mavon-editor v-model="editForm.content"/> -->
           <v-md-editor v-model="editForm.content" height="400px"></v-md-editor>
         </el-form-item>

         <el-form-item style="margin: auto;">
          <router-link to="/admin/allblogs">
              <el-button style="margin-left: 10px;">返回</el-button>
            </router-link>
           <el-button type="primary" @click="submitForm('editForm')">发布文章</el-button>
           <!-- <el-button>取消</el-button> -->
         </el-form-item>
       </el-form>
     </div>
 
    <!-- 对话框内容 -->
    <el-dialog title="发布文章" v-model="dialogFormVisible" width="35%">

         <el-form-item label="发布形式" prop="published" :label-width="formLabelWidth"
             :rules="{
               required: true, message: '发布形式不能为空', trigger: 'blur'
             }">
           <el-select v-model="editForm.published" placeholder="请选择发布形式，，默认为私密">
             <el-option label="私密" value=0></el-option>
             <el-option label="公开" value=1></el-option>
           </el-select>
         </el-form-item>
         
       <div slot="footer" class="dialog-footer">
         <el-button @click="dialogFormVisible = false">取 消</el-button>
         <el-button type="primary" @click="submitForm('editForm')">确 定</el-button>
       </div>
     </el-dialog>
 
 
   </div>
  </template>
  <script>
    export default {
        inject:['postKeyValueRequest','postRequest','putRequest','getRequest','deleteRequest'],
      name: "EditBlog",
      data() {
        return {
          // id:'',
          editForm: {  //博客文章表单
            id: null,
            userId:'',
            gameName:'',
            title: '',
            description: '',
            first_picture: '',
            content: '',
            original: true,
            processed:null,
            published: null,
            share_statement:true,
            tags: [],
          },
          oldtags:'',  //字符串类型的标签
          type:{  //分类专栏
             name:''
          },
          getalltype:[], // 所有分类专栏
          dialogFormVisible: false,  //控制发布博客对话框
          dialog2: false,  //控制新增分类专栏对话框
          rules1: {  //表单校验
            title: [
              {required: true, message: '请输入标题', trigger: 'blur'},
              {min: 2, max: 100, message: '长度在 3 到 100 个字符', trigger: 'blur'}
            ],
            gameName: [
              {required: true, message: '请输入游戏名', trigger: 'blur'}
            ],
            description: [
              {required: true, message: '请输入摘要', trigger: 'blur'}
            ],
            first_picture: [
              {required: true, message: '请输入图片地址', trigger: 'blur'}
            ],
            content: [
              {required: true, message: '请输入文章内容', trigger: 'blur'}
            ],
            type_id: [
              {required: true, message: '分类专栏不能为空', trigger: 'blur'}
            ],
            original: [
              {required: true, message: '文章类型不能为空', trigger: 'blur'}
            ],
            published: [
              {required: true, message: '发布形式不能为空', trigger: 'blur'}
            ],
            share_statement: [
              {required: true, message: '是否已经发布不能为空', trigger: 'blur'}
            ],
          },
          rules2:{ //表单校验
            name: [
              {required: true, message: '分类专栏名不能为空', trigger: 'blur'}
            ],
          },
          formLabelWidth: '120px' , // 输入框的宽度
          inputVisible: false,
          inputValue: '',
          btags: null,
          tblogTags: [],
        }
      },
      props:['id'],
      mounted() {
        this.initBlog()
      },
      created(){

      },
      methods: {
        handleClose(tag) {
          this.editForm.tblogTags.splice(this.editForm.tblogTags.indexOf(tag), 1);
        },

        showInput() {
          this.inputVisible = true;
          this.$nextTick(_ => {
            this.$refs.saveTagInput.$refs.input.focus();
          });
        },

        handleInputConfirm() {
          let inputValue = this.inputValue;
          if (inputValue) {
            this.editForm.tags.push(inputValue);
          }
          this.inputVisible = false;
          this.inputValue = '';
        },
        //初始化博客数据
        async initBlog(){
          const _this = this
          console.log(this.id)
          let resp = await  this.getRequest('/blog/getByBlogId?id=' + this.id)
          console.log(resp)
          _this.editForm = resp.data.data
          //右侧的是后端传来的，即Java类中名字
          _this.editForm.published = _this.editForm.published  ? "公开":"私密"
          _this.editForm.type_id = _this.editForm.typeId + ''
          _this.editForm.share_statement = _this.editForm.shareStatement == true ? "发布" : "草稿"
          _this.editForm.original = _this.editForm.original = true ? "原创" : "转载"
          _this.editForm.first_picture = _this.editForm.firstPicture
          let resp2 = await  this.getRequest('/game/getByGameId?id=' + resp.data.data.gameId)
          //console.log(resp2)
          if(resp2.data.data) 
            _this.editForm.gameName = resp2.data.data.name
          //console.log(_this.editForm.tags)

          // _this.editForm.tblogTags = resp.obj.tblogTags

        },
        //校验博客基本内容表单
        submitForm(formName) {
          this.$refs[formName].validate((valid) => {
            if (valid) {
              //发布博客
                this.putRequest('/blog/updateBlog',this.editForm).then(resp=>{
                  console.log(this.editForm)
                    if(resp){
                      //alert("更新成功，点击确定跳转到博客列表页面!")
                      this.$router.push('/admin/allblogs')
                    }
                })
            } else {
              return false;
            }
          });
        },

      }
    }
  </script>
  
  <style>
    .m_container{
      margin-top: 20px;
    }
  </style>