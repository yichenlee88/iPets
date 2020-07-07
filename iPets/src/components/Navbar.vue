<template>
  <b-navbar toggleable="lg" variant="faded" type="light">
    <b-navbar-brand href="/" style="  font-size: 24px;">iPets</b-navbar-brand>
    <!-- Right aligned nav items -->
    <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>
    <b-navbar-nav class="ml-auto">
      <b-collapse id="nav-collapse" is-nav>
        <b-nav-item href="#/about" v-if="!isSignedIn">關於我們</b-nav-item>
        <b-nav-item href="#/dogScience" v-if="!isSignedIn">狗狗科普</b-nav-item>
        <b-nav-item href="#/dogInfo" v-if="!isSignedIn"
          >人與狗的關係</b-nav-item
        >
        <b-nav-item href="#/contact" v-if="!isSignedIn">聯繫我們</b-nav-item>
        <b-nav-item href="#/homeLogin" v-if="isSignedIn">行事曆</b-nav-item>
        <b-nav-item href="#/" v-if="isSignedIn">相簿</b-nav-item>
        <b-nav-item href="#/" v-if="isSignedIn">會員專區</b-nav-item>
        <b-nav-item href="#/" v-if="isSignedIn">設定</b-nav-item>
        <b-nav-form>
          <b-nav-item @click="logout" v-if="isSignedIn"
            ><i class="fas fa-sign-in-alt" style="size:12px"></i
            >登出</b-nav-item
          >
        </b-nav-form>
        <b-nav-form>
          <b-nav-item href="#/login" v-if="!isSignedIn"
            ><i class="fas fa-sign-in-alt" style="size:12px"></i
            >登入</b-nav-item
          >
          <b-nav-item href="#/register" v-if="!isSignedIn"
            ><i class="far fa-user" style="size:12px"></i>註冊</b-nav-item
          >
          <b-form-input
            class="mr-sm-2"
            placeholder="Search"
            v-if="!isSignedIn"
            style="margin-top:8px"
          ></b-form-input>
          <b-button
            class="my-2"
            type="submit"
            v-if="!isSignedIn"
            style="margin-top:10px"
            >Search</b-button
          >
        </b-nav-form>
      </b-collapse>
    </b-navbar-nav>
  </b-navbar>
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
.navbar-brand,
.form-input,
.botton,
.nav-item {
  height: 22px;
  width: 150px;
  font-size: 18px;
  text-align: center;
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
