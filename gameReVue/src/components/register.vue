<template>
    <div v-if="!showRegisterForm">

        <el-form class="registerForm" ref="phoneForm" :model="phoneForm" :rules="rulesPhone" label-width="80px">
            <h3 class="loginTitle">注册</h3>
            <el-form-item label="手机号" prop="phoneNumber">
                <el-input v-model="phoneForm.phoneNumber"></el-input>
            </el-form-item>
            <el-form-item label="验证码" prop="captcha">
                <el-input v-model="phoneForm.captcha">   
                </el-input>
                <el-button :disabled="isSending" @click="sendCaptcha">{{ sendButtonText }}</el-button>
            </el-form-item>
            <el-form-item style="margin: auto;">
                <el-button type="primary" @click="verifyCaptcha('phoneForm')">验证</el-button>
            </el-form-item>
        </el-form>
    </div>
    <div v-else>
    <el-form ref="registerForm" status-icon :model="registerForm" :rules="rules1" label-width="120px" class="registerForm">
        <h3 class="loginTitle">注册</h3>
      <!-- <el-form-item  label="手机号：" prop="phone">
        <el-input v-model="registerForm.phone" placeholder=""></el-input>
      </el-form-item> -->
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
    <span slot="footer" class="dialog-footer">
        <el-button @click="registerDialog = true">取 消</el-button>
        <el-button type="primary" @click="registerConfirm">确 定</el-button>
      </span>
    </el-form>
</div>

</template>

<script>
// 登录组件中的逻辑处理
import eventBus from '@/eventBus';
import Captcha from '../components/Captcha.vue';
import { RouterLink } from 'vue-router';
export default {
    inject:['postKeyValueRequest','postRequest','putRequest','getRequest','deleteRequest'],
    name: "Register",
    components: {
        Captcha,
    },
    
    data(){
        var validatePass = (rule, value, callback) => {
            if (value === '') {
                callback(new Error('请输入密码'));
            } else {
                if (this.editForm.confirmPwd !== '') {
                    this.$refs.editForm.validateField('confirmPwd');
                }
                callback();
            }
        };
        var validatePass2 = (rule, value, callback) => {
            if (value === '') {
                callback(new Error('请再次输入密码'));
            } else if (value !== this.editForm.newPwd) {
                callback(new Error('两次输入密码不一致!'));
            } else {
                callback();
            }
        };

        var validateCaptcha = (rule, value, callback) => {
            if (value === '') {
                callback(new Error('请输入验证码'));
            } else {
                callback();
            }
        };
        return{
            isSending: false,
            sendButtonText: '发送验证码',
            showRegisterForm: false,
            phoneForm: {
                phoneNumber: '',
                captcha: ''
            },
            registerForm:{
                email:'',
                phone:'',
                userName:'泥航玩家',
                password:'',
            },
            generatedCaptcha: "", // 存储生成的验证码
            registerDialog:true,
            loginForm:{
              userName:"admin",
              password:"123"
            },
            checked:true,
            rulesPhone: {
                phoneNumber: [
                        { required: true, message: '请输入手机号', trigger: 'blur' },
                        { pattern: /^1[3-9]\d{9}$/, message: '请输入正确的手机号码', trigger: 'blur' }
                    ],
                captcha: [
                    { validator: validateCaptcha, trigger: 'blur' }
                ]
            },
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
        sendCaptcha() {
            // 发送验证码的逻辑
            this.$refs.phoneForm.validateField('phoneNumber', (errorMessage) => {
       
                // 假设发送验证码的接口是 /user/sendCaptcha
                this.postRequest(`/user/rsendCaptcha?phoneNumber=${this.phoneForm.phoneNumber}`).then(resp => {
                    if (resp && resp.code === 200) {
                        this.$message.success('验证码已发送，请查收短信');
                        this.isSending = true;
                        this.sendButtonText = '60秒后重新发送';
                        let count = 5;
                        const timer = setInterval(() => {
                        count--;
                        this.sendButtonText = `${count}秒后重新发送`;
                        if (count <= 0) {
                            clearInterval(timer);
                            this.isSending = false;
                        this.sendButtonText = '发送验证码';
                    }
                }, 1000);
                    } 
                    else if(resp && resp.code === 500){
                        this.$message.error('手机号已经被注册');
                    }
                    else{
                        this.$message.error('出错了');
                    }
                });
            });
        },
        verifyCaptcha(formName) {
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    // 验证验证码的逻辑
                    // 假设验证验证码的接口是 /user/verifyCaptcha
                    this.postRequest(`/user/verifyCaptcha?phoneNumber=${this.phoneForm.phoneNumber}&&verificationCode=${this.phoneForm.captcha}`).then(resp => {
                        if (resp && resp.code === 200) {
                            this.showRegisterForm = true;
                        } else {
                            this.$message.error('验证码验证失败');
                        }
                    });
                } else {
                    return false;
                }
            });
        },
        submitLogin(){
            this.$refs.loginForm.validate((validate) =>{  // Element自带的校验
                if(validate){

                    //alert("submit!");
                    //console.log(this.loginForm)
                    this.postKeyValueRequest('/doLogin',this.loginForm).then(resp=>{
                        if(resp.code==200){
                            //resp：从服务端拿到的数据  用户的数据要保存到哪里？ 保存在sessionStorage  关闭浏览器就没了
                            window.sessionStorage.setItem("user", JSON.stringify(resp));
                            // 通过事件总线通知导航栏组件用户已经登录
                            eventBus.emit('login', resp); // 发送登录事件
                            //console.log(resp);
                            //console.log(JSON.parse(window.sessionStorage.getItem("user")));
                            //页面跳转  replace：替换  用push的话，可以使用后退按钮回到登录页，用replace不可以回到登录页
                            //console.log(resp)
                            if(resp.data)
                            {
                                // if(resp.data.role==1){
                                //     this.$router.replace('/admin/home')
                                // }
                                // else if(resp.data.role==2){
                                //     this.$router.replace('/writer/home')
                                // }
                                // else if(resp.data.role==3){
                                //     this.$router.replace('/home')
                                // }
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
                    this.registerForm.phone = this.phoneForm.phoneNumber;
                    if (this.registerForm.captcha.toUpperCase() !== this.generatedCaptcha.toUpperCase()) {
                        this.$message.error("验证码错误，请重新输入！");
                        this.$refs.captchaRef.generateCaptcha(); // 重新生成验证码
                        return;
                    }
                    this.postRequest('/user/register',this.registerForm).then(resp=>{
                        if(resp){
                            console.log(resp);
                            _this.registerDialog = true
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
.registerForm{
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