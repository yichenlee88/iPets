<template>
  <div class="container">
    <img src="../assets/logo_banner.png" class="banner_png" />
    <div class="card">
      <div class="card-header" style="background-color: #e3f2fd;">登入</div>
      <div class="card-body">
        <input
          type="email"
          style="margin-top: 15px;"
          class="form-control"
          name="txtEmail"
          id="txtEmail"
          placeholder="請輸入信箱"
        />
        <input
          class="form-control"
          style="margin-top: 15px;"
          name="txtPwd"
          id="txtPwd"
          placeholder="輸入密碼"
        />
        <input
          class="btn"
          type="submit"
          value="送出"
          style="margin-top: 15px; background-color: #e3f2fd; width: 100%"
        />
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
  </div>
</template>

<script>
export default {
  name: 'Login',
  data () {
    return {}
  }
}
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
  width: 100%;
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
  background-color: #3B5998;
  color: white;
}

.twitter {
  background-color: #55ACEE;
  color: white;
}

.google {
  background-color: #dd4b39;
  color: white;
}
</style>
