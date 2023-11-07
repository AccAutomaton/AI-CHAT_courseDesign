const userCenterRoutes = [
    {
        path: '/userCenter',
        component: () => import("@/views/userCenter/UserCenterRoot.vue"),
        meta: {
            requireAuthentication: true
        }
    }
]

export default userCenterRoutes;