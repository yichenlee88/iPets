// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from "vue";
import App from "./App";
import router from "./router";
import { db } from "./db";

// Vuetify
import Vuetify from "vuetify";
import "vuetify/dist/vuetify.min.css";
import VueTextareaAutosize from "vue-textarea-autosize";
import "material-design-icons-iconfont/dist/material-design-icons.css";

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
// Vuetify
Vue.use(Vuetify);
Vue.use(VueTextareaAutosize);

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
      vuetify: new Vuetify(),
      components: { App },
      template: "<App/>"
    });
  }
});
