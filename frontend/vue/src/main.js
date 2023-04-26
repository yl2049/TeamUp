import Vue from 'vue'
import App from './App.vue'
import router from './router'
// import VueRouter from 'vue-router'
import ElementUI from 'element-ui';
// import ViewUIPlus from 'view-ui-plus';
// import 'view-ui-plus/dist/styles/viewuiplus.css'
import 'element-ui/lib/theme-chalk/index.css';

// import '@/assets/global.css'
import axios from 'axios'
import VueAxios from 'vue-axios'


Vue.config.productionTip = false
Vue.use(ElementUI,{size:'small'}); //medium, small, mini
Vue.use(VueAxios, axios)
// Vue.use(VueRouter)


new Vue({
  router,
  render: h => h(App)
}).$mount('#app')