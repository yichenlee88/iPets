<template>
  <div id="app">
    <nav
      class="navbar navbar-expand-lg navbar-light"
      style="background-color: #e3f2fd;"
    >
      <a class="navbar-brand" href="#/">iPets</a>
      <button
        class="navbar-toggler"
        type="button"
        data-toggle="collapse"
        data-target="#navbarSupportedContent"
        aria-controls="navbarSupportedContent"
        aria-expanded="false"
        aria-label="Toggle navigation"
      >
        <span class="navbar-toggler-icon"></span>
      </button>

      <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
          <li class="nav-item">
            <a class="nav-link" href="#/about" v-if="!isLogIn">關於我們</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="#/dogScience" v-if="!isLogIn">狗狗科普</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="#/dogInfo" v-if="!isLogIn"
              >人與狗的關係</a
            >
          </li>
          <li class="nav-item">
            <a class="nav-link" href="#/contact" v-if="!isLogIn">聯繫我們</a>
          </li>
        </ul>

        <div class="collapse navbar-collapse" id="navbarSupportedContent">
          <ul class="navbar-nav mr-auto">
            <li class="nav-item">
              <a class="nav-link" href="#/homeLogin" v-if="isLogIn">行事曆</a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="#/" v-if="isLogIn">相簿</a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="#/" v-if="isLogIn">會員專區</a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="#/" v-if="isLogIn">設定</a>
            </li>
          </ul>
        </div>
        <BUtton class="btn my-2 my-sm-0" v-if="isLogIn" @click="logout">
          登出
        </BUtton>
        <!-- <ul class="navbar-nav mr-5 mt-2 mt-lg-0" v-if="isLogIn">
          <li class="nav-item">
            <a class="nav-link" @click="logout">
              <i class="fas fa-sign-in-alt" style="size:12px"></i>登出
            </a>
          </li>
        </ul> -->

        <form class="form-inline my-2 my-lg-0">
          <ul class="navbar-nav my-2 my-sm-0" v-if="!isLogIn">
            <li class="nav-item">
              <a class="nav-link" href="#/login">
                <i class="fas fa-sign-in-alt" style="size:12px"></i>登入
              </a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="#/register">
                <i class="far fa-user" style="size:12px"></i>註冊
              </a>
            </li>
          </ul>
          <input
            class="form-control mr-sm-2"
            type="search"
            placeholder="Search"
            aria-label="Search"
          />
          <button class="btn btn-outline-success my-2 my-sm-0" type="submit">
            Search
          </button>
        </form>
      </div>
    </nav>
    <router-view />
    <div id="footer">
      <div class="row">
        <div class="col-12 text-center h-50">
          <p>© 台北商業大學 資訊管理系 版權所有</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { db } from "./db";

const fAuth = db.auth();

export default {
  name: "App",
  data() {
    return {
      isLogIn: false
    };
  },
  created() {
    if (fAuth.currentUser) {
      this.isLogIn = true;
    }
  },
  methods: {
    logout: function(e) {
      fAuth.signOut().then(() => {
        this.$router.go({ path: this.$router.path });
      });
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
  text-align: center;
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
