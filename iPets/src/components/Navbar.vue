<template>
  <div>
    <b-navbar class="navbar-custom" toggleable="lg">
      <b-navbar-brand href="/" style="font-size: 30px;">iPets</b-navbar-brand>
      <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

      <b-collapse id="nav-collapse" is-nav>
        <b-navbar-nav>
          <b-nav-item href="#/about">關於我們</b-nav-item>
          <b-nav-item href="#/dogScience">狗狗科普</b-nav-item>
          <b-nav-item href="#/dogInfo">人與狗的關係</b-nav-item>
          <b-nav-item href="#/contact" v-if="!isSignedIn">聯繫我們</b-nav-item>
          <b-nav-item href="#/calendar" v-if="isSignedIn">行事曆</b-nav-item>
          <b-nav-item href="#/album" v-if="isSignedIn">相簿</b-nav-item>
          <b-nav-item href="#/identify" v-if="isSignedIn">寵物辨識</b-nav-item>
          <b-nav-item href="#/tunit" v-if="isSignedIn">風格轉換</b-nav-item>
          <b-nav-item href="#/setting/editProfile" v-if="isSignedIn"
            >設定</b-nav-item
          >
        </b-navbar-nav>

        <!-- Right aligned nav items -->
        <b-navbar-nav class="ml-auto">
          <b-nav-form v-if="!isSignedIn">
            <b-nav-item href="#/login"
              ><i class="fas fa-sign-in-alt" style="size:12px"></i
              >登入</b-nav-item
            >
            <b-nav-item href="#/register"
              ><i class="far fa-user" style="size:12px"></i>註冊</b-nav-item
            >
            <b-nav-item href="#/">網站導覽</b-nav-item>
          </b-nav-form>
          <b-nav-form v-if="isSignedIn">
            <b-nav-item>
              <b-dropdown
                variant="white"
                :text="current_pet_name"
                right
                class="m-2"
              >
                <b-dropdown-item
                  v-for="item in pets_list"
                  :key="item.id"
                  :value="item.id"
                  :disabled="item.name === current_pet_name"
                  @click="changeCurrentPet(item.id, item.name)"
                  >{{ item.name }}
                </b-dropdown-item>
                <b-dropdown-divider></b-dropdown-divider>
                <b-dropdown-item to="/newPet">新增寵物</b-dropdown-item>
              </b-dropdown>
            </b-nav-item>
            <b-nav-item @click="logout">
              <i class="fas fa-sign-in-alt" style="size:12px"></i
              >登出</b-nav-item
            >
          </b-nav-form>
        </b-navbar-nav>
      </b-collapse>
    </b-navbar>
  </div>
</template>

<script>
import firebase from "firebase/app";
import { updateSinglePetInfo } from "../firebase/pet";
export default {
  name: "navbar",
  data() {
    return {
      isSignedIn: false,
      selectedPet: "3qvZxKgG4Gmzyx1ZEKG1"
    };
  },
  created(event) {
    if (firebase.auth().currentUser) {
      this.isSignedIn = true;
    }
  },
  methods: {
    logout: function(e) {
      firebase
        .auth()
        .signOut()
        .then(() => {
          this.$router.go({ path: this.$router.path });
        });
    },
    async changeCurrentPet(id, name) {
      await this.$store.commit("UpdateCurrentPet", {
        current_pet_id: id,
        current_pet_name: name
      });
      await updateSinglePetInfo(
        this.$store,
        this.$store.state.uid,
        this.$store.state.current_pet_id
      );
    }
  },
  computed: {
    current_pet_id() {
      return this.$store.state.current_pet_id;
    },
    current_pet_name() {
      return this.$store.state.current_pet_name;
    },
    pets_list() {
      return this.$store.state.pets_list;
    }
  }
};
</script>
<style>
.navbar-brand,
.form-input,
.botton,
.nav-item {
  height: auto;
  width: auto;
  font-size: 22px;
}

@media screen and (max-width: 768px) {
  .navbar-brand,
  .form-input,
  .botton,
  .nav-item {
    background-size: auto 350px !important;
  }
}
</style>
