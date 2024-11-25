<template>
  <div class="111r">
   
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
            <el-upload
              class="blog-uploader"
              action="/api/upload/blog"  
              :on-success="handleUploadSuccess"
              :before-upload="beforeUpload"
              accept="image/*"
              :show-file-list="false">
              <el-button size="small" type="primary">上传图片</el-button>
            </el-upload>
          </el-form-item>
        <!-- mavon-editor代码在vue3中有问题 -->
        <el-form-item label="内容" prop="content">
          <!-- <mavon-editor v-model="editForm.content"/> -->
          <v-md-editor v-model="editForm.content" height="400px"></v-md-editor>
        </el-form-item>

        <el-form-item style="margin: auto;">
          <el-button type="info" @click="temporarySave()">暂时保存</el-button>
          <el-button type="primary" @click="tosubmitForm('editForm')">发布文章</el-button>
          <!-- <el-button>取消</el-button> -->
        </el-form-item>
      </el-form>
    </div>

   <!-- 对话框内容 -->
   <el-dialog title="发布文章" v-model="dialogFormVisible" width="35%">
        <el-form-item label="文章类型" prop="original" :label-width="formLabelWidth"
            :rules="{
              required: true, message: '文章类型不能为空', trigger: 'blur'
            }">
          <el-select v-model="editForm.original" placeholder="请选择文章类型，默认为原创">
            <el-option label="原创" value="原创"></el-option>
            <el-option label="转载" value="转载"></el-option>
          </el-select>
        </el-form-item>
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
        <el-button type="primary" @click="submitBlog('editForm')">确 定</el-button>
      </div>
    </el-dialog>


  </div>
</template>
<script>
  export default {
      inject:['postKeyValueRequest','postRequest','putRequest','getRequest','deleteRequest'],
    name: "WriterWriteBlog",
    data() {
      return {
        editForm: {  //博客文章表单
          id: null,
          userId:'',
          gameName:'',
          title: '',
          description: '',
          first_picture: '',
          content: '',
          original: null,
          processed:null,
          published: null,
          share_statement:true,
          tags: [],
        },
        editForm2: {  //用来校验的表单
          type_id: null,
          flag:'',
          published: null,
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
            {min: 3, max: 100, message: '长度在 3 到 100 个字符', trigger: 'blur'}
          ],
          gameName: [
            {required: true, message: '请输入标题', trigger: 'blur'},
            {min: 3, max: 100, message: '长度在 3 到 100 个字符', trigger: 'blur'}
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
        },
        rules2:{ //表单校验
          name: [
            {required: true, message: '分类专栏名不能为空', trigger: 'blur'}
          ],
        },
        formLabelWidth: '120px',  // 输入框的宽度
        inputVisible: false,
        inputValue: ''
      }
    },
    created() {
      let user = eval("("+window.sessionStorage.getItem("user")+")");
     this.editForm.userId=user.data.id.toString()
    },
    methods: {
      handleUploadSuccess(response, file, fileList) {
        // 上传成功后的处理
        if (response && response.data && response.data.url) {
          this.editForm.first_picture = response.data.url;  // 设置返回的 URL
        } else {
          this.$message.error('头像上传失败');
        }
      },
      beforeUpload(file) {
        // 可以在此检查文件类型、大小等
        const isImage = file.type.startsWith('image/');
        if (!isImage) {
          this.$message.error('只能上传图片');
        }
        return isImage;
      },
      handleClose(tag) {
        this.editForm.tags.splice(this.editForm.tags.indexOf(tag), 1);
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
      //去发布文章
      toSubmit() {
        this.dialogFormVisible = true
        //console.log("set visible success")
      },
      //校验添加分类专栏表单
      submitForm(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            
          } else {
            return false;
          }
        });
      },
      //校验博客基本内容表单
      tosubmitForm(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            this.toSubmit()
          } else {
              //console.log("tosubmitForm function says not valid")
            return false;
          }
        });
      },
      //校验发布博客表单,校验成功后发布博客
      submitBlog(formName) {
        const _this = this
        this.editForm.share_statement = true
        console.log(this.editForm)
        this.$refs[formName].validate((valid) => {
          if (valid) {
            //发布博客
            this.postRequest('/blog/saveBT',this.editForm).then(resp=>{
              if(resp){
                _this.dialogFormVisible = false
                //alert("发布成功，点击确定跳转到博客列表页面!")
                _this.$router.push('/writer/allblogs')
              }
            })
          } else {
            return false;
          }
        });
      },
      //暂时保存博客
      temporarySave(){
        const _this = this
        this.editForm.share_statement = false
        console.log( this.editForm)
        this.postRequest('/blog/temporarySave',this.editForm).then(resp=>{
          if(resp){
            this.$message('已成功保存草稿成功');
          }
        })
      }
    }
  }
</script>

<style>
  .m_container{
    margin-top: 20px;
    padding: 0px;
    margin: 0px;
  }
  .el-tag + .el-tag {
    margin-left: 10px;
  }
  .button-new-tag {
    margin-left: 10px;
    height: 32px;
    line-height: 30px;
    padding-top: 0;
    padding-bottom: 0;
  }
  .input-new-tag {
    width: 90px;
    margin-left: 10px;
    vertical-align: bottom;
  }
</style>
