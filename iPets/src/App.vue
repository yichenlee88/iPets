<template>
  <div id="app">
    <Navbar />
    <router-view />
    <Footer />
  </div>
</template>

<script>
import Navbar from "./components/Navbar";
import Footer from "./components/Footer";
import { db } from "./db";
import { updateUserProfile } from "./firebase/user";
import { updatePetInfo } from "./firebase/pet";

const fAuth = db.auth();

export default {
  name: "App",
  components: {
    Navbar,
    Footer
  },
  data() {
    return {
      isLogIn: false,
      Height: 0
    };
  },
  created() {
    if (fAuth.currentUser) {
      var uid = fAuth.currentUser.uid;
      updateUserProfile(this.$store, uid);
      updatePetInfo(this.$store, uid);
      this.isLogIn = true;
    }
  }
};
</script>

<style>
#app {
  font-family: "Microsoft JhengHei", Helvetica, Arial, sans-serif;
  size: 20px;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  width: 100%;
  color: #2c3e50;
}
</style>
