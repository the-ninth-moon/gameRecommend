<template>
    <div class="m_container">
      <div class="m_content">
        <el-form ref="editForm" status-icon :model="editForm" :rules="rules1"  label-width="80px">
          <el-form-item label="昵称" prop="userName">
            <el-input v-model="editForm.userName"></el-input> 
          </el-form-item>
          <el-form-item label="头像" prop="avatar">
            <el-input v-model="editForm.avatar"></el-input>
            <el-upload
              class="avatar-uploader"
              action="/api/upload/avatar"  
              :on-success="handleUploadSuccess"
              :before-upload="beforeUpload"
              accept="image/*"
              :show-file-list="false">
              <el-button size="small" type="primary">上传头像</el-button>
            </el-upload>
          </el-form-item>

          <el-form-item label="申请" prop="want" v-if="this.role==3">
            <el-checkbox v-model="editForm.want">申请成为游戏推荐家</el-checkbox>
          </el-form-item>

          <el-form-item style="margin: auto;">
            <el-button type="primary" @click="submitUser('editForm')">确定</el-button>
            <!-- <el-button>取消</el-button> -->
          </el-form-item>
          
        </el-form>
      </div>
 
  
    </div>
  </template>
  <script>
    export default {
        inject:['postKeyValueRequest','postRequest','putRequest','getRequest','deleteRequest'],
      name: "Myself",
      data() {
        return {
          editForm: { 
            id: null,
            userName: '',
            avatar:'',
            want:false,
          },
          user:null,
          rules1: {  //表单校验
            userName: [
              {required: true, message: '请输入昵称', trigger: 'blur'},
              {min: 1, max: 30, message: '长度在 1 到 30 个字符', trigger: 'blur'}
            ],
            avatar: [
              {required: true, message: '请输入头像', trigger: 'blur'}
            ],

          },
        }
      },
      mounted() {
        this.user = eval("("+window.sessionStorage.getItem("user")+")").data;
        this.editForm.id = this.user.id;
        this.editForm.userName = this.user.username;
        this.editForm.avatar = this.user.avatar;
        this.role = this.user.role;
        console.log(this.user.avatar)
      },
      methods: {
        handleUploadSuccess(response, file, fileList) {
            // 上传成功后的处理
            if (response && response.data && response.data.url) {
              this.editForm.avatar = response.data.url;  // 设置返回的 URL
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
          submitUser(formName) {
            const _this = this
            console.log(this.editForm)
            this.$refs[formName].validate((valid) => {
              if (valid) {
                this.postRequest('/user/updateBasic',this.editForm).then(resp=>{
                  if(resp){
                    //修改基础信息
                    console.log(resp)
                    window.sessionStorage.removeItem("user");
                    window.sessionStorage.setItem("user", JSON.stringify(resp));
                    if(this.user.role==2)
                    {
                      _this.$router.push('/writer/home')
                    }
                   else if(this.user.role==3){
                    _this.$router.push('/user/home')
                   }
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

  </style>
  