<template>
  <div id="app">
    <Navbar />
    <router-view />
  </div>
</template>

<script>
import Navbar from "./components/Navbar";
import { db } from "./db";
import { updateUserProfile } from "./firebase/user";
import { updatePetInfo } from "./firebase/pet";

const fAuth = db.auth();

export default {
  name: "App",
  components: {
    Navbar
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
  size: 28px;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  width: 100%;
  color: #2c3e50;
}

#footer {
  height: 50px;
  box-sizing: border-box;
  bottom: auto;
  width: 100%;
  background-color: #e3f2fd;
  flex: 0 0 auto;
}
</style>
