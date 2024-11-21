<template>
    <div>
        <header>
      <nav class="navbar">
        <div class="logo" style="font-size: 25px;">S T R E A M</div>
        <el-dropdown class="userInfo" @command="commandHandler">  <!--@command="commandHandler" 点击菜单项触发的事件回调-->
            <span class="el-dropdown-link"> <!--设置name和头像的-->
                {{user.data.username}}
                <i><img :src="user.data.avatar" alt=""></i>
            </span>
            <template #dropdown>
            <el-dropdown-menu>
                <!-- <el-dropdown-item command="userInfo">个人中心</el-dropdown-item> -->
                <!--disabled:禁用的    divided:有分隔线-->
                <el-dropdown-item command="logout" divided>注销登录</el-dropdown-item>
            </el-dropdown-menu>
            </template>
        </el-dropdown>
      </nav>


    </header>

    <el-container>
    <el-header>
        <el-menu mode="horizontal" router>
            <el-menu-item index="/home">
                <el-icon><HomeFilled /></el-icon>
                <span>首页</span>
            </el-menu-item>
            <!-- {{ routeList[0].children }} -->
            <!-- 这个遍历拿到的是index.js里面的routers地址数组 -->
            <!-- !item.hidden 是将home和login的路由名隐藏，不需要在左侧菜单渲染出来 -->
            <el-menu-item :index="child.path" v-for="(child, indexj) in routeList[0].children" :key="indexj" >
                    {{ child.name }}
                </el-menu-item>

        </el-menu>
    </el-header>

            <el-main>
                <!-- 面包屑 -->
                <el-breadcrumb :separator-icon="ArrowRight" v-if="$router.currentRoute.value.path !== '/puser/home'">
                    <el-breadcrumb-item :to="{ path: '/puser/home' }">首页</el-breadcrumb-item>
                    <el-breadcrumb-item>{{$route.name}}</el-breadcrumb-item>
                </el-breadcrumb>
                <div class="homeWelcome" v-if="$route.path === '/puser/home'">
                    <h1>游戏推荐系统</h1>
                </div>
               
            </el-main>
            <RouterView></RouterView>
        </el-container>
    </div>
          <!-- 底部固定导航栏 -->
          <footer class="bottom-nav">
        <ul class="nav-links">
          <li><RouterLink to="/phome"><el-icon><i class="el-icon-house"></i></el-icon><span>首页</span></RouterLink></li>
          <li><RouterLink to="/pgames"><el-icon><i class="el-icon-gamepad"></i></el-icon><span>游戏</span></RouterLink></li>
          <li><RouterLink to="/pblogs"><el-icon><i class="el-icon-edit-outline"></i></el-icon><span>推文</span></RouterLink></li>
          <li>
            <RouterLink to="/puser/home">
              <el-icon><i class="el-icon-user"></i></el-icon><span>用户中心</span>
            </RouterLink>
          </li>
        </ul>
      </footer>
</template>
 
<script>
import { ArrowRight } from '@element-plus/icons-vue'
import { RouterLink } from 'vue-router'
import { RouterView } from 'vue-router';
import eventBus from '@/eventBus';
    export default {
        name: "pUserHome",
        data(){
            return{
                user:JSON.parse(window.sessionStorage.getItem("user")),  //这样得到的数据是字符串，要用JSON.parse方法吧字符串转换成json数据
                image:"https://img.hongyoubizhi.com/picture/pages/regular/2022/06/14/21/98841400_p0_master1200.jpg",
            }
        },
        computed:{
            //在computed里先做好判断，这里过滤的成本远比v-if的成本低
            routeList(){
                //return this.list.filter((_,index) => index === 1)
                return this.$router.options.routes.filter((_,index) => _.hidden===false ).filter((_,index) => _.path.charAt(1)=='p')
            },
        },
        methods:{
            commandHandler(cmd) {  //该方法有一个参数，cmd
                if (cmd === 'logout') {
                    this.$confirm('此操作将注销登录, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                    }).then(async () => { // 确保是异步操作
                    try {
                        // 等待注销请求完成
                        //await this.getRequest("/logout");
                        
                        // 注销成功后，移除 sessionStorage 中的用户信息
                        window.sessionStorage.removeItem("user");
                        eventBus.emit('logout'); // 发送注销事件
                        // 跳转回首页
                        this.$router.replace("/");
                        
                        // 可选：提示用户成功注销
                        this.$message({
                        type: 'success',
                        message: '已成功注销'
                        });
                    } catch (error) {
                        // 处理注销请求的错误
                        this.$message({
                        type: 'error',
                        message: '注销失败，请重试'
                        });
                    }
                    }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消注销'
                    });
                    });
                }
                }

        }
    }
</script>
 
<style>
.homeHeader{
    background-color:#409eff;
    display: flex;
    align-items: center; /*竖轴上居中*/
    justify-content:space-between; /*空白的地方在中间*/
    padding: 0 15px;
    box-sizing: border-box;
}
/* 字体样式 */
.title{
    font-size: 30px;
    font-family: 黑体;
    color: #ffffff;
}
/*设置鼠标移上去显示为手指*/
.userInfo{
    cursor: pointer; 
}
/* 头像样式 */
.el-dropdown-link img{
    width: 48px;
    height: 48px;
    border-radius: 24px;
    margin-left: 8px;
}
/* 用户名样式 */
.el-dropdown-link{
    display: flex;
    align-items: center;
}
/* 面包屑样式 */
.homeWelcome{
    text-align: center;
    font-size: 30px;
    font-family: 华文行楷;
    color: #409eff;
    padding-top: 50px;
}
a{
    text-decoration:none;
}


</style>