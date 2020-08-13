// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from "vue";
import App from "./App";
import router from "./router";
import { db } from "./db";

// Vuex
import store from "./_store/index";

// BootstrapVue
import { BootstrapVue, IconsPlugin } from "bootstrap-vue";
import "bootstrap/dist/css/bootstrap.css";
import "bootstrap-vue/dist/bootstrap-vue.css";

// VueGoogleMaps
import * as VueGoogleMaps from "vue2-google-maps";

// vue2-datepicker
import DatePicker from "vue2-datepicker";
import "vue2-datepicker/index.css";
import "vue2-datepicker/locale/zh-cn";

// ------------------------------------------------------

// BootstrapVue
Vue.use(BootstrapVue);
Vue.use(IconsPlugin);

// VueGoogleMaps
Vue.use(VueGoogleMaps, {
  load: {
    key: "AIzaSyAYCtXOCCgcccdDBl9hCHmSd_m6gy4YgUw",
    libraries: "places" // necessary for places input
  }
});

// vue2-datepicker
Vue.use(DatePicker);

// ------------------------------------------------------

Vue.config.productionTip = false;

const fAuth = db.auth();

let app;
fAuth.onAuthStateChanged(() => {
  if (!app) {
    app = new Vue({
      el: "#app",
      router,
      store,
      components: { App },
      template: "<App/>"
    });
  }
});
