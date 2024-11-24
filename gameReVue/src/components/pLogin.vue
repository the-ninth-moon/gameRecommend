<template>
    <!-- ref="loginForm" 绑定校验表单 -->
    <el-form :rules="rules" :model="loginForm" class="loginContainer" ref="loginForm">
        <h3 class="loginTitle">登录</h3>
        <!--表单里面的每一项叫做<el-form-item></el-form-item,要加个prop属性，本来是不用加的，但是我们这个使用了字段校验，这种情况家就一定要加prop属性-->
        <el-form-item prop="userName">
            <!--auto-complete:是否自动补全-->
            <el-input type="text" v-model="loginForm.userName" auto-complete="off" placeholder="请输入手机号或邮箱" @keydown.enter.native="submitLogin"></el-input>
        </el-form-item>
        <el-form-item prop="password">
            <el-input type="password" v-model="loginForm.password" auto-complete="off" placeholder="请输入密码" @keydown.enter.native="submitLogin"></el-input>
        </el-form-item>

        <el-button style="margin-left:20px;height:25px;" @click="navigateTo('/plogin/phoneLogin')">手机登录</el-button>
        <el-button style="margin-left:20px;height:25px;" @click="navigateTo('/plogin/resetPassWord')">忘记密码</el-button>
        <el-button style="margin-left:20px;height:25px;" @click="navigateTo('/plogin/register')">注册</el-button>
        <el-button type="primary" style="width: 100%" @click="submitLogin">登录</el-button>
    </el-form>

  
  <!-- 注册 -->
  <el-dialog
    title="注册"
    v-model="registerDialog"
    width="30%"
    >
    <el-form ref="registerForm" status-icon :model="registerForm" :rules="rules1" label-width="120px">
      <el-form-item  label="手机号：" prop="phone">
        <el-input v-model="registerForm.phone" placeholder=""></el-input>
      </el-form-item>
      <el-form-item   label="昵称：" prop="userName">
        <el-input  v-model="registerForm.userName" placeholder="泥航玩家"></el-input>
      </el-form-item>
      <el-form-item  label="密码：" prop="password">
        <el-input  v-model="registerForm.password" placeholder=""></el-input>
      </el-form-item>
      <el-form-item prop="captcha" label="验证码：">
        <Captcha
            ref="captchaRef"
            :width="'200px'"
            :height="'80px'"
            :number="4"
            :night="false"
            @getValue="updateCaptchaValue"
        />
        <el-input
             
            v-model="registerForm.captcha"
            placeholder="请输入验证码"
        ></el-input>
    </el-form-item>
    </el-form>

     <span slot="footer" class="dialog-footer">
        <el-button @click="registerDialog = false">取 消</el-button>
        <el-button type="primary" @click="registerConfirm">确 定</el-button>
      </span>
  </el-dialog>
</template>

<script>
// 登录组件中的逻辑处理
import eventBus from '@/eventBus';
import Captcha from '../components/Captcha.vue';
import { RouterLink } from 'vue-router';
export default {
    inject:['postKeyValueRequest','postRequest','putRequest','getRequest','deleteRequest'],
    name: "pLogin",
    components: {
        Captcha,
    },
    data(){
        return{
            registerForm:{
                email:'',
                phone:'',
                userName:'泥航玩家',
                password:'',
            },
            generatedCaptcha: "", // 存储生成的验证码
            registerDialog:false,
            loginForm:{
              userName:"admin",
              password:"123"
            },
            checked:true,
            rules: {
                userName: [{ required: true, message: "用户名不能为空", trigger: "blur" }],
                password: [{ required: true, message: "密码不能为空", trigger: "blur" }],
               
            },
            rules1: {
                email: [
                    { required: true, message: '请输入邮箱', trigger: 'blur' },
                    { type: 'email', message: '请输入正确的邮箱地址', trigger: ['blur', 'change'] }
                ],
                phone: [
                    { required: true, message: '请输入手机号', trigger: 'blur' },
                    { pattern: /^1[3-9]\d{9}$/, message: '请输入正确的手机号码', trigger: 'blur' }
                ],
                userName: [
                    { required: true, message: '请输入用户名', trigger: 'blur' },
                    { min: 2, max: 20, message: '长度在 2 到 20 个字符', trigger: 'blur' },
                ],
                password: [
                    { required: true, message: '请输入密码', trigger: 'blur' },
                    { min: 6, max: 20, message: '密码长度在 6 到 20 个字符之间', trigger: 'blur' },
                    { pattern: /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{6,20}$/, message: '密码必须同时包含字母和数字', trigger: 'blur' }
                ],
                captcha: [{ required: true, message: "请输入验证码", trigger: "blur" }],
            }
        }
    },
    methods:{
        navigateTo(path) {
            this.$router.push(path);
        },
        // 更新生成的验证码值
        updateCaptchaValue(captchaValue) {
            this.generatedCaptcha = captchaValue;
        },
        submitLogin(){
            this.$refs.loginForm.validate((validate) =>{  // Element自带的校验
                if(validate){
                    this.postKeyValueRequest('/doLogin',this.loginForm).then(resp=>{
                        if(resp.code==200){
                            window.sessionStorage.setItem("user", JSON.stringify(resp));
                            eventBus.emit('login', resp); // 发送登录事件
                            if(resp.data)
                            {

                                this.$router.replace('/home')
                            }

                        }
                        else if(resp.code==400){
                            this.$message.error("用户名或密码错误");
                        }
                    })
                }else {
                    this.$message.error("请输入所有字段");
                    return false;
                }
            })
        },
        registerConfirm() {
            const _this = this
            this.$refs.registerForm.validate((valid) => {
                if (valid) {
                    if (this.registerForm.captcha.toUpperCase() !== this.generatedCaptcha.toUpperCase()) {
                        this.$message.error("验证码错误，请重新输入！");
                        this.$refs.captchaRef.generateCaptcha(); // 重新生成验证码
                        return;
                    }
                    this.postRequest('/user/register',this.registerForm).then(resp=>{
                        if(resp){
                            console.log(resp);
                            _this.registerDialog = false
                            //alert("发布成功，点击确定跳转到博客列表页面!")
                            _this.$router.push('/login')
                        return;
                        } else{
                            this.$message.error("手机号可能已经出测过！");
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

<style scoped>
.loginContainer{
    border-radius: 15px;
    /* 控制background的范围 */
    background-clip:padding-box;
    margin: 180px auto;
    width: 300px;
    padding: 15px 35px 15px 35px;
    background: #fff;
    border: 1px solid #eaeaea;
    box-shadow: 0 0 25px #cac6c6;
}
.loginTitle{
    margin: 15px auto 20px auto;
    text-align: center;
    color: #505458;
}
.loginRember{
    text-align: left;
    margin: 0 0 25px 0;
}
</style>