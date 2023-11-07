const chatRoutes = [
    {
        path: '/chat',
        component: () => import("@/views/ChatView.vue"),
        meta: {
            requireAuthentication: true
        }
    }
]

export default chatRoutes;