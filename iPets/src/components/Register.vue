<template>
  <div class="container">
    <img src="../assets/logo_banner.png" class="banner_png" />
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
            class="w-25 btn btn-primary"
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
  </div>
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
      password: ""
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
              password: this.password
            })
            .then(() => {
              alert("success");
            });
        })
        .catch(error => {
          alert(error.code);
          alert(error.message);
        })
        .finally(() => {
          alert("恭喜註冊成功了!");
        });
      e.preventDefault();
    }
  }
};
</script>

<style scoped>
.container {
  margin-top: 20px;
  margin-bottom: 20px;
  border: 0px;
  font-family: "Microsoft JhengHei", "sans-serif";
  width: 40%;
  min-height: 600px;
}

.banner_png {
  width: 60%;
  margin: auto;
}

.row {
  vertical-align: bottom;
  line-height: 50px;
  text-align: center;
}

.btn {
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
</style>
