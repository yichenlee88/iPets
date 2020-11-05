import Vue from "vue";
import Router from "vue-router";
import home from "@/components/home";
import about from "@/components/about";
import dogInfo from "@/components/dogInfo";
import contact from "@/components/contact";
import login from "@/components/login";
import register from "@/components/register";
import PetProfile from "@/components/PetProfile";
import Calendar from "@/components/Calendar";
import ManageArticle from "@/components/ManageArticle";
import ManageAdoption from "@/components/ManageAdoption";
import manageContact from "@/components/manageContact";
import manage from "@/components/Manage";
import { db } from "../db";
import dogScience from "@/components/dogScience";
import Post from "@/components/Post";
import Identify from "@/components/Identify";
import Tunit from "@/components/Tunit";
import Album from "@/components/Album";
import albumView from "@/components/albumView";
import Setting from "@/components/Setting";
import EditProfile from "@/components/EditProfile";
import EditPetProfile from "@/components/EditPetProfile";
import Password from "@/components/Password";
import Mail from "@/components/Mail";
import LoginActivity from "@/components/LoginActivity";
import FAQ from "@/components/FAQ";
import Feedback from "@/components/Feedback";
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
        requiresGuest: true
      }
    },
    {
      path: "/dogInfo",
      name: "dogInfo",
      component: dogInfo,
      meta: {
        requiresGuest: true
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
      path: "/identify",
      name: "Identify",
      component: Identify,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: "/tunit",
      name: "Tunit",
      component: Tunit,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: "/dogScience",
      name: "dogScience",
      component: dogScience,
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
          component: ManageArticle
        },
        {
          path: "manageContact",
          component: manageContact
        },
        {
          path: "manageAdoption",
          component: ManageAdoption
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
    },
    {
      path: "/setting",
      name: "Setting",
      component: Setting,
      meta: {
        requiresAuth: true
      },
      children: [
        {
          path: "editProfile",
          component: EditProfile
        },
        {
          path: "editPetProfile",
          component: EditPetProfile
        },
        {
          path: "password",
          component: Password
        },
        {
          path: "mail",
          component: Mail
        },
        {
          path: "loginActivity",
          component: LoginActivity
        },
        {
          path: "FAQ",
          component: FAQ
        },
        {
          path: "feedback",
          component: Feedback
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
