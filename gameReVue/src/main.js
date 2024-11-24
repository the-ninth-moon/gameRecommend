import { createApp } from 'vue'
import App from './App.vue'
import router from './router'



import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import 'element-plus/theme-chalk/display.css'
//引入图标
import * as ElementPlusIconsVue from '@element-plus/icons-vue'

//引入axiox制作的网络请求
import {postKeyValueRequest} from "./utils/api";
import {postRequest} from "./utils/api";
import {putRequest} from "./utils/api";
import {getRequest} from "./utils/api";
import {deleteRequest} from "./utils/api";

//引入编辑博客的包
import VueMarkdownEditor from '@kangc/v-md-editor';
import '@kangc/v-md-editor/lib/style/base-editor.css';
import vuepressTheme from '@kangc/v-md-editor/lib/theme/vuepress.js';
import '@kangc/v-md-editor/lib/theme/style/vuepress.css';
import Prism from 'prismjs';
import { ElMessage } from 'element-plus'

VueMarkdownEditor.use(vuepressTheme, {
  Prism,
});

router.beforeEach((to,from,next)=>{
    //to: 将要去哪里  from: 从哪里来的  next: 下一步，放行
    //如果要去的页面是Login页，直接放行

    if(to.path=='/' || to.path=='/login'||to.path=='/home'||to.path=='/phome'|| to.path=='/plogin'
       || to.path=='/login/resetPassWord'  || to.path=='/login/register' || to.path=='/plogin/resetPassWord'  || to.path=='/plogin/register'
       || to.path=='/login/phoneLogin'  || to.path=='/plogin/phoneLogin'){
        next();
    }
    else if(to.path=='/admin/home')
    {
      let uId = eval("(" + window.sessionStorage.getItem("user") + ")").data;
      if(uId.role!=1)
      {
        ElMessage.error({message: "无权访问"})
        next("/");
      }
      else{
        next();
      }
    }
    else if(to.path=='/writer/home')
    {
      let uId = eval("(" + window.sessionStorage.getItem("user") + ")").data;
      if(uId.role==3)
      {
        ElMessage.error({message: "无权访问"})
        next("/");
      }
      else{
        next();
      }
    }
    else{
      //如果有user，说明已经登录了
      if(window.sessionStorage.getItem("user")){
          next();
      }else{
        //没有登录就去登录页并记录你刚才要去的路由
        //to.path是你刚要去的路由，但因未登录被拦截了，在登录之后就会直接去到你刚访问的路由
        ElMessage.error({message: "尚未登录，请先登录"})
        next("/login");
      }
     
    }
  })

const app = createApp(App)

app.use(router)

app.use(ElementPlus)

app.use(VueMarkdownEditor);

app.provide('postKeyValueRequest',postKeyValueRequest)
app.provide('postRequest',postRequest)
app.provide('putRequest',putRequest)
app.provide('getRequest',getRequest)
app.provide('deleteRequest',deleteRequest)

for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
  app.component(key, component)
}

app.mount('#app')

