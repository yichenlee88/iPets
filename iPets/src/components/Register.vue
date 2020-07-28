<template>
  <b-container
    ><b-img
      class="banner_png center"
      src="../static/img/logo_banner.png"
    ></b-img>
    <b-card
      title="註冊"
      tag="article"
      class="text-center center"
      style="width:512px;"
    >
      <b-card-text>
        <p class="center">請輸入您的帳號</p>
        <b-form-input
          class="InputClass center"
          id="input-text"
          v-model="text"
          placeholder="Username"
          required
        ></b-form-input>
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
        <!-- 預設輸入 -->
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
        <div v-if="passwordHidden">
          <b-form-input
            type="password"
            class="InputClass center"
            id="Confirm Password"
            v-model="password"
            placeholder="Confirm Password"
            required
          ></b-form-input>
          <b-input-group-prepend class="mr-n2">
            <span
              class="display-eye fa fa-eye-slash"
              @click="hidePassword"
            ></span>
          </b-input-group-prepend>
        </div>
        <!-- 預設確認 -->
        <div v-if="!passwordHidden">
          <b-form-input
            class="InputClass center"
            id="Confirm Password"
            type="text"
            v-model="password"
            placeholder="Confirm Password"
            required
          ></b-form-input>
          <b-input-group-prepend class="mr-n2">
            <span class="display-eye fa fa-eye" @click="showPassword"></span>
          </b-input-group-prepend>
        </div>
        <b-form-select
          class="InputClass center"
          v-model="selected"
          :options="options"
        ></b-form-select>
        <div>
          <b-form-input
            type="text"
            class="InputClass center"
            id="input-birth"
            v-model="text"
            placeholder="Birth"
            required
          ></b-form-input>
          <b-input-group-prepend class="mr-n2">
            <span class="date"></span>
          </b-input-group-prepend>
        </div>
        <b-button class="ButtonClass" @click="auth_email">註冊</b-button>
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
  <!-- <div class="container">
    <img src="../assets/logo_banner.png" class="center" />
    <div class="card">
      <div class="card-header" style="background-color: #e3f2fd;">註冊</div>
      <div class="card-body">
        <form>
          <div class="form-group mx-auto my-3" style="width: 80%;">
            <label for="input-username">使用者名稱</label>
            <span class="text-danger ml-1">*</span>
            <input
              id="input-username"
              class="form-control"
              type="text"
              required
              placeholder="帳號長度為8~12，不可有空白"
              v-model="username"
            />
          </div>

          <div class="form-group mx-auto my-3" style="width: 80%;">
            <label for="input-email">電子郵件</label>
            <span class="text-danger ml-1">*</span>
            <input
              id="input-email"
              class="form-control"
              type="email"
              required
              placeholder="格式為:xxx@gmail.com"
              v-model="email"
            />
          </div>

          <div class="form-group mx-auto my-3" style="width: 80%;">
            <label for="input-password">密碼</label>
            <span class="text-danger ml-1">*</span>
            <input
              id="input-password"
              class="form-control"
              type="password"
              required
              placeholder="帳號長度為8~12"
              v-model="password"
            />
          </div>

          <button
            type="submit"
            class="w-25 btn btn-primary center"
            @click="auth_email"
          >
            註冊
          </button>
        </form>

        <div class="hide-md-lg">
          <p>或者</p>
        </div>
        <div class="row">
          <div class="col-12">
            <a href="#" class="fb btn">
              <i class="fa fa-facebook fa-fw"></i> Register with Facebook
            </a>
          </div>
          <div class="col-12">
            <a href="#" class="twitter btn">
              <i class="fa fa-twitter fa-fw"></i> Register with Twitter
            </a>
          </div>
          <div class="col-12">
            <a href="#" class="google btn">
              <i class="fa fa-google fa-fw"></i> Register with Google+
            </a>
          </div>
        </div>
      </div>
    </div>
  </div> -->
</template>

<script>
import { db } from "../db";

const fAuth = db.auth();
const fStore = db.firestore();

export default {
  name: "Register",
  data() {
    return {
      username: "",
      email: "",
      password: "",
      passwordFieldType: "password",
      passwordHidden: true,
      selected: "I prefer not to say",
      options: [
        { value: "I prefer not to say", text: "不透漏" },
        { value: "male", text: "男" },
        { value: "female", text: "女" }
      ]
    };
  },
  methods: {
    auth_email: function(e) {
      fAuth
        .createUserWithEmailAndPassword(this.email, this.password)
        .then(userCredential => {
          var user = userCredential.user;
          fStore
            .collection("users")
            .doc(user.uid)
            .set({
              name: this.username,
              email: this.email,
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
          console.log("恭喜註冊成功了!");
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

.option {
  border-radius: 40px;
}
</style>
