<template>
    <div class="m_container">
      <div class="m_content">
        <el-form ref="editForm" status-icon :model="editForm" :rules="rules1"  label-width="80px">
          <el-form-item label="原密码" prop="old">
            <el-input v-model="editForm.old"></el-input> 
          </el-form-item>
          <el-form-item label="新密码" prop="newPwd">
            <el-input v-model="editForm.newPwd"></el-input>
          </el-form-item>
          <el-form-item label="再次输入新密码" prop="confirmPwd">
            <el-input v-model="editForm.confirmPwd"></el-input>
          </el-form-item>
          <el-form-item prop="captcha">
            <Captcha
                ref="captchaRef"
                :width="'200px'"
                :height="'80px'"
                :number="4"
                :night="false"
                @getValue="updateCaptchaValue"
            />
            <el-input
                v-model="editForm.captcha"
                placeholder="请输入验证码,不区分大小写"
            ></el-input>
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
    import Captcha from '@/components/Captcha.vue';
    export default {
        inject:['postKeyValueRequest','postRequest','putRequest','getRequest','deleteRequest'],
      name: "ChangePwd",
      components: {
            Captcha,
        },
      data() {
        var validatePass = (rule, value, callback) => {
        if (value === '') {
        } else {
          if (this.editForm.confirmPwd !== '') {
              this.$refs.editForm.validateField('confirmPwd');
          }
        }
      };
      var validatePass2 = (rule, value, callback) => {
          if (value === '') {
          } else if (value !== this.editForm.newPwd) {
              new Error('两次输入密码不一致!');
          } else {
          }
      };
        return {
          generatedCaptcha: "", // 存储生成的验证码
          editForm: {  //游戏文章表单
            id: null,
            old: '',
            newPwd:'',
            confirmPwd:'',
          },
          user:null,
          rules1: {  //表单校验
            old: [
              {required: true, message: '请输入原密码', trigger: 'blur'},
              {min: 1, max: 30, message: '长度在 1 到 30 个字符', trigger: 'blur'}
            ],
            newPwd: [
              { required: true, message: '请输入密码', trigger: 'blur' },
              { min: 3, max: 16, message: '长度在 3 到 16 个字符', trigger: 'blur' },
            ],
            confirmPwd: [
              { required: true, message: '请确认密码', trigger: 'blur' },
              { min: 3, max: 16, message: '长度在 3 到 16 个字符', trigger: 'blur' },
            ],
            captcha: [{ required: true, message: "请输入验证码", trigger: "blur" }],
          },
        }
      },
      mounted() {
        this.user = eval("("+window.sessionStorage.getItem("user")+")").data;
        this.editForm.id = this.user.id;
      },
      methods: {
            // 更新生成的验证码值
            updateCaptchaValue(captchaValue) {
                this.generatedCaptcha = captchaValue;
            },
          submitUser(formName) {
           // console.log(1)
            const _this = this
            this.$refs[formName].validate((valid) => {
              if (valid) {
                if (this.editForm.captcha.toUpperCase() !== this.generatedCaptcha.toUpperCase()) {
                      this.$message.error("验证码错误，请重新输入！");
                      this.$refs.captchaRef.generateCaptcha(); // 重新生成验证码
                      return;
                  }
                if(this.editForm.newPwd!=this.editForm.confirmPwd)
                {
                    alert("两次密码不一致");
                    return;
                }
                this.postRequest('/user/updatePwd',this.editForm).then(resp=>{
                  if(resp){
                    console.log(resp)
                    if(resp.code==500){
                        if(this.user.role==2)
                        {
                            _this.$router.push('/writer/changePwd')
                        }
                         else if(this.user.role==3){
                            _this.$router.push('/user/changePwd')
                         }
                         return
                    }
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
  