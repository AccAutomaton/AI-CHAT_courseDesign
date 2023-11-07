const walletRoutes = [
    {
        path: '/wallet',
        component: () => import("@/views/WalletView.vue"),
        meta: {
            requireAuthentication: true
        }
    }
]

export default walletRoutes;