import Vue from 'vue'
import VueRouter from 'vue-router'
import ViewUI from "view-design";
Vue.use(VueRouter)
import Login from "@/views/Login";
import Home from "@/views/Home";
import UserInfo from "@/components/UserInfo";
import Music from "@/components/Music";
import Singer from "@/components/Singer";
import Welcome from "@/components/Welcome";
import MusicInfo from "@/components/MusicInfo";
import Search from "@/components/Search";
import SingerInfo from "@/components/SingerInfo";
import ListInfo from "@/components/ListInfo";
import SingerRequest from "@/components/SingerRequest";
import AdminPage from "@/components/AdminPage";
import AdminReview from "@/components/AdminReview";
import UploadMusic from "@/components/UploadMusic";

const routes = [
  {
    path: '/',
    redirect:'/login'
  },
  {
    path: '/login',
    name: 'login',
    component: Login
  },
  {
    path:'/home',
    name:'home',
    component:Home,
    children:[
      {
        path:'/userInfo',
        name:'userInfo',
        component:UserInfo
      },
      {
        path:'/music',
        name:'music',
        component:Music
      },
      {
        path:'/welcome',
        name:'welcome',
        component:Welcome
      },
      {
        path:'/singer',
        name:'singer',
        component:Singer
      },
      {
        path:'/musicInfo',
        name:'musicInfo',
        component:MusicInfo
      },
      {
        path:'/search',
        name:'search',
        component:Search
      },
      {
        path:'/singerInfo',
        name:'singerInfo',
        component:SingerInfo
      },
      {
        path: '/listInfo',
        name:'listInfo',
        component: ListInfo
      },
      {
        path:'/singerRequest',
        name:'singerRequest',
        component: SingerRequest
      },
      {
        path:'/adminPage',
        name:'adminPage',
        component: AdminPage
      },
      {
        path:'/adminReview',
        name:'adminReview',
        component: AdminReview
      },
      {
        path:'/uploadMusic',
        name:'uploadMusic',
        component: UploadMusic
      }
    ]
  }
]

const router = new VueRouter({
  routes
});
//这是路由守卫
router.beforeEach((to, from, next) => {   //to代表即将跳转的url，from表示从哪个url过来的，next表示放行
  if (to.path === '/login') {    //若要跳转的页面是登录界面
    next();     //直接跳转
  }
  else if (to.path !== '/login'){   //若要跳转的页面是个人界面
    let token = localStorage.getItem('token');    //获取本地存储的token值
    if (token===null||token===''){    //若token为空则验证不成功，跳转到登录页面
      next('/login');
      ViewUI.Message.warning('请登录')
    }
    else{           //不为空则验证成功
      next();
    }
  }
  else{
    next();
  }
});
router.afterEach((to,form) => {
});

export default router
