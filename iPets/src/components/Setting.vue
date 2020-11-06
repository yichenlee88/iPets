<template>
  <div class="screen" style="background-color:#F5F5F5">
    <div class="center">
      <b-row>
        <b-col cols="6" sm="4" md="4">
          <b-list-group>
            <b-list-group-item
              ><router-link to="/setting/editProfile"
                >編輯個人資訊</router-link
              ></b-list-group-item
            >
            <b-list-group-item
              ><router-link to="/setting/editPetProfile"
                >編輯寵物資訊</router-link
              ></b-list-group-item
            >
            <b-list-group-item
              ><router-link to="/setting/password"
                >更改密碼</router-link
              ></b-list-group-item
            >
            <b-list-group-item
              ><router-link to="/setting/mail"
                >電子郵件與簡訊</router-link
              ></b-list-group-item
            >
            <b-list-group-item
              ><router-link to="/setting/loginActivity"
                >登入活動</router-link
              ></b-list-group-item
            >
            <b-list-group-item
              ><router-link to="/setting/FAQ"
                >常見問題</router-link
              ></b-list-group-item
            >
            <b-list-group-item
              ><router-link to="/setting/feedback"
                >聯絡我們</router-link
              ></b-list-group-item
            >
          </b-list-group>
        </b-col>
        <b-col
          cols="6"
          sm="8"
          md="8"
          style="overflow: scroll; height: 75vh; margin-top:15px; background-color:#FFFFFF; border: 1px solid; border-color: #ADADAD;"
        >
          <router-view></router-view>
        </b-col>
      </b-row>

      <!-- <b-row>
              <b-col col="6" sm="4" md="4">
                <nav class="nav flex-column nav-pills nav-fill">
            <a class="nav-link" href="#">編輯寵物資訊</a>
            <a class="nav-link" href="#">更改密碼</a>
            <a class="nav-link" href="#">電子郵件與簡訊</a>
            <a class="nav-link" href="#">登入活動</a>
            <a class="nav-link" href="#">常見問題</a>
            <a class="nav-link" href="#">聯絡我們</a>
            <a class="nav-link" href="#">登入活動</a>
          </nav>
        <b-list-group>
          <b-list-group-item href="#profiles">編輯個人資訊</b-list-group-item>
          <b-list-group-item href="#pet">編輯寵物資訊</b-list-group-item>
          <b-list-group-item href="#">更改密碼</b-list-group-item>
          <b-list-group-item href="#">電子郵件與簡訊</b-list-group-item>
          <b-list-group-item href="#">登入活動</b-list-group-item>
          <b-list-group-item href="#">常見問題</b-list-group-item>
          <b-list-group-item href="#">聯絡我們</b-list-group-item>
          <b-list-group-item href="#">登入活動</b-list-group-item>
        </b-list-group>
      </b-col>
      <b-col col="6" sm="8" md="8">電子郵件與簡訊</b-col>

<b-card no-body>
        <div class="col-4 sm-4 md-4">
          <b-tabs pills card vertical end>
            <b-tab title="編輯個人資訊"></b-tab>
            <b-tab title="編輯寵物資訊"></b-tab>
            <b-tab title="更改密碼"></b-tab>
            <b-tab title="電子郵件與簡訊"></b-tab>
            <b-tab title="登入活動"></b-tab>
            <b-tab title="常見問題"></b-tab>
            <b-tab title="聯絡我們"></b-tab>
            <b-tab title="登入活動"></b-tab>
          </b-tabs>
        </div>
      </b-card>

      <div>
        <b-card no-body>
          <b-tabs pills card vertical nav-wrapper-class="w-50">
            <b-tab title="Tab 1" active
              ><b-card-text>Tab contents 1</b-card-text></b-tab
            >
            <b-tab title="Tab 2"
              ><b-card-text>Tab contents 2</b-card-text></b-tab
            >
            <b-tab title="Tab 3"
              ><b-card-text>Tab contents 3</b-card-text></b-tab
            >
          </b-tabs>
        </b-card>
      </div>

        <b-col col="6" sm="4" md="4">
          <b-list-group>
            <b-list-group-item href="#">編輯個人資訊</b-list-group-item>
            <b-list-group-item href="#">編輯寵物資訊</b-list-group-item>
            <b-list-group-item href="#">更改密碼</b-list-group-item>
            <b-list-group-item href="#">電子郵件與簡訊</b-list-group-item>
            <b-list-group-item href="#">登入活動</b-list-group-item>
            <b-list-group-item href="#">常見問題</b-list-group-item>
            <b-list-group-item href="#">聯絡我們</b-list-group-item>
            <b-list-group-item href="#">登入活動</b-list-group-item>
          </b-list-group></b-col
        >
        <b-col col="12" sm="8" md="8">電子郵件與簡訊</b-col>
      </b-row> -->
    </div>
  </div>
</template>

<script>
export default {
  name: "setting"
};
</script>

<style scoped>
.center {
  display: block;
  margin-left: auto;
  margin-right: auto;
  width: 70%;
}

.screen {
  height: 80vh;
}

a {
  color: inherit;
}
</style>
