<template>
    <div>
        <!-- ref="loginForm" 绑定校验表单 -->
        <el-form :rules="rules" :model="loginForm" class="loginContainer" ref="loginForm">
            <h3 class="loginTitle">游戏推荐系统后台登录</h3>
            <!--表单里面的每一项叫做<el-form-item></el-form-item,要加个prop属性，本来是不用加的，但是我们这个使用了字段校验，这种情况家就一定要加prop属性-->
            <el-form-item prop="userName">
                <!--auto-complete:是否自动补全-->
                <el-input type="text" v-model="loginForm.userName" auto-complete="off" placeholder="请输入手机号或邮箱" @keydown.enter.native="submitLogin"></el-input>
            </el-form-item>
            <el-form-item prop="password">
                <el-input type="password" v-model="loginForm.password" auto-complete="off" placeholder="请输入密码" @keydown.enter.native="submitLogin"></el-input>
            </el-form-item>
            <el-checkbox class="loginRember" v-model="checked">记住我</el-checkbox>
            <el-button style="margin-left:20px;height:25px;" @click="registerDialog= true">注册</el-button>
            <el-button type="primary" style="width: 100%" @click="submitLogin">登录</el-button>
        </el-form>
    </div>

      
      <!-- 注册 -->
      <el-dialog
        title="注册"
        v-model="registerDialog"
        width="30%"
        >
        <el-form ref="registerForm" status-icon :model="registerForm" :rules="rules1" label-width="120px">
          <el-form-item  label="邮箱/手机号：" prop="phone">
            <el-input v-model="registerForm.phone" placeholder=""></el-input>
          </el-form-item>
          <el-form-item   label="昵称：" prop="userName">
            <el-input  v-model="registerForm.userName" placeholder="泥航玩家"></el-input>
          </el-form-item>
          <el-form-item  label="密码：" prop="password">
            <el-input  v-model="registerForm.password" placeholder=""></el-input>
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
    export default {
        inject:['postKeyValueRequest','postRequest','putRequest','getRequest','deleteRequest'],
        name: "Login",
        data(){
            return{
                registerForm:{
                    email:'',
                    phone:'',
                    userName:'泥航玩家',
                    password:'',
                },
                registerDialog:false,
                loginForm:{
                  userName:"admin",
                  password:"123"
                },
                checked:true,
                rules:{
                    //required:true:用户名必填  如果没填就弹出““””“"请输入用户名",trigger:blur 触发的方式是blur
                    userName:[{required:true,message:"用户名不能为空",trigger:"blur"}],
                    password:[{required:true,message:"密码不能为空",trigger:"blur"}],
                },
                rules1: {  //表单校验
                    email: [
                    {required: true, message: '请输入手机号或邮箱', trigger: 'blur'},
                   
                    ],
                    phone: [
                    {required: true, message: '请输入手机号或邮箱', trigger: 'blur'}
                    ],
                    userName: [
                    {required: true, message: '请输入用户名', trigger: 'blur'},
                    {min: 3, max: 20, message: '长度在 3 到 20 个字符', trigger: 'blur'}
                    ],
                    password: [
                    {required: true, message: '请输入密码', trigger: 'blur'}
                    ],
                },
            }
        },
        methods:{
            submitLogin(){
                this.$refs.loginForm.validate((validate) =>{  // Element自带的校验
                    if(validate){
                        //alert("submit!");
                        //console.log(this.loginForm)
                        this.postKeyValueRequest('/doLogin',this.loginForm).then(resp=>{
                            if(resp){
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
                        this.postRequest('/user/register',this.registerForm).then(resp=>{
                            if(resp){
                            _this.registerDialog = false
                            //alert("发布成功，点击确定跳转到博客列表页面!")
                            _this.$router.push('/login')
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
        width: 350px;
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