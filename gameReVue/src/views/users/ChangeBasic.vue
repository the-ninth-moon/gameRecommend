<template>
    <div class="m_container">
      <div class="m_content">
        <el-form ref="editForm" status-icon :model="editForm" :rules="rules1"  label-width="80px">
          <el-form-item label="昵称" prop="userName">
            <el-input v-model="editForm.userName"></el-input> 
          </el-form-item>
          <el-form-item label="头像" prop="avatar">
            <el-input v-model="editForm.avatar"></el-input>
          </el-form-item>

          <el-form-item label="申请" prop="want">
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
          editForm: {  //游戏文章表单
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
        //console.log(this.user)
      },
      methods: {
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
  