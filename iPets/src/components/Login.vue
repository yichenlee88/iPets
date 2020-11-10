<template>
  <b-container
    ><b-img
      class="banner_png center"
      src="../static/img/login-01.jpg"
      style="width:512px;"
    ></b-img>
    <b-card
      title="登入"
      tag="article"
      class="text-center center"
      style="width:512px;"
    >
      <b-card-text>
        <b-form-input
          class="InputClass center"
          id="input-email"
          v-model="email"
          placeholder="Email"
          required
        ></b-form-input>
        <div v-if="passwordHidden">
          <b-form-input
            type="password"
            class="InputClass center"
            id="input-password"
            v-model="password"
            placeholder="Password"
            required
          ></b-form-input>
          <b-input-group-prepend class="mr-n2">
            <span
              class="display-eye fa fa-eye-slash"
              @click="hidePassword"
            ></span>
          </b-input-group-prepend>
        </div>
        <!-- 預設 -->
        <div v-if="!passwordHidden">
          <b-form-input
            class="InputClass center"
            id="input-password"
            type="text"
            v-model="password"
            placeholder="Password"
            required
          ></b-form-input>
          <b-input-group-prepend class="mr-n2">
            <span class="display-eye fa fa-eye" @click="showPassword"></span>
          </b-input-group-prepend>
        </div>
        <b-button class="ButtonClass" @click="auth_email">登入</b-button>
        <b-row
          ><b-col>
            <a class="btn btn-social-icon btn-facebook">
              <span class="fa fa-facebook fa-2x"></span>
            </a>
            <a class="btn btn-social-icon btn-instagram">
              <span class="fa fa-instagram fa-2x"></span>
            </a>
            <a class="btn btn-social-icon btn-google">
              <span class="fa fa-google fa-2x"></span> </a></b-col
        ></b-row>
      </b-card-text> </b-card
  ></b-container>
</template>

<script>
import { db } from "../db";

const fAuth = db.auth();

export default {
  props: {
    passwordText: {
      default: "",
      type: String
    },
    fieldLabel: {
      default: "",
      type: String
    }
  },
  name: "login",
  data() {
    return {
      email: "",
      password: "",
      passwordFieldType: "password",
      passwordHidden: true
    };
  },
  methods: {
    auth_email: function(e) {
      fAuth
        .signInWithEmailAndPassword(this.email, this.password)
        .then(userCredential => {
          this.$router.go({ path: this.$router.path });
        })
        .catch(error => {
          alert(error.code);
          alert(error.message);
        })
        .finally(() => {
          console.log("登入成功");
        });
      e.preventDefault();
    },
    hidePassword() {
      this.passwordHidden = false;
      this.passwordFieldType =
        this.passwordFieldType === "password" ? "text" : "password";
    },
    showPassword() {
      this.passwordHidden = true;
      this.passwordFieldType = this.passwordFieldType;
    }
  }
};
</script>

<style scoped>
.InputClass {
  height: 48px;
  width: 400px;
  border-radius: 40px;
  margin-bottom: 20px;
}

.InputGroup {
  height: 48px;
  width: 400px;
  border-radius: 40px;
  margin-bottom: 20px;
}

.display-eye {
  position: absolute;
  height: 24px;
  width: 24px;
  top: 46%;
  margin-top: -6px;
  right: 70px;
  z-index: 1;
  cursor: pointer;
}

.banner_png {
  width: 60%;
  margin: auto;
}

.center {
  display: block;
  margin-left: auto;
  margin-right: auto;
}

.card {
  -webkit-box-shadow: 0 0 15px rgba(0, 0, 0, 0.2);
  -moz-box-shadow: 0 0 15px rgba(0, 0, 0, 0.2);
  box-shadow: 0 0 15px 5px rgba(0, 0, 0, 0.2);
  margin-bottom: 20px;
}

.ButtonClass {
  height: 48px;
  width: 400px;
  border-radius: 40px;
  margin-bottom: 20px;
  background: -webkit-linear-gradient(
    left,
    rgb(148, 115, 221) 0%,
    rgb(26, 201, 228) 100%
  );
}
</style>
