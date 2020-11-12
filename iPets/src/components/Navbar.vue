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
          <b-nav-form>
            <b-nav-item href="#/login" v-if="!isSignedIn"
              ><i class="fas fa-sign-in-alt" style="size:12px"></i
              >登入</b-nav-item
            >
            <b-nav-item href="#/register" v-if="!isSignedIn"
              ><i class="far fa-user" style="size:12px"></i>註冊</b-nav-item
            >
          </b-nav-form>
          <b-nav-item href="#/" v-if="!isSignedIn">網站導覽</b-nav-item>
          <b-nav-form>
            <b-nav-item @click="logout" v-if="isSignedIn"
              ><i class="fas fa-sign-in-alt" style="size:12px"></i
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
export default {
  name: "navbar",
  data() {
    return {
      isSignedIn: false
    };
  },
  created() {
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
    }
  },
  computed: {
    name() {
      return this.$store.state.name;
    }
  }
};
</script>
<style>
/* .navbar-custom {
  background-color: #d2e9ff;
} */

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
