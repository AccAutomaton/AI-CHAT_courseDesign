// --- vue.js --- //
import { createApp } from 'vue'

import App from './App.vue'
import store from './store'
import router from './router'

const app = createApp(App)

app.use(router).use(store).mount('#app')

// --- element-plus --- //
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
import zhCn from 'element-plus/dist/locale/zh-cn.mjs'

for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
}
app.use(ElementPlus, {locale: zhCn})