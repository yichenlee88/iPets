<template>
  <b-container>
    <b-row class="ContactTitle text-center"
      ><b-col><h1>歡迎提出您任何的問題</h1></b-col></b-row
    >
    <b-row>
      <b-col col="3" sm="3" md="3"
        ><img
          class="imgcenter"
          src="../assets/contact-us-02.png"
          width="auto"
          alt="image slot"
      /></b-col>
      <b-col col="6" sm="6" md="6">
        <div style="margin-top: 20%">
          <b-form-input
            class="InputClass"
            v-model="text"
            placeholder="First Name"
          ></b-form-input>
          <b-form-input
            class="InputClass"
            v-model="text"
            placeholder="Last Name"
          ></b-form-input
          ><b-form-input
            class="InputClass"
            v-model="email"
            placeholder="Your Email"
          ></b-form-input>
          <b-form-textarea
            class="TextareaClass"
            id="textarea-default"
            placeholder="Your Question..."
            maxlength
          ></b-form-textarea
          ><b-button class="ButtonClass">Send</b-button>
        </div></b-col
      >
      <b-col col="3" sm="3" md="3"
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
export default {
  name: "Contact"
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
  width: 50%;
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
