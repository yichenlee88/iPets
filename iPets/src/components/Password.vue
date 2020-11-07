<template>
  <b-row>
    <b-col class="coltitle" cols="4" sm="4" md="4">
      <p>舊密碼：</p>
    </b-col>
    <b-col cols="8" sm="8" md="8">
      <b-form-input class="InputClass" name="oldPassword" v-model="oldPassword" placeholder="舊密碼"></b-form-input>
    </b-col>
    <b-col class="coltitle" cols="4" sm="4" md="4">
      <p>新密碼：</p>
    </b-col>
    <b-col cols="8" sm="8" md="8">
      <b-form-input class="InputClass" name="newPassword" v-model="password" placeholder="新密碼"></b-form-input>
    </b-col>
    <b-col class="coltitle" cols="4" sm="4" md="4">
      <p>確認新密碼：</p>
    </b-col>
    <b-col cols="8" sm="8" md="8">
      <b-form-input
        class="InputClass"
        name="confirmNewPassword"
        v-model="check"
        placeholder="確認新密碼"
      ></b-form-input>
    </b-col>
    <b-col cols="4" sm="4" md="4"></b-col>
    <b-col cols="8" sm="8" md="8">
      <b-button class="ButtonClass" @click="updatePassword">送出</b-button>
    </b-col>
  </b-row>
</template>

<script>
import { db } from "../db";
import firebase from "firebase";
// const fStore = db.firestore();
const fAuth = db.auth();

export default {
  name: "password",
  data() {
    return {
      user: "",
      oldPassword: "",
      password: "",
      check: ""
    };
  },
  mounted() {
    this.getPassword();
  },
  methods: {
    updatePassword() {
      if ((this.check = this.password)) {
        var user = firebase.auth().currentUser;
        var newPassword = fAuth.getASecureRandomPassword();
        user
          .updatePassword(newPassword)
          .then(function() {
            // Update successful.
          })
          .catch(function(error) {
            alert(error);
          });
      }
    },
    getPassword() {
      var user = firebase.auth().currentUser;
      this.password = user.password;
      // let uid = firebase.auth().currentUser.uid;
      // let snapshot = fStore
      //   .collection("users")
      //   .doc(uid)
      //   .get();
      // var newPassword = getASecureRandomPassword();
      // console.log(newPassword);
      // const events = [];
      // snapshot.forEach(doc => {
      //   let appData = doc.data();
      //   appData.id = doc.id;
      //   events.push(appData);
      // });
      // fStore.
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
  margin-bottom: 20px;
  background: -webkit-linear-gradient(
    left,
    rgb(148, 115, 221) 0%,
    rgb(26, 201, 228) 100%
  );
}
</style>
