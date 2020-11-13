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
          <div>
            <b-dropdown
              id="dropdown-divider"
              variant="white"
              text="asdasd"
              right
              class="m-2"
            >
              <b-dropdown-item-button v-for="item in pets_list" :key="item.id"
                >{{ item.name }}
              </b-dropdown-item-button>
              <b-dropdown-divider></b-dropdown-divider>
              <b-dropdown-item-button>新增寵物</b-dropdown-item-button>
            </b-dropdown>
          </div>
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
      isSignedIn: false,
      selectedPet: ""
    };
  },
  created() {
    if (firebase.auth().currentUser) {
      this.isSignedIn = true;
    }
    this.selectedPet = this.$store.state.name;
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
    changePet() {
      console.log(this.selectedPet);
    }
  },
  computed: {
    pet() {
      return this.$store.state.pet;
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
