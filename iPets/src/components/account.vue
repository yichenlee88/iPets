<template>
  <b-container>
    <b-row>
      <b-col>
        <h3>聯絡我們：</h3>
      </b-col>
    </b-row>
    <div>
      <b-form @submit="addContact">
        <b-form-input
          class="InputClass center"
          name="userName"
          v-model="userName"
          placeholder="使用者名稱"
        ></b-form-input>
        <b-form-select
          class="InputClass center"
          name="problemType"
          v-model="problemType"
          :options="options"
        ></b-form-select
        ><b-form-input
          class="InputClass center"
          name="email"
          v-model="email"
          placeholder="電子郵件"
        ></b-form-input>
        <b-form-textarea
          class="TextareaClass center"
          name="description"
          id="description"
          v-model="description"
          placeholder="您遇到的問題是..."
          maxlength
        ></b-form-textarea
        ><b-button type="submit" class="ButtonClass center"
          >送出</b-button
        ></b-form
      >
    </div>
  </b-container>
</template>

<script>
import { db } from "../db";
const fStore = db.firestore();

export default {
  name: "account"
};
</script>

<style scoped>
.center {
  display: block;
  margin-left: auto;
  margin-right: auto;
}

.InputClass {
  height: 48px;
  max-width: 75%;
  border-radius: 40px;
  margin-bottom: 20px;
  margin-top: 10px;
}

.TextareaClass {
  height: 120px;
  max-width: 75%;
  border-radius: 16px;
  margin-bottom: 20px;
}

.ButtonClass {
  height: 48px;
  width: 75%;
  border-radius: 40px;
  margin-bottom: 20px;
  background: -webkit-linear-gradient(
    left,
    rgb(148, 115, 221) 0%,
    rgb(26, 201, 228) 100%
  );
}
</style>
