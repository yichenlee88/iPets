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
import ManageArticle from "@/components/ManageArticle";
import ManageAdoption from "@/components/ManageAdoption";
import manageContact from "@/components/manageContact";
import manage from "@/components/Manage";
import { db } from "../db";
import DogScience from "@/components/DogScience";
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
      path: "/albumView/:name",
      name: "albumView",
      component: albumView,
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
