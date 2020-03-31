<template>
    <div class="container marketing contactus">
        <div class="row featurette">
            <div class="col-md-7 order-md-2">
                <h2 class="title">聯絡我們</h2>
                <br>
                <div class="form-group text-left">
                    <label for="txtName">寄件人姓名</label>
                    <input type="text" class="form-control" name="txtName" id="txtName" required>
                </div>
                <div class="form-group text-left">
                    <label for="txtEmail">寄件人信箱</label>
                    <input type="email" class="form-control" name="txtEmail" id="txtEmail" required>
                </div>
                <div class="form-group text-left">
                    <label for="txtName" class="text-left">建議</label>
                    <textarea class="form-control" required></textarea>
                </div>
                <button type="submit" class="btn btn-primary" style="width: 100%;">送出</button>
            </div>
            <div class="col-md-5 order-md-1">
                <h2 class="title1">聯絡方式</h2>
                <br>
                <ul>
                    <li style="margin-top: 15px;" class="text-left">電話：09xx-xxx-xxx</li>
                    <li style="margin-top: 15px;" class="text-left">信箱：xxxxx@gmail.com</li>
                    <li style="margin-top: 15px;" class="text-left">地址：100台北市中正區濟南路一段321號</li>
                    <li style="margin-top: 15px;" class="text-left">粉絲專業：xxx/xxx/xxx/xxx</li>
                </ul>
            </div>
        </div>
    </div>
</template>

<script>
export default {
  name: 'Contact'
}
</script>

<style>
.contactus {
    height: 500px;
    margin-top: 110px;
}
</style>
