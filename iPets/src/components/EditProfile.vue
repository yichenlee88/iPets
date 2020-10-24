<template>
  <b-row>
    <b-col class="coltitle" col="4" sm="4" md="4"> <p>姓名：</p></b-col>
    <b-col col="8" sm="8" md="8">
      <b-form-input
        class="InputClass"
        name="name"
        v-model="text"
        placeholder="Name"
      ></b-form-input>
    </b-col>
    <b-col class="coltitle" col="4" sm="4" md="4"> <p>使用者名稱：</p></b-col>
    <b-col col="8" sm="8" md="8">
      <b-form-input
        class="InputClass"
        name="username"
        v-model="text"
        placeholder="UserName"
      ></b-form-input>
    </b-col>
    <b-col class="coltitle" col="4" sm="4" md="4"> <p>生日：</p></b-col>
    <b-col col="8" sm="8" md="8">
      <b-form-input
        class="InputClass"
        name="birth"
        v-model="text"
        placeholder="Birth"
      ></b-form-input>
    </b-col>
    <b-col class="coltitle" col="4" sm="4" md="4"> <p>電子郵件：</p></b-col>
    <b-col col="8" sm="8" md="8">
      <b-form-input
        class="InputClass"
        name="email"
        v-model="text"
        placeholder="Email"
      ></b-form-input>
    </b-col>
    <b-col class="coltitle" col="4" sm="4" md="4"> <p>電話：</p></b-col>
    <b-col col="8" sm="8" md="8">
      <b-form-input
        class="InputClass"
        name="phone"
        v-model="text"
        placeholder="Phone"
      ></b-form-input>
    </b-col>
    <b-col class="coltitle" col="4" sm="4" md="4"> <p>性別：</p></b-col>
    <b-col col="8" sm="8" md="8">
      <b-form-input
        class="InputClass"
        name="gender"
        v-model="text"
        placeholder="Gender"
      ></b-form-input>
    </b-col>
    <b-col col="4" sm="4" md="4"></b-col>
    <b-col col="8" sm="8" md="8">
      <b-button class="ButtonClass">Send</b-button>
    </b-col>
  </b-row>
</template>

<script>
export default {
  name: "EditProfile"
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
