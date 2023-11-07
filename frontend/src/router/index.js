import {createRouter, createWebHistory} from 'vue-router'
import chatRoutes from "@/router/chat";
import notFoundRoutes from "@/router/404";
import store from "@/store";
import {GetAuthorization} from "@/request";
import walletRoutes from "@/router/wallet";
import userCenterRoutes from "@/router/userCenter";
import backgroundRoutes from "@/router/background";

const indexRoutes = [
    {
        path: '/',
        redirect: "/chat",
        meta: {
            requireAuthentication: true
        }
    },
    {
        path: '/login',
        component: () => import("@/views/login/LoginView.vue")
    },
    {
        path: '/register',
        component: () => import("@/views/login/RegisterView.vue")
    }
]

const routes = indexRoutes.concat(chatRoutes, walletRoutes, userCenterRoutes, backgroundRoutes, notFoundRoutes)

const router = createRouter({
    history: createWebHistory(),
    routes
})

router.beforeEach((to, from, next) => {
  if (to.meta.requireAuthentication) {
    if (store.getters.getIsLogin || GetAuthorization()) {
      next();
    }
    else {
      next({
        path: '/login',
      })
    }
  }
  else {
    next();
  }
})

export default router
