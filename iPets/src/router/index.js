import Vue from "vue";
import Router from "vue-router";
import home from "@/components/home";
import about from "@/components/about";
import dogInfo from "@/components/dogInfo";
import contact from "@/components/contact";
import login from "@/components/login";
import register from "@/components/register";
import petProfile from "@/components/petProfile";
import calendar from "@/components/calendar";
import manageArticle from "@/components/manageArticle";
import manageAdoption from "@/components/manageAdoption";
import manageContact from "@/components/manageContact";
import manage from "@/components/manage";
import { db } from "../db";
import dogScience from "@/components/dogScience";
import post from "@/components/post";
import identify from "@/components/identify";
import tunit from "@/components/tunit";
import album from "@/components/album";
import albumView from "@/components/albumView";
import setting from "@/components/setting";
import editProfile from "@/components/editProfile";
import addPetProfile from "@/components/addPetProfile";
import password from "@/components/password";
import mail from "@/components/mail";
import loginActivity from "@/components/loginActivity";
import FAQ from "@/components/FAQ";
import feedback from "@/components/feedback";
import pageNotFound from "@/components/pageNotFound";

// import { component } from "vue/types/umd";

const fAuth = db.auth();

Vue.use(Router);

let router = new Router({
  routes: [
    {
      path: "/",
      name: "home",
      component: home,
      meta: {
        requiresGuest: true
      }
    },
    {
      path: "/about",
      name: "about",
      component: about,
      meta: {
        requiresGuest: true,
        requiresAuth: true
      }
    },
    {
      path: "/dogInfo",
      name: "dogInfo",
      component: dogInfo,
      meta: {
        requiresGuest: true,
        requiresAuth: true
      }
    },
    {
      path: "/contact",
      name: "contact",
      component: contact,
      meta: {
        requiresGuest: true
      }
    },
    {
      path: "/login",
      name: "login",
      component: login,
      meta: {
        requiresGuest: true
      }
    },
    {
      path: "/register",
      name: "register",
      component: register,
      meta: {
        requiresGuest: true
      }
    },
    {
      path: "/petProfile",
      name: "petProfile",
      component: petProfile,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: "/calendar",
      name: "calendar",
      component: calendar,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: "/identify",
      name: "identify",
      component: identify,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: "/tunit",
      name: "tunit",
      component: tunit,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: "/dogScience",
      name: "dogScience",
      component: dogScience,
      meta: {
        requiresGuest: true,
        requiresAuth: true
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
          path: "manageContact",
          component: manageContact
        },
        {
          path: "manageAdoption",
          component: manageAdoption
        }
      ]
    },
    {
      path: "/post/:id",
      name: "post",
      component: post,
      meta: {
        requiresGuest: true
      }
    },
    {
      path: "/album",
      name: "album",
      component: album,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: "/setting",
      name: "setting",
      component: setting,
      meta: {
        requiresAuth: true
      },
      children: [
        {
          path: "editProfile",
          component: editProfile
        },
        {
          path: "addPetProfile",
          component: addPetProfile
        },
        {
          path: "password",
          component: password
        },
        {
          path: "mail",
          component: mail
        },
        {
          path: "loginActivity",
          component: loginActivity
        },
        {
          path: "FAQ",
          component: FAQ
        },
        {
          path: "feedback",
          component: feedback
        }
      ]
    },
    {
      path: "/albumView/:name",
      name: "albumView",
      component: albumView,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: "*",
      name: "pageNotFound",
      component: pageNotFound
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
