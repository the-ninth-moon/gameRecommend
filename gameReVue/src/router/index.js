import { createRouter, createWebHashHistory } from 'vue-router'
import Login from "../components/Login.vue"
import AdminHome from "../components/AdminHome.vue"
import WriterHome from "../components/WriterHome.vue"
import UserHome from "../components/UserHome.vue"

import WriteBlog from '../views/blogs/WriteBlog'
import WriteGame from '../views/games/WriteGame'
import EditGame from '../views/games/EditGame'
import AllBlogs from '../views/blogs/AllBlogs'
import WriterWriteBlog from '../views/blogs/WriterWriteBlog'
import WriterEditBlog from '../views/blogs/WriterEditBlog'
import WriterAllBlogs from '../views/blogs/WriterAllBlogs'
import AllGames from '../views/games/AllGames'
import CategoryGame from '../views/games/CategoryGame'
import TagGame from '../views/games/TagGame'
import CommentBlog from '../views/blogs/CommentBlog'
import CommentGame from '../views/games/CommentGame'
import EditBlog from '../views/blogs/EditBlog'
import Myself from '../views/users/ChangeBasic'
import ChangePwd from '../views/users/ChangePassWord'
import AllUsers from '../views/users/AllUsers'
import HomePage from '../components/HomePage'
import ResetPassWord from '../components/ResetPassWord'
import mainContent from '@/views/mainContent.vue'
import GamesShower from '../views/games/GamesShower.vue'
import aboutUs from '@/views/aboutUs.vue'
import GameShower from '@/views/games/GameShower.vue'
import BlogsShower from '@/views/blogs/BlogsShower.vue'
import BlogShower from '@/views/blogs/BlogShower.vue'

import pHomePage from '@/components/pHomePage.vue'
import pmainContent from '@/views/pmainContent.vue'
import pGamesShower from '@/views/games/pGamesShower.vue'
import pBlogsShower from '@/views/blogs/pBlogsShower.vue'
import pBlogShower from '@/views/blogs/pBlogShower.vue'
import pUserHome from '@/components/pUserHome.vue'
import MyfavorGame from '@/views/users/MyfavorGame.vue'
import MyfavorBlog from '@/views/users/MyfavorBlog.vue'
import pMyfavorBlog from '@/views/users/pMyfavorBlog.vue'
import pMyfavorGame from '@/views/users/pMyfavorGame.vue'

const routes = [

  {
    path: '/',
    redirect: '/home',
    hidden:true,//用于不在菜单中显示
  },
  {
    path: '/phome',
    name: 'phome',
    component: pHomePage,
    hidden:true,
    children:[
      { path: '/phome', name: 'p首页',  component: pmainContent  },
      { path: '/pgames', name: 'p游戏',  component:pGamesShower  },
      { path: '/pblogs', name: 'p推文',  component: pBlogsShower },
      { path: '/pabout', name: 'p关于我们',  component: aboutUs  },
      { path: '/plogin', name: 'p登录',  component: Login  },
      { path: '/pgames/:id', name: 'p游戏详情',  component: GameShower,props:true,hidden:true  },
      { path: '/pblogs/:id', name: 'p推文详情',  component: pBlogShower,props:true,hidden:true  },

    ]
  },
  {
    path: '/home',
    name: 'home',
    component: HomePage,
    hidden:true,
    children:[
      { path: '/home', name: '首页',  component: mainContent  },
      { path: '/games', name: '游戏',  component:GamesShower  },
      { path: '/blogs', name: '推文',  component: BlogsShower },
      { path: '/about', name: '关于我们',  component: aboutUs  },
      { path: '/login', name: '登录',  component: Login  },
      { path: '/games/:id', name: '游戏详情',  component: GameShower,props:true,hidden:true  },
      { path: '/blogs/:id', name: '推文详情',  component: BlogShower,props:true,hidden:true  },

    ]
  },
  {
    path: '/login',
    name: 'Login',
    component: Login,
    hidden:true,
  },
  {
    path: '/login/resetPassWord',
    name: 'ResetPassWord',
    component: ResetPassWord,
    hidden:true,
  },

  {
    path: '/admin/home',
    name: '游戏信息管理',
    component: AdminHome,
    hidden:false,
    children:[
      { path: '/admin/writegame', name: '发布游戏信息',  component: WriteGame  },
      { path: '/admin/editgame/:id', name: '编辑游戏信息',  component: EditGame,props:true,hidden:true  },
      { path: '/admin/allgames', name: '管理游戏',  component: AllGames  },
      { path: '/admin/categorygame', name: '分类管理',  component: CategoryGame  },
      { path: '/admin/taggame', name: '标签管理',  component: TagGame  },
    ]
  },
  {
    path: '/admin/home',
    name: '游戏推文管理',
    component: AdminHome,
    hidden:false,
    children:[
      { path: '/admin/writeblog', name: '发布推文',  component: WriteBlog  },
     { path: '/admin/editblog/:id', name: '编辑推文',  component: EditBlog,props:true,hidden:true  },
      { path: '/admin/allblogs', name: '管理推文',  component: AllBlogs,props:true,hidden:true  },
    ]
  },
  {
    path: '/admin/home',
    name: '用户管理',
    component: AdminHome,
    hidden:false,
    children:[
      { path: '/admin/allusers', name: '管理用户',  component: AllUsers  },

    ]
  },
  {
    path: '/admin/home',
    name: '评论管理',
    component: AdminHome,
    hidden:false,
    children:[
      { path: '/admin/commentgame', name: '游戏评论管理',  component: CommentGame  },
      { path: '/admin/commentblog', name: '推文评论管理',  component: CommentBlog  },
    ]
  },

  {
    path: '/writer/home',
    name: '我的信息',
    component: WriterHome,
    hidden:false,
    children:[
      { path: '/writer/me', name: '修改信息 ',  component: Myself  },
      { path: '/writer/changePwd', name: '修改密码 ',  component: ChangePwd  },
      { path: '/writer/favorGame', name: '收藏游戏 ',  component: MyfavorGame  },
      { path: '/writer/favorBlog', name: '收藏博文 ',  component: MyfavorBlog  },
    ]
  },
  {
    path: '/writer/home',
    name: '我的推文',
    component: WriterHome,
    hidden:false,
    children:[
      { path: '/writer/writeblog', name: '发布推文 ',  component: WriterWriteBlog  },
     { path: '/writer/editblog/:id', name: '编辑推文 ',  component: WriterEditBlog,props:true,hidden:true  },
      { path: '/writer/allblogs', name: '管理推文 ',  component: WriterAllBlogs,props:true,hidden:true  },
    ]
  },

  {
    path: '/user/home',
    name: '用户信息 ',
    component: UserHome,
    hidden:false,
    children:[
      { path: '/user/me', name: '修改信息  ',  component: Myself  },
      { path: '/user/changePwd', name: '修改密码  ',  component: ChangePwd  },
      { path: '/user/favorGame', name: '收藏游戏  ',  component: MyfavorGame  },
      { path: '/user/favorBlog', name: '收藏博文  ',  component: MyfavorBlog  },
    ]
  },
  {
    path: '/puser/home',
    name: '我的信息 ',
    component: pUserHome,
    hidden:false,
    children:[
      { path: '/puser/me', name: '修改信息',  component: Myself  },
      { path: '/puser/changePwd', name: '修改密码',  component: ChangePwd  },
      { path: '/puser/favorGame', name: '收藏游戏',  component: pMyfavorGame  },
      { path: '/pser/favorBlog', name: '收藏博文',  component: pMyfavorBlog  },
    ]
  },

 ]


const router = createRouter({
  history: createWebHashHistory(),
  routes
})

export default router
