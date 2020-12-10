<template>
  <b-container>
    <b-row>
      <b-col cols="12" sm="12" md="12">
        <h3>更改帳號：</h3>
      </b-col>
      <b-col class="coltitle" cols="4" sm="4" md="4">
        <p>更改帳號：</p>
      </b-col>
      <b-col cols="8" sm="8" md="8">
        <div>
          <b-form-input
            type="email"
            id="newEmail"
            class="InputClass"
            name="newEmail"
            v-model="newEmail"
            placeholder="Email"
          ></b-form-input>
        </div>
      </b-col>
      <b-col cols="4" sm="4" md="4"></b-col>
      <b-col cols="8" sm="8" md="8">
        <b-button
          class="ButtonClass"
          @click="update_email(newEmail), updateProfile_mail(newEmail), clearData()"
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
      newEmail: ""
    };
  },
  methods: {
    update_email(newEmail) {
      var password = this.password;
      firebase.auth().onAuthStateChanged(function(user) {
        if (user) {
          user
            .updateEmail(newEmail)
            .then(() => {
              alert("變更成功");
            })
            .catch(function() {
              var credential = firebase.auth.EmailAuthProvider.credential(
                user.email,
                password
              );
              user.reauthenticateWithCredential(credential).then(function() {
                user.updateEmail(newEmail).then(() => {
                  alert("變更成功");
                });
              });
            });
        }
      });
    },
    updateProfile_mail() {
      let uid = firebase.auth().currentUser.uid;
      fStore
        .collection("users")
        .doc(uid)
        .update({
          email: this.newEmail
        });
    },
    clearData() {
      this.newEmail = "";
    }
  },
  computed: {
    password() {
      return this.$store.state.password;
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
