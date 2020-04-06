<template>
  <div class="container">
    <div class="card">
        <div class="card-header" style="background-color: #e3f2fd;">登入</div>
        <div class="card-body">
            <div class="row col-12">
                <div class="col-2">
                    <label class="txtEmail">電子郵件: </label>
                </div>
                <div class="col-10">
                    <input type="email" style="margin-top: 15px;" class="form-control" name="txtEmail" id="txtEmail" required placeholder="請輸入信箱">
                </div>
            </div>
            <label class="txtEmail">電子郵件: </label>
            <input type="email" style="margin-top: 15px;" class="form-control" name="txtEmail" id="txtEmail" required placeholder="請輸入信箱">
            <label class="txtPwd" style="margin-top: 15px;">密碼：</label>
            <input class="form-control" style="margin-top: 15px;" name="txtPwd" id="txtPwd" required placeholder="輸入密碼">
            <input class="btn" type="submit" value="送出" style="margin-top: 15px; background-color: #e3f2fd; width: 100%">
            <div class="hide-md-lg">
                <p>Or sign with:</p>
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
    width: 70%;
    min-height: 600px;
}

.row {
    vertical-align: bottom;
    line-height: 50px;
    text-align: center;
    display: row;
}

.hide-md-lg {
    font-family: "Microsoft JhengHei", "sans-serif";
    text-align: center;
    margin-top: 15px;
}
</style>
