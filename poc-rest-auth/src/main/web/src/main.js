import Vue from 'vue'
import VueRouter from 'vue-router'
import VeeValidate from 'vee-validate'

import App from './App.vue'
import {routes} from './utils/routes'
import {SessionUtil} from './utils/SessionUtil'
import msg from './utils/vee-validate.pt_BR'

import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap/dist/js/bootstrap.js'
import 'sweetalert/dist/sweetalert.css'
import 'sweetalert/dist/sweetalert.min.js'

Vue.use(VueRouter);
Vue.use(VeeValidate, {
  locale: 'pt_BR',
  dictionary: {
    'pt_BR': {
      messages: msg
    }
  }
})

const router = new VueRouter({
  routes,
  mode: 'history'
})

new Vue({
  el: '#app',
  render: h => h(App),
  router
})
