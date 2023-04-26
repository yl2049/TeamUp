import Vue from 'vue'
import VueRouter from 'vue-router'

import HomeView from '../views/HomeView.vue'
import Login from '../views/login/Login.vue'
// import Login from '../views/Login.vue'
import Register from '../views/register/Register.vue'
// import Home from '../views/home/Home.vue'
import User from '../views/user/user.vue'



import studentLayout from '../views/student/studentLayout.vue'
import teacherLayout from '../views/teacher/teacherLayout.vue'
import { Cookie } from '@mui/icons-material'
import { Message } from "element-ui";
import Cookies from 'js-cookie';

Vue.use(VueRouter)

const routes = [

    //MainPages:
  {
    path: '/login',
    name: 'login',
    component: Login
  },
  {
    path: '/register',
    name: 'register',
    component: Register
  },
  // {
  //   path: '/user/:user_id',
  //   name: 'user',
  //   component: User
  // },
  {
    // Student:
    path: '/', //TODO: CHange this user_id = '555'
    name: 'studentLayout',
    component: studentLayout,
    redirect: '/home',
    children: [
      {
        path: 'home',
        name: 'home',
        component: () => import('../views/HomeView.vue')
      },
      {
        path: 'course',
        name: 'course',
        component: () => import( '@/views/student/studentMyCoursesView.vue')
      },
      {
        path: 'course/:course_id',
        name: 'entercourse',
        component: () => import( '@/views/student/studentEnterCourse.vue')
      },
      {
        path: 'myteams',
        name: 'myTeams',
        component: () => import( '@/views/student/studentMyTeams.vue')
      },
      {
        path: '/user/:user_id',
        name: 'user',
        component: () => import('@/views/user/user.vue')
      },
      {
        path: 'course/:course_id/task/:task_id/team',
        name: 'studentTeamDetail',
        component: () => import( '@/views/student/studentTeamDetail.vue')
      },
      {
        path: 'invitation/team/:teamId/:acceptUserId',
        name: 'invitationPage',
        component: () => import( '@/views/student/invitationPage.vue')

      },
      {
        path: 'post/:course_id/getPost/:task_id',
        name: 'post',
        component: () => import('@/views/post/PostsView')
      }
    ]
  },

  {
    // teacher:
    path: '/teacher/', //TODO: CHange this user_id = '555'
    name: 'teacherLayout',
    component: teacherLayout,
    redirect: '/teacher/home',
    children: [
      {
        path: 'home',
        name: 'teacherhome',
        component: () => import('../views/HomeView.vue')
      },
      {
        path: 'course',
        name: 'teacherMyCoursesView',
        component: () => import( '@/views/teacher/teacherMyCoursesView.vue')
      },
      {
        path: 'course/:course_id',
        name: 'teacherEnterCourse',
        component: () => import( '@/views/teacher/teacherEnterCourse.vue')
      },
      {
        path: 'course/:course_id/addtask',
        name: 'teacherAddTask',
        component: () => import('@/views/teacher/teacherAddTask.vue')
      },
      {
        path: 'course/empty',
        name: 'empty',
        component: () => import('@/views/teacher/empty.vue')
      },
      {
        path: 'course/:course_id/task/:task_id',
        name: 'teacherManageTask',
        component: () => import('@/views/teacher/teacherManageTask.vue'),
        redirect: '/teacher/course/:course_id/task/:task_id/operations',
        children: [
          {
            path: 'operations',
            name: 'teacherManageTaskOperations',
            component: () => import('@/views/teacher/teacherManageTaskOperations.vue')
          },
          {
            path: 'info',
            name: 'teacherManageTaskInfo',
            component: () => import('@/views/teacher/teacherManageTaskInfo.vue')
          },
          {
            path: 'currentteams',
            name: 'teacherManageTaskCurrentTeams',
            component: () => import('@/views/teacher/teacherManageTaskCurrentTeams.vue')
          }
        ]
      }

    ]
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

router.beforeEach((to, from, next) => {
  let isAuthenticated = !!Cookies.get('user')

  // block redirection to other paths unless logged in
  if (to.path !== '/login' && to.path !== '/register' && !isAuthenticated) {
      next({ path: '/login' })
      Message({
          message: 'Please log in！',
          type: "warning",
      });
  } else next()
})


export default router
