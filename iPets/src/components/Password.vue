<template>
  <b-container>
    <b-row>
      <b-col cols="12" sm="12" md="12">
        <h3>更改密碼：</h3>
      </b-col>
      <b-col class="coltitle" cols="4" sm="4" md="4">
        <p>舊密碼：</p>
      </b-col>
      <b-col cols="8" sm="8" md="8">
        <div v-if="passwordCurrentPasswordHidden">
          <b-form-input
            type="password"
            class="InputClass"
            id="currentPassword"
            v-model="currentPassword"
            placeholder="舊密碼"
          ></b-form-input>
          <b-input-group-prepend class="mr-n2">
            <span class="Password display-eye fa fa-eye-slash" @click="hideCurrentPassword"></span>
          </b-input-group-prepend>
        </div>
        <div v-if="!passwordCurrentPasswordHidden">
          <b-form-input
            type="text"
            class="InputClass"
            id="currentPassword"
            v-model="currentPassword"
            placeholder="舊密碼"
          ></b-form-input>
          <b-input-group-prepend class="mr-n2">
            <span class="Password display-eye fa fa-eye" @click="showCurrentPassword"></span>
          </b-input-group-prepend>
        </div>
      </b-col>
      <b-col class="coltitle" cols="4" sm="4" md="4">
        <p>新密碼：</p>
      </b-col>
      <b-col cols="8" sm="8" md="8">
        <div v-if="passwordNewPasswordHidden">
          <b-form-input
            type="password"
            class="InputClass"
            id="newPassword"
            v-model="newPassword"
            placeholder="新密碼"
          ></b-form-input>
          <b-input-group-prepend class="mr-n2">
            <span class="Password display-eye fa fa-eye-slash" @click="hideNewPassword"></span>
          </b-input-group-prepend>
        </div>
        <div v-if="!passwordNewPasswordHidden">
          <b-form-input
            type="text"
            class="InputClass"
            id="newPassword"
            v-model="newPassword"
            placeholder="新密碼"
          ></b-form-input>
          <b-input-group-prepend class="mr-n2">
            <span class="Password display-eye fa fa-eye" @click="showNewPassword"></span>
          </b-input-group-prepend>
        </div>
      </b-col>
      <b-col class="coltitle" cols="4" sm="4" md="4">
        <p>確認新密碼：</p>
      </b-col>
      <b-col cols="8" sm="8" md="8">
        <div v-if="passwordConfirmNewPasswordHidden">
          <b-form-input
            type="password"
            class="InputClass"
            id="confirmNewPassword"
            v-model="confirmNewPassword"
            placeholder="確認新密碼"
          ></b-form-input>
          <b-input-group-prepend class="mr-n2">
            <span class="Password display-eye fa fa-eye-slash" @click="hideConfirmNewPassword"></span>
          </b-input-group-prepend>
        </div>
        <div v-if="!passwordConfirmNewPasswordHidden">
          <b-form-input
            type="text"
            class="InputClass"
            id="confirmNewPassword"
            v-model="confirmNewPassword"
            placeholder="確認新密碼"
          ></b-form-input>
          <b-input-group-prepend class="mr-n2">
            <span class="Password display-eye fa fa-eye" @click="showConfirmNewPassword"></span>
          </b-input-group-prepend>
        </div>
      </b-col>
      <b-col cols="4" sm="4" md="4"></b-col>
      <b-col cols="8" sm="8" md="8">
        <b-button
          class="ButtonClass"
          @click="
            checkPassword(confirmNewPassword, newPassword),
              updatePassword(currentPassword, newPassword),
              updateProfile_password(currentPassword),
              clearData()
          "
        >送出</b-button>
      </b-col>
    </b-row>
  </b-container>
</template>

<script>
import { db } from "../db";
import firebase from "firebase";
const fStore = db.firestore();

export default {
  data() {
    return {
      user: "",
      currentPassword: "",
      firebasePassword: "",
      newPassword: "",
      confirmNewPassword: "",
      check: false,
      passwordCurrentPasswordHidden: true,
      passwordNewPasswordHidden: true,
      passwordConfirmNewPasswordHidden: true,
      password: ""
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
      }
    },
    clearData() {
      this.currentPassword = "";
      this.newPassword = "";
      this.confirmNewPassword = "";
    },
    checkPassword(confirmNewPassword, newPassword) {
      if (confirmNewPassword === newPassword) {
        this.check = true;
      } else {
        alert("兩次密碼不相等，請再次確認密碼");
        this.check = false;
      }
    },
    hideCurrentPassword() {
      this.passwordCurrentPasswordHidden = false;
      this.passwordCurrentPasswordFieldType =
        this.passwordCurrentPasswordFieldType === "password"
          ? "text"
          : "password";
    },
    updateProfile_password() {
      let uid = firebase.auth().currentUser.uid;
      fStore
        .collection("users")
        .doc(uid)
        .update({
          password: this.confirmNewPassword
        });
    },
    showCurrentPassword() {
      this.passwordCurrentPasswordHidden = true;
      this.passwordCurrentPasswordFieldType = this.passwordCurrentPasswordFieldType;
    },
    hideNewPassword() {
      this.passwordNewPasswordHidden = false;
      this.passwordNewPasswordFieldType =
        this.passwordNewPasswordFieldType === "password" ? "text" : "password";
    },
    showNewPassword() {
      this.passwordNewPasswordHidden = true;
      this.passwordNewPasswordFieldType = this.passwordNewPasswordFieldType;
    },
    hideConfirmNewPassword() {
      this.passwordConfirmNewPasswordHidden = false;
      this.passwordConfirmNewPasswordFieldType =
        this.passwordNewPasswordFieldType === "password" ? "text" : "password";
    },
    showConfirmNewPassword() {
      this.passwordConfirmNewPasswordHidden = true;
      this.passwordConfirmNewPasswordFieldType = this.passwordConfirmNewPasswordFieldType;
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

.Password {
  position: absolute;
  height: 24px;
  width: 24px;
  top: 44%;
  margin-top: -6px;
  right: 155px;
  z-index: 1;
  cursor: pointer;
}
</style>
