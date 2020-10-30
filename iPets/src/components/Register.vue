<template>
  <b-container>
    <b-img
      class="banner_png center"
      src="../static/img/login-01.jpg"
      style="width:512px;"
    ></b-img>
    <b-card tag="article" class="text-center center" style="width:768px;">
      <b-card-text>
        <!-- username -->
        <b-form-input
          class="InputClass center"
          name="userName"
          v-model="userName"
          placeholder="用戶名稱"
          required
        ></b-form-input>
        <!-- name -->
        <b-form-input
          class="InputClass center"
          name="name"
          v-model="name"
          placeholder="姓名"
          required
        ></b-form-input>
        <!-- Email -->
        <b-form-input
          class="InputClass center"
          name="Email"
          v-model="Email"
          placeholder="電子郵件"
          required
        ></b-form-input>
        <!-- password -->
        <div v-if="passwordHidden">
          <b-form-input
            type="password"
            v-model="password"
            name="password"
            class="InputClass center"
            placeholder="密碼"
            required
          ></b-form-input>
          <b-input-group-prepend class="mr-n2">
            <span
              class="display-eye fa fa-eye-slash"
              @click="hidePassword"
            ></span>
          </b-input-group-prepend>
        </div>
        <!-- password預設輸入 -->
        <div v-if="!passwordHidden">
          <b-form-input
            class="InputClass center"
            name="password"
            v-model="password"
            type="text"
            placeholder="密碼"
            required
          ></b-form-input>
          <b-input-group-prepend class="mr-n2">
            <span class="display-eye fa fa-eye" @click="showPassword"></span>
          </b-input-group-prepend>
        </div>
        <!-- Confirm Password -->
        <div v-if="passwordHidden2">
          <b-form-input
            type="password"
            name="confirmPassword"
            class="InputClass center"
            placeholder="確認密碼"
            required
          ></b-form-input>
          <b-input-group-prepend class="mr-n2">
            <span
              class="display-eye-2 fa fa-eye-slash"
              @click="hidePassword2"
            ></span>
          </b-input-group-prepend>
        </div>
        <!-- Confirm Password 預設確認 -->
        <div v-if="!passwordHidden2">
          <b-form-input
            class="InputClass center"
            name="confirmPassword"
            type="text"
            placeholder="確認密碼"
            required
          ></b-form-input>
          <b-input-group-prepend class="mr-n2">
            <span class="display-eye-2 fa fa-eye" @click="showPassword2"></span>
          </b-input-group-prepend>
        </div>
        <!-- userBirth -->
        <date-picker
          v-model="userBirth"
          name="userBirth"
          type="date"
          placeholder="用戶生日"
        ></date-picker>
        <!-- userGender -->
        <b-form-select
          class="InputClass center"
          name="userGender"
          v-model="userGender"
          :options="options"
        ></b-form-select>
        <!-- phone -->
        <b-form-input
          class="InputClass center"
          name="phone"
          v-model="phone"
          placeholder="電話"
          required
        ></b-form-input>
        <!--address-->
        <b-form-input
          class="InputClass center"
          name="address"
          v-model="address"
          placeholder="地址"
          required
        ></b-form-input>
        <b-button class="ButtonClass center" @click="auth_email">註冊</b-button>
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
      </b-card-text>
    </b-card>
  </b-container>
</template>

<script>
// Import required dependencies
import "bootstrap/dist/css/bootstrap.css";
import { db } from "../db";

const fAuth = db.auth();
const fStore = db.firestore();

export default {
  name: "Register",
  data() {
    return {
      userName: "",
      name: "",
      Email: "",
      password: "",
      passwordFieldType: "password",
      passwordHidden: true,
      passwordHidden2: true,
      userBirth: "",
      userGender: "null",
      options: [
        { value: "null", text: "用戶性別" },
        { value: "I prefer not to say", text: "不透漏" },
        { value: "male", text: "男" },
        { value: "female", text: "女" }
      ],
      phone: "",
      userAddress: ""
    };
  },
  methods: {
    auth_email: function(e) {
      fAuth
        .createUserWithEmailAndPassword(this.Email, this.password)
        .then(userCredential => {
          var user = userCredential.user;
          fStore
            .collection("users")
            .doc(user.uid)
            .set({
              userName: this.userName,
              name: this.name,
              Email: this.Email,
              password: this.password,
              userGender: this.userGender,
              userBirth: this.userBirth.toISOString().slice(0, 10),
              birth_year: this.userBirth.getUTCFullYear(),
              birth_month: this.userBirth.getMonth() + 1,
              birth_date: this.userBirth.getDate(),
              address: this.address,
              phone: this.phone,
              photoURL: ""
            })
            .then(() => {
              this.$router.go({ path: this.$router.path });
            });
        })
        .catch(error => {
          alert(error.code);
          alert(error.message);
        })
        .finally(() => {
          alert("歡迎加入iPets的大家庭~!");
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
    },
    hidePassword2() {
      this.passwordHidden2 = false;
      this.passwordFieldType =
        this.passwordFieldType === "password" ? "text" : "password";
    },
    showPassword2() {
      this.passwordHidden2 = true;
      this.passwordFieldType = this.passwordFieldType;
    }
  }
};
</script>

<style scoped>
.InputClass {
  height: 48px;
  width: 512px;
  border-radius: 40px;
  margin-bottom: 20px;
}

.display-eye {
  position: absolute;
  height: 24px;
  width: 24px;
  top: 30%;
  right: 140px;
  margin-top: 3px;
  z-index: 1;
  cursor: pointer;
}

.display-eye-2 {
  position: absolute;
  height: 24px;
  width: 24px;
  top: 40%;
  margin-top: -8px;
  right: 140px;
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
  border: 0px;
  margin-bottom: 20px;
}

.ButtonClass {
  height: 48px;
  width: 512px;
  border-radius: 40px;
  margin-bottom: 20px;
  background: -webkit-linear-gradient(
    left,
    rgb(148, 115, 221) 0%,
    rgb(26, 201, 228) 100%
  );
}

.options {
  border-radius: 40px;
}

.mx-datepicker {
  width: auto;
}
.mx-datepicker >>> .mx-icon-calendar,
.mx-datepicker >>> .mx-icon-clear {
  margin-right: 8px;
}

.mx-datepicker >>> .mx-input {
  height: 48px;
  width: 512px;
  border-radius: 40px;
  margin-bottom: 20px;
  display: block;
  margin-left: auto;
  margin-right: auto;
}
</style>
