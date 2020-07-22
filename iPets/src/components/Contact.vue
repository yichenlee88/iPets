<template>
  <b-container>
    <b-row class="ContactTitle text-center"
      ><b-col><h1>歡迎提出您任何的問題</h1></b-col></b-row
    >
    <b-row>
      <b-col col="4"
        ><img class="center" src="../assets/4.jpg" width="320" alt="image slot"
      /></b-col>
      <b-col col="8">
        <b-form-input
          class="InputClass"
          v-model="text"
          placeholder="First Name"
        ></b-form-input>
        <b-form-input
          class="InputClass"
          v-model="text"
          placeholder="Last Name"
        ></b-form-input
        ><b-form-input
          class="InputClass"
          v-model="email"
          placeholder="Your Email"
        ></b-form-input>
        <b-form-textarea
          class="TextareaClass"
          id="textarea-default"
          placeholder="Your Question..."
          maxlength
        ></b-form-textarea
        ><b-button class="ButtonClass">Send</b-button></b-col
      >
    </b-row>

    <b-row class="ContactTitle text-center"
      ><b-col><h1>加入我們</h1></b-col></b-row
    >
    <b-card
      img-src="../static/img/carousel-01.jpg"
      img-alt="Card image"
      img-top
    >
      <b-card-text>
        <b-row
          ><b-col col="6">
            <b-img class="qrcenter" src="../static/img/M.png"></b-img></b-col
          ><b-col col="6"></b-col
        ></b-row>
      </b-card-text>

      <b-button href="#" variant="primary">Go somewhere</b-button>
    </b-card>
    <!-- <div class="row feature">
      <div class="col-md-7 order-md-2">
        <h2 class="title">聯絡我們</h2>
        <br />
        <div class="form-group text-left">
          <label for="txtName">寄件人姓名</label>
          <input
            type="text"
            class="form-control"
            name="txtName"
            id="txtName"
            required
          />
        </div>
        <div class="form-group text-left">
          <label for="txtEmail">寄件人信箱</label>
          <input
            type="email"
            class="form-control"
            name="txtEmail"
            id="txtEmail"
            required
          />
        </div>
        <div class="form-group text-left">
          <label for="txtName" class="text-left">建議</label>
          <textarea class="form-control" required></textarea>
        </div>
        <button type="submit" class="btn btn-primary" style="width: 100%;">
          送出
        </button>
      </div>
      <div class="col-md-5 order-md-1 info">
        <h2 class="title1">聯絡方式</h2>
        <br />
        <ul>
          <li style="margin-top: 15px;" class="text-left">
            電話：09xx-xxx-xxx
          </li>
          <li style="margin-top: 15px;" class="text-left">
            信箱：xxxxx@gmail.com
          </li>
          <li style="margin-top: 15px;" class="text-left">
            地址：100台北市中正區濟南路一段321號
          </li>
          <li style="margin-top: 15px;" class="text-left">
            粉絲專業：xxx/xxx/xxx/xxx
          </li>
        </ul>
      </div>
    </div> -->
  </b-container>
</template>

<script>
export default {
  name: "Contact"
};
</script>

<style>
.ContactTitle {
  margin-top: 100px;
  margin-bottom: 100px;
}

.center {
  display: block;
  margin-left: auto;
  margin-right: auto;
  width: 50%;
}

.qrcenter {
  display: block;
  margin-left: auto;
  margin-right: auto;
  width: 25%;
}

.InputClass {
  height: 48px;
  width: 400px;
  border-radius: 40px;
  margin-bottom: 20px;
}

.TextareaClass {
  height: 120px;
  width: 400px;
  border-radius: 16px;
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

.feature {
  margin-top: 130px;
  margin-bottom: 130px;
}
</style>
