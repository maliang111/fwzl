import Vue from 'vue'
import Router from 'vue-router'
import Home from '@/view/home'
import CzxxList from '@/view/fwxx/CzxxList'
import grxx from '@/view/yhxx/Grxx'
import HouseList from '@/view/fwxx/HouseList'
import HouseForm from '@/components/HouseForm'
Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'home',
      component: Home,
      children : [
        {
          path : "/fwlb",
          name : "fwlb",
          component : HouseList
        },
        {
          path : "/czxx",
          name : "czxx",
          component : CzxxList
        },
        {
          path : "/grxx",
          name : "grxx",
          component : grxx
        },
        {
          path : "/fbxx",
          name : "fbxx",
          component : HouseForm
        }
      ]
    }
  ]
})
