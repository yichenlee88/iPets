<template>
  <b-row>
    <b-col class="coltitle" cols="4" sm="4" md="4">
      <p>舊密碼：</p>
    </b-col>
    <b-col cols="8" sm="8" md="8">
      <b-form-input
        class="InputClass"
        id="currentPassword"
        v-model="currentPassword"
        placeholder="舊密碼"
      ></b-form-input>
    </b-col>
    <b-col class="coltitle" cols="4" sm="4" md="4">
      <p>新密碼：</p>
    </b-col>
    <b-col cols="8" sm="8" md="8">
      <b-form-input class="InputClass" id="newPassword" v-model="newPassword" placeholder="新密碼"></b-form-input>
    </b-col>
    <b-col class="coltitle" cols="4" sm="4" md="4">
      <p>確認新密碼：</p>
    </b-col>
    <b-col cols="8" sm="8" md="8">
      <b-form-input
        class="InputClass"
        id="confirmNewPassword"
        v-model="againPassword"
        placeholder="確認新密碼"
      ></b-form-input>
    </b-col>
    <b-col cols="4" sm="4" md="4"></b-col>
    <b-col cols="8" sm="8" md="8">
      <b-button
        class="ButtonClass"
        @click="checkPassword(againPassword, newPassword);updatePassword(currentPassword, newPassword)"
      >送出</b-button>
    </b-col>
  </b-row>
</template>

<script>
import firebase from "firebase";

export default {
  data() {
    return {
      user: "",
      currentPassword: "",
      firebasePassword: "",
      newPassword: "",
      againPassword: "",
      check: false
    };
  },
  methods: {
    updatePassword(currentPassword, newPassword) {
      var user = firebase.auth().currentUser;
      const credential = firebase.auth.EmailAuthProvider.credential(
        user.email,
        currentPassword
      );
      if (this.check) {
        user
          .reauthenticateWithCredential(credential)
          .then(function() {
            user
              .updatePassword(newPassword)
              .then(function() {
                console.log(newPassword);
                alert("更改成功！");
                // Update successful.
              })
              .catch(function(error) {
                console.log(error);
              });
          })
          .catch(function(error) {
            console.log(error);
            alert("密碼輸入錯誤");
          });
        this.currentPassword = "";
        this.newPassword = "";
        this.againPassword = "";
      }
    },
    checkPassword(againPassword, newPassword) {
      if (againPassword === newPassword) {
        this.check = true;
      } else {
        alert("兩次密碼不相等，請再次確認密碼");
        this.check = false;
      }
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
