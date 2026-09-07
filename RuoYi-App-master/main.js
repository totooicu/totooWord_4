import Vue from 'vue'
import App from './App'
import store from './store' // store
import plugins from './plugins' // plugins
import './permission' // permission
import { useDict } from '@/utils/dict'
import * as Pinia from 'pinia';
Vue.use(plugins)

Vue.config.productionTip = false
Vue.prototype.$store = store

App.mpType = 'app'

const app = new Vue({
  ...App
})
// 创建Pinia实例并挂载到Vue
const pinia = Pinia.createPinia()
app.use(pinia)
app.config.globalProperties.useDict = useDict
app.$mount()


export {
  app,
  Pinia
}