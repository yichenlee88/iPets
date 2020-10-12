import Vue from "vue";
import Router from "vue-router";
import Home from "@/components/Home";
import About from "@/components/About";
import DogInfo from "@/components/DogInfo";
import Contact from "@/components/Contact";
import Login from "@/components/Login";
import Register from "@/components/Register";
import PetProfile from "@/components/PetProfile";
import Calendar from "@/components/Calendar";
import manageArticle from "@/components/manageArticle";
import manageAdoption from "@/components/manageAdoption";
import manageUser from "@/components/manageUser";
import manage from "@/components/manage";
import { db } from "../db";
import DogScience from "@/components/DogScience";
import Post from "@/components/Post";
import Member from "@/components/Member";
import Album from "@/components/Album";

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
      path: "/petProfile",
      name: "PetProfile",
      component: PetProfile,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: "/calendar",
      name: "Calendar",
      component: Calendar,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: "/member",
      name: "Member",
      component: Member,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: "/dogScience",
      name: "DogScience",
      component: DogScience,
      meta: {
        requiresGuest: true
      }
    },
    {
      path: "/manage",
      name: "manage",
      component: manage,
      meta: {
        requiresGuest: true
      },
      children: [
        {
          path: "manageArticle",
          component: manageArticle
        },
        {
          path: "manageUser",
          component: manageUser
        }, {
          path: "manageAdoption",
          component: manageAdoption
        }
      ]
    },
    {
      path: "/post/:id",
      name: "Post",
      component: Post,
      meta: {
        requiresGuest: true
      }
    },
    {
      path: "/album",
      name: "Album",
      component: Album,
      meta: {
        requiresAuth: true
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
      next("/petProfile");
    } else {
      next();
    }
  } else {
    next();
  }
});

export default router;
