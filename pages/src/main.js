// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import store from './store'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import axios from 'axios';
import qs from 'querystring';
Vue.use(ElementUI);

Vue.config.productionTip = false;

Vue.prototype.$qs = qs;

axios.defaults.withCredentials=true;

axios.defaults.baseURL = "http://" + location.host + "/fwzl";
// axios.defaults.baseURL = "http://127.0.0.1:8088/fwzl";
Vue.prototype.$axios = axios;
// axios.defaults.baseURL = "'" + process.env.BASE_URL + "'";

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store : store,
  components: { App },
  template: '<App/>'
});
