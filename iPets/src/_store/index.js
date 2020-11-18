import Vue from "vue";
import Vuex from "vuex";

Vue.use(Vuex);

const store = new Vuex.Store({
  state: {
    // user
    uid: null,
    name: null,
    email: null,
    photoURL: null,
    // pet
    current_pet_id: null,
    current_pet_name: null,
    pet: null,
    month_calendar: null,
    // global pets
    pets_list: null
  },
  mutations: {
    updateUserProfile(state, payload) {
      state.uid = payload.uid;
      state.name = payload.name;
      state.email = payload.email;
      state.photoURL = payload.photoURL;
    },
    getUserPetsList(state, payload) {
      state.pets_list = payload.pets_list;
    },
    UpdateCurrentPet(state, payload) {
      state.current_pet_id = payload.current_pet_id;
      state.current_pet_name = payload.current_pet_name;
    },
    updateSinglePetInfo(state, payload) {
      state.pet = payload.pet;
    },
    updateMonthCalendar(state, payload) {
      state.month_calendar = payload.month_calendar;
    }
  }
});

export default store;
