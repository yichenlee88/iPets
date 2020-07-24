<template>
  <b-container
    ><b-img
      class="banner_png center"
      src="../static/img/logo_banner.png"
    ></b-img>
    <b-card
      title="登入"
      tag="article"
      class="text-center center"
      style="width:512px;"
    >
      <b-card-text>
        <b-row>
          <b-col>
            <b-form-input
              class="InputClass center"
              id="input-email"
              v-model="email"
              placeholder="Email"
              required
            ></b-form-input>
          </b-col>
        </b-row>
        <b-row>
          <b-col
            ><b-form-input
              class="InputClass center"
              id="input-password"
              type="password"
              v-model="password"
              placeholder="Password"
              required
            ></b-form-input
          ></b-col>
        </b-row>
        <!-- <b-row>
          <b-col col="10">
            <b-form-input></b-form-input>
            <span
              class="display-eye fa fa-eye-slash center"
              @click="hidePassword"
            ></span
          ></b-col>
        </b-row> -->
        <b-row>
          <b-col>
            <b-button class="ButtonClass" @click="auth_email">登入</b-button>
          </b-col>
        </b-row>
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
  <!--
  <div class="container">
    <img src="../assets/logo_banner.png" class="center" />
    <div class="card">
      <div class="card-header">
        登入
      </div>
      <div class="card-body">
        <form>
          <div class="form-group mx-auto my-3" style="width: 80%;">
            <label for="input-email">電子郵件</label>
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
            登入
          </button>
        </form>
        <div class="hide-md-lg">
          <p>或者</p>
        </div>
        <div class="row">
          <div class="col-12">
            <a href="#" class="fb btn">
              <i class="fa fa-facebook fa-fw"></i> Login with Facebook
            </a>
          </div>
          <div class="col-12">
            <a href="#" class="twitter btn">
              <i class="fa fa-twitter fa-fw"></i> Login with Twitter
            </a>
          </div>
          <div class="col-12">
            <a href="#" class="google btn">
              <i class="fa fa-google fa-fw"></i> Login with Google+
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

export default {
  name: "Login",
  data() {
    return {
      email: "",
      password: ""
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

/* .btn {
  width: 80%;
  padding: 12px;
  border: none;
  border-radius: 4px;
  margin: 5px 0;
  opacity: 0.85;
  display: inline-block;
  font-size: 17px;
  line-height: 20px;
  text-decoration: none;
  opacity: inherit;
}

input:hover,
.btn:hover {
  opacity: 1;
}

.hide-md-lg {
  font-family: "Microsoft JhengHei", "sans-serif";
  text-align: center;
  margin-top: 15px;
}

.fb {
  background-color: #3b5998;
  color: white;
}

.twitter {
  background-color: #55acee;
  color: white;
}

.google {
  background-color: #dd4b39;
  color: white;
}

.center {
  display: block;
  margin-left: auto;
  margin-right: auto;
  width: 80%;
}

.card-header {
  background-color: #e3f2fd;
  font-size: 24px;
  font-family: "Microsoft JhengHei", Helvetica, Arial, sans-serif;
  text-align: center;
} */
</style>
