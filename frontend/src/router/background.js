const backgroundRoutes = [
    {
        path: '/root',
        component: () => import("@/views/root/RootView.vue"),
        meta: {
            requireAuthentication: true
        },
        children: [
            {
                path: 'user',
                children: [
                    {
                        path: 'information',
                        component: () => import("@/views/root/user/UserInformationView.vue")
                    }
                ],
            },
            {
                path: 'vip',
                children: [
                    {
                        path: 'description',
                        component: () => import("@/views/root/vip/VipDescriptionView.vue")
                    }
                ]
            },
            {
                path: 'chat',
                children: [
                    {
                        path: 'management',
                        component: () => import("@/views/root/chat/ChatManageView.vue")
                    }
                ]
            },
            {
                path: 'recharge',
                children: [
                    {
                        path: 'rechargeRecords',
                        component: () => import("@/views/root/recharge/RechargeRecordsView.vue")
                    }
                ]
            }
        ]
    }
]

export default backgroundRoutes;