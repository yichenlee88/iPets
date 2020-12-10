import Vue from "vue";
import Router from "vue-router";
import home from "@/components/home.vue";
import about from "@/components/about.vue";
import dogInfo from "@/components/dogInfo.vue";
import contact from "@/components/contact.vue";
import login from "@/components/login.vue";
import register from "@/components/register.vue";
import petProfile from "@/components/petProfile.vue";
import calendar from "@/components/calendar.vue";
import manageArticle from "@/components/manageArticle.vue";
import manageAdoption from "@/components/manageAdoption.vue";
import manageContact from "@/components/manageContact.vue";
import manage from "@/components/manage.vue";
import newPet from "@/components/newPet.vue";
import { db } from "../db.js";
import dogScience from "@/components/dogScience.vue";
import post from "@/components/post.vue";
import identify from "@/components/identify.vue";
import tunit from "@/components/tunit.vue";
import album from "@/components/album.vue";
import albumView from "@/components/albumView.vue";
import setting from "@/components/setting.vue";
import editProfile from "@/components/editProfile.vue";
// import addPetProfile from "@/components/addPetProfile.vue";
import password from "@/components/password.vue";
import mail from "@/components/mail.vue";
import loginActivity from "@/components/loginActivity.vue";
import FAQ from "@/components/FAQ.vue";
import account from "@/components/account.vue";
import feedback from "@/components/feedback.vue";
import pageNotFound from "@/components/pageNotFound.vue";

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
      meta: {}
    },
    {
      path: "/dogInfo",
      name: "dogInfo",
      component: dogInfo,
      meta: {}
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
      meta: {}
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
      meta: {}
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
      path: "/newPet",
      component: newPet,
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
        // {
        //   path: "addPetProfile",
        //   component: addPetProfile
        // },
        {
          path: "account",
          component: account
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
