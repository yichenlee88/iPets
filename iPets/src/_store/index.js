import Vue from "vue";
import Vuex from "vuex";

Vue.use(Vuex);

const store = new Vuex.Store({
  state: {
    uid: null,
    name: null,
    email: null,
    photoURL: null
  },
  mutations: {
    updateUserProfile(state, payload) {
      state.uid = payload.uid;
      state.name = payload.name;
      state.email = payload.email;
      state.photoURL = payload.photoURL;
    }
  }
});

export default store;
