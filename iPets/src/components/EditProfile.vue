<template>
  <b-row>
    <b-col class="coltitle" cols="4" sm="4" md="4">
      <p>姓名：</p>
    </b-col>
    <b-col cols="8" sm="8" md="8">
      <b-form-input class="InputClass" name="name" v-model="name" placeholder="Name"></b-form-input>
    </b-col>
    <b-col class="coltitle" cols="4" sm="4" md="4">
      <p>使用者名稱：</p>
    </b-col>
    <b-col cols="8" sm="8" md="8">
      <b-form-input class="InputClass" name="userName" v-model="userName" placeholder="userName"></b-form-input>
    </b-col>
    <b-col class="coltitle" cols="4" sm="4" md="4">
      <p>生日：</p>
    </b-col>
    <b-col cols="8" sm="8" md="8">
      <b-form-input class="InputClass" name="birth" v-model="birth" placeholder="Birth"></b-form-input>
    </b-col>
    <b-col class="coltitle" cols="4" sm="4" md="4">
      <p>電子郵件：</p>
    </b-col>
    <b-col cols="8" sm="8" md="8">
      <b-form-input class="InputClass" name="email" v-model="email" placeholder="Email"></b-form-input>
    </b-col>
    <b-col class="coltitle" cols="4" sm="4" md="4">
      <p>電話：</p>
    </b-col>
    <b-col cols="8" sm="8" md="8">
      <b-form-input class="InputClass" name="phone" v-model="phone" placeholder="Phone"></b-form-input>
    </b-col>
    <b-col class="coltitle" cols="4" sm="4" md="4">
      <p>性別：</p>
    </b-col>
    <b-col cols="8" sm="8" md="8">
      <b-form-input class="InputClass" name="gender" v-model="gender" placeholder="Gender"></b-form-input>
    </b-col>
    <b-col cols="4" sm="4" md="4"></b-col>
    <b-col cols="8" sm="8" md="8">
      <b-button class="ButtonClass" @click="updateProfile">送出</b-button>
    </b-col>
  </b-row>
</template>

<script>
import { db } from "../db";
import firebase from "firebase";
const fStore = db.firestore();

export default {
  name: "editProfile",
  data() {
    return {
      name: "",
      userName: "",
      birth: "",
      email: "",
      phone: "",
      gender: ""
    };
  },
  mounted() {
    this.getUserProfile();
  },
  methods: {
    getUserProfile() {
      let uid = firebase.auth().currentUser.uid;
      fStore
        .collection("users")
        .doc(uid)
        .get()
        .then(function(doc) {
          console.log(doc);
        });
    },
    updateProfile() {
      let uid = firebase.auth().currentUser.uid;
      fStore
        .collection("users")
        .doc(uid)
        .add({
          name: this.name,
          email: this.email,
          userName: this.userName,
          address: this.address,
          userGender: this.gender,
          phone: this.phone,
          userBirth: this.birth
        });
    }
  }
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
  background: -webkit-linear-gradient(
    left,
    rgb(148, 115, 221) 0%,
    rgb(26, 201, 228) 100%
  );
}
</style>
