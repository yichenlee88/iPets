<template>
  <b-row>
    <b-col class="coltitle" col="4" sm="4" md="4"> <p>舊密碼：</p></b-col>
    <b-col col="8" sm="8" md="8">
      <b-form-input
        class="InputClass"
        name="oldPassword"
        v-model="text"
        placeholder="舊密碼"
      ></b-form-input>
    </b-col>
    <b-col class="coltitle" col="4" sm="4" md="4"> <p>新密碼：</p></b-col>
    <b-col col="8" sm="8" md="8">
      <b-form-input
        class="InputClass"
        name="newPassword"
        v-model="text"
        placeholder="新密碼"
      ></b-form-input>
    </b-col>
    <b-col class="coltitle" col="4" sm="4" md="4"> <p>確認新密碼：</p></b-col>
    <b-col col="8" sm="8" md="8">
      <b-form-input
        class="InputClass"
        name="confirmNewPassword"
        v-model="text"
        placeholder="確認新密碼"
      ></b-form-input>
    </b-col>
    <b-col col="4" sm="4" md="4"></b-col>
    <b-col col="8" sm="8" md="8">
      <b-button class="ButtonClass">送出</b-button>
    </b-col>
  </b-row>
</template>

<script>
export default {
  name: "Password"
};
</script>

<style scoped>
.coltitle {
  padding: 16px 0;
  text-align: right;
}

.InputClass {
  max-width: 70%;
  border-radius: 40px;
}

.ButtonClass {
  height: 48px;
  width: 70%;
  border-radius: 40px;
  margin-bottom: 20px;
  background: -webkit-linear-gradient(
    left,
    rgb(148, 115, 221) 0%,
    rgb(26, 201, 228) 100%
  );
}
</style>
