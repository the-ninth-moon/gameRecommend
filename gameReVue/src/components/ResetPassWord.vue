<template>
    <div class="m_container">
        <div class="m_content">
            <div v-if="!showResetForm">
                <h3 class="loginTitle">找回密码</h3>
                <el-form ref="phoneForm" :model="phoneForm" :rules="rulesPhone" label-width="80px">
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
                <h3 class="loginTitle">找回密码</h3>
                <el-form ref="editForm" status-icon :model="editForm" :rules="rules1" label-width="80px">
                    <el-form-item label="新密码" prop="newPwd">
                        <el-input v-model="editForm.newPwd"></el-input>
                    </el-form-item>
                    <el-form-item label="再次输入新密码" prop="confirmPwd">
                        <el-input v-model="editForm.confirmPwd"></el-input>
                    </el-form-item>
                    <el-form-item style="margin: auto;">
                        <el-button type="primary" @click="submitUser('editForm')">确定</el-button>
                    </el-form-item>
                </el-form>
            </div>
        </div>
    </div>
    </template>
<script>
import Captcha from '@/components/Captcha.vue';

export default {
    inject: ['postKeyValueRequest', 'postRequest', 'putRequest', 'getRequest', 'deleteRequest'],
    name: "ResttPassWord",
    components: {
        Captcha,
    },
    data() {
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

        var validatePhoneNumber = (rule, value, callback) => {
            if (value === '') {
                callback(new Error('请输入手机号码'));
            } else if (!/^[1][3-9][0-9]{9}$/.test(value)) {
                callback(new Error('请输入正确的手机号码'));
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

        return {
            isSending: false,
            sendButtonText: '发送验证码',
            showResetForm: false,
            phoneForm: {
                phoneNumber: '',
                captcha: ''
            },
            editForm: {
                phone: null,
                newPwd: '',
                confirmPwd: ''
            },
            user: null,
            rulesPhone: {
                phoneNumber: [
                        { required: true, message: '请输入手机号', trigger: 'blur' },
                        { pattern: /^1[3-9]\d{9}$/, message: '请输入正确的手机号码', trigger: 'blur' }
                    ],
                captcha: [
                    { validator: validateCaptcha, trigger: 'blur' }
                ]
            },
            rules1: {
                newPwd: [
                    { required: true, message: '请输入密码', trigger: 'blur' },
                    { min: 3, max: 16, message: '长度在 3 到 16 个字符', trigger: 'blur' },
                    { validator: validatePass, trigger: 'blur' }
                ],
                confirmPwd: [
                    { required: true, message: '请确认密码', trigger: 'blur' },
                    { min: 3, max: 16, message: '长度在 3 到 16 个字符', trigger: 'blur' },
                    { validator: validatePass2, trigger: 'blur' }
                ]
            }
        }
    },
    methods: {
        sendCaptcha() {
            // 发送验证码的逻辑
            this.$refs.phoneForm.validateField('phoneNumber', (errorMessage) => {
       
                // 假设发送验证码的接口是 /user/sendCaptcha
                this.postRequest(`/user/sendCaptcha?phoneNumber=${this.phoneForm.phoneNumber}`).then(resp => {
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
                        this.$message.error('手机号还没有注册');
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
                            this.showResetForm = true;
                        } else {
                            this.$message.error('验证码验证失败');
                        }
                    });
                } else {
                    return false;
                }
            });
        },
        submitUser(formName) {
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    if (this.editForm.newPwd !== this.editForm.confirmPwd) {
                        this.$message.error("两次密码不一致");
                        return;
                    }
                    this.editForm.phone = this.phoneForm.phoneNumber;
                    this.postRequest('/user/fupdatePwd', this.editForm).then(resp => {
                        if (resp) {
                            if (resp.code === 500) {
                                this.$message.error("未知错误");
                                return;
                            }
                            else{
                                this.$router.push('/login');
                            }
                        }
                    });
                } else {
                    return false;
                }
            });
        }
    }
}
</script>
<style>
.m_container{
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
</style>
