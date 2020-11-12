<template>
  <div id="Manage">
    <b-container v-if="auth">
      <center>
        <b-row class="justify-content-md-center">
          <b-col cols="6" sm="6" md="6">
            <p>驗證碼：</p>
            <b-form-input class="InputClass" name="password" v-model="password"></b-form-input>
            <b-button class="ButtonClass" @click="checkPassword">送出</b-button>
          </b-col>
        </b-row>
      </center>
    </b-container>
    <b-container v-else>
      <b-row class="my-1" style="text-align:center">
        <b-col cols="12">目前登入帳號：10646043</b-col>
        <b-col cols="12">管理員名稱：iPets</b-col>
        <b-col cols="12">管理權限：最高管理員</b-col>
      </b-row>
      <div class="center">
        <router-link to="/manage/manageArticle">管理文章</router-link>
        <router-link to="/manage/manageAdoption">管理領養機構</router-link>
        <router-link to="/manage/manageContact">管理使用者回饋</router-link>
        <router-view></router-view>
      </div>
    </b-container>
  </div>
</template>

<script>
export default {
  name: "manage",
  data() {
    return {
      auth: true,
      password: ""
    };
  },
  methods: {
    checkPassword() {
      if (this.password === "ntub10646043") {
        this.auth = false;
      } else {
        this.auth = true;
      }
    }
    // logout() {
    //   this.auth = true;
    // }
  }
};
</script>

<style>
.center {
  text-align: center;
}
.coltitle {
  padding: 16px 0;
  text-align: right;
}
.InputClass {
  max-width: 50%;
  border-radius: 40px;
}
.ButtonClass {
  height: 48px;
  width: 40%;
  border-radius: 40px;
  margin-top: 20px;
  border-width: 0px;
  background: -webkit-linear-gradient(
    left,
    rgb(148, 115, 221) 0%,
    rgb(26, 201, 228) 100%
  );
}
</style>
