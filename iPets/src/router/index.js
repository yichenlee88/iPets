import Vue from "vue";
import Router from "vue-router";
import Home from "@/components/Home";
import About from "@/components/About";
import DogInfo from "@/components/DogInfo";
import Contact from "@/components/Contact";
import Login from "@/components/Login";
import Register from "@/components/Register";
import HomeLogin from "@/components/HomeLogin";
import Manager from "@/components/Manager";
import { db } from "../db";
import DogScience from "@/components/DogScience";
import Post from "@/components/Post";

const fAuth = db.auth();

Vue.use(Router);

let router = new Router({
  routes: [
    {
      path: "/",
      name: "Home",
      component: Home,
      meta: {
        requiresGuest: true
      }
    },
    {
      path: "/about",
      name: "About",
      component: About,
      meta: {
        requiresGuest: true
      }
    },
    {
      path: "/dogInfo",
      name: "DogInfo",
      component: DogInfo,
      meta: {
        requiresGuest: true
      }
    },
    {
      path: "/contact",
      name: "Contact",
      component: Contact,
      meta: {
        requiresGuest: true
      }
    },
    {
      path: "/login",
      name: "Login",
      component: Login,
      meta: {
        requiresGuest: true
      }
    },
    {
      path: "/register",
      name: "Register",
      component: Register,
      meta: {
        requiresGuest: true
      }
    },
    {
      path: "/homeLogin",
      name: "HomeLogin",
      component: HomeLogin,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: "/dogScience",
      name: "DogScience",
      component: DogScience,
      children: [
        {
          path: "post/:id",
          name: "Post",
          component: Post
        }
      ],
      meta: {
        requiresGuest: true
      }
    },
    {
      path: "/manager",
      name: "Manager",
      component: Manager,
      meta: {
        requiresGuest: true
      }
    }
  ]
});

router.beforeEach((to, from, next) => {
  if (to.matched.some(record => record.meta.requiresAuth)) {
    // check if NOT logged in
    if (!fAuth.currentUser) {
      // Go to login
      next("/");
    } else {
      next();
    }
  } else if (to.matched.some(record => record.meta.requiresGuest)) {
    // check if logged in
    if (fAuth.currentUser) {
      // Go to login
      next("/HomeLogin");
    } else {
      next();
    }
  } else {
    next();
  }
});

export default router;
