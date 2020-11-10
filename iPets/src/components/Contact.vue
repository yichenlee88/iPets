<template>
  <b-container>
    <b-row class="ContactTitle text-center"
      ><b-col><h1>歡迎提出您任何的問題</h1></b-col></b-row
    >
    <b-row>
      <b-col class="col col-3 col-sm-3 col-md-3"
        ><img
          class="imgcenter"
          src="../assets/contact-us-02.png"
          width="auto"
          alt="image slot"
      /></b-col>
      <b-col class="col col-12 col-sm-6 col-md-6">
        <div style="margin-top: 20%">
          <b-form @submit="addContact">
            <b-form-input
              class="InputClass"
              name="userName"
              v-model="userName"
              placeholder="使用者名稱"
            ></b-form-input>
            <b-form-select
              class="InputClass center"
              name="problemType"
              v-model="problemType"
              :options="options"
            ></b-form-select
            ><b-form-input
              class="InputClass"
              name="email"
              v-model="email"
              placeholder="電子郵件"
            ></b-form-input>
            <b-form-textarea
              class="TextareaClass"
              name="description"
              id="description"
              v-model="description"
              placeholder="您遇到的問題是..."
              maxlength
            ></b-form-textarea
            ><b-button type="submit" class="ButtonClass">送出</b-button></b-form
          >
        </div></b-col
      >
      <b-col class="col col-3 col-sm-3 col-md-3"
        ><img
          class="imgcenter"
          src="../assets/contact-us-03.png"
          width="auto"
          alt="image slot"
      /></b-col>
    </b-row>

    <b-row class="ContactTitle text-center"
      ><b-col><h1>加入我們</h1></b-col></b-row
    >
    <b-card img-src="../static/img/join-us.png" img-alt="Card image" img-top>
      <b-card-text>
        <b-row
          ><b-col col="6"
            ><h3 class="text-center">歡迎加入我們的大家庭</h3>
            <p class="text-center">
              加入我們的會員
            </p>
            <p class="text-center">可以更快速的收到最新消息</p>
            <p class="text-center">掃描右側的QR Code或著點擊加入</p>
            <p class="text-center">就可以成為我們的夥伴囉~</p></b-col
          >
          <b-col col="6"
            ><b-img class="Qrcenter" src="../static/img/M.png"></b-img
            ><a href="https://lin.ee/GZOoOSt"
              ><img
                class="LinkCenter"
                src="https://scdn.line-apps.com/n/line_add_friends/btn/zh-Hant.png"
                alt="加入好友"
                height="36"/></a></b-col
        ></b-row>
      </b-card-text>
    </b-card>
  </b-container>
</template>

<script>
import { db } from "../db";
const fStore = db.firestore();

export default {
  name: "contact",
  data() {
    return {
      problemType: null,
      userName: "",
      email: "",
      description: "",
      options: [
        { value: "null", text: "選擇您的問題型態" },
        { value: "帳號安全", text: "帳號安全" },
        { value: "關於行事曆", text: "關於行事曆" },
        { value: "關於倒數計時器", text: "關於倒數計時器" },
        { value: "關於相簿", text: "關於相簿" },
        { value: "關於辨識功能", text: "關於辨識功能" },
        { value: "關於風格轉換", text: "關於風格轉換" },
        { value: "給iPets的建議", text: "給iPets的建議" }
      ],
      timestamp: ""
    };
  },
  methods: {
    addContact(e) {
      e.preventDefault();
      var docRef = fStore.collection("contact").doc();
      docRef
        .set({
          userName: this.userName,
          email: this.email,
          problemType: this.problemType,
          description: this.description,
          timestamp: new Date()
        })
        .then(() => {
          this.$router.go({ path: this.$router.path });
        })
        .finally(() => {
          alert("您的建議已經送出囉~!");
        });
    }
  }
};
</script>

<style>
/* .ContactTitle {
  margin-top: 100px;
  margin-bottom: 100px;
} */
.imgcenter {
  display: block;
  margin-left: auto;
  margin-right: auto;
  width: 100%;
}

.center {
  display: block;
  margin-left: auto;
  margin-right: auto;
}

.Qrcenter {
  display: block;
  margin-left: auto;
  margin-right: auto;
  width: 25%;
}

.LinkCenter {
  display: block;
  margin-left: auto;
  margin-right: auto;
  border-radius: 5px;
}

.InputClass {
  height: 48px;
  max-width: auto;
  border-radius: 40px;
  margin-bottom: 20px;
}

.TextareaClass {
  height: 120px;
  max-width: auto;
  border-radius: 16px;
  margin-bottom: 20px;
}

.ButtonClass {
  height: 48px;
  width: 100%;
  border-radius: 40px;
  margin-bottom: 20px;
  background: -webkit-linear-gradient(
    left,
    rgb(148, 115, 221) 0%,
    rgb(26, 201, 228) 100%
  );
}

.card {
  margin-bottom: 100px;
}
</style>
