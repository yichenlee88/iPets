import Vue from "vue";
import Vuex from "vuex";

Vue.use(Vuex);

const store = new Vuex.Store({
  state: {
    uid: null,
    name: null,
    email: null,
    photoURL: null,
    pet: null,
    pet_doc_id: null,
    pets_list: null,
    pet_info: null,
    month_calendar: null
  },
  mutations: {
    updateUserProfile(state, payload) {
      state.uid = payload.uid;
      state.name = payload.name;
      state.email = payload.email;
      state.photoURL = payload.photoURL;
    },
    updatePetInfo(state, payload) {
      state.pet = payload.pet;
      state.pet_doc_id = payload.pet_doc_id;
    },
    getUserPetsList(state, payload) {
      state.pets_list = payload.pets_list;
    },
    updateInfo(state, payload) {
      state.pet_info = payload.pet_info;
    },
    updateMonthCalendar(state, payload) {
      state.month_calendar = payload.month_calendar;
    }
  }
});

export default store;
