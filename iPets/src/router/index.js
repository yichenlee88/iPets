import Vue from 'vue'
import Router from 'vue-router'
import Home from '@/components/Home'
import About from '@/components/About'
import DogInfo from '@/components/DogInfo'
import Contact from '@/components/Contact'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Home',
      component: Home
    },
    {
      path: '/about',
      name: 'About',
      component: About
    },
    {
      path: '/dogInfo',
      name: 'DogInfo',
      component: DogInfo
    },
    {
      path: '/contact',
      name: 'Contact',
      component: Contact
    }
  ]
})
