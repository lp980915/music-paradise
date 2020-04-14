import Vue from 'vue'
import './plugins/axios'
import App from './App.vue'
import router from './router'
import './plugins/ant-design-vue.js'
import qs from "qs";
import './assets/css/global.css'
import { message } from 'ant-design-vue'

Vue.config.productionTip = false;
Vue.prototype.$qs = qs;
Vue.prototype.$message = message;
new Vue({
  router,
  render: function (h) { return h(App) }
}).$mount('#app')
