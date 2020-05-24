// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from "vue";
import App from "./App";
import router from "./router";
import { db } from "./db";
import * as VueGoogleMaps from "vue2-google-maps";

// Vuex
import store from "./_store/index";

// BootstrapVue
import { BootstrapVue, IconsPlugin } from "bootstrap-vue";
import "bootstrap/dist/css/bootstrap.css";
import "bootstrap-vue/dist/bootstrap-vue.css";

// BootstrapVue
Vue.use(BootstrapVue);
Vue.use(IconsPlugin);

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

Vue.use(VueGoogleMaps, {
  load: {
    key: "AIzaSyAuxtzj2J-9DBHV9vBjCaQ9uhR_zc6hqyU",
    libraries: "places" // necessary for places input
  }
});
