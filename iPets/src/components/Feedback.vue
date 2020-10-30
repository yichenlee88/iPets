<template>
  <b-container>
    <b-row>
      <b-col>
        <h3>聯絡我們：</h3>
      </b-col>
    </b-row>
    <div>
      <b-form @submit="onSubmit">
        <b-form-input
          class="InputClass"
          name="username"
          v-model="username"
          placeholder="使用者名稱"
        ></b-form-input>
        <b-form-select
          class="InputClass center"
          name="prombleType"
          v-model="prombleType"
          :options="options"
        ></b-form-select
        ><b-form-input
          class="InputClass"
          name="email"
          v-model="email"
          placeholder="電子郵件"
        ></b-form-input>
        <b-form-textarea
          class="TextareaClass"
          name="description"
          id="description"
          v-model="description"
          placeholder="您遇到的問題是..."
          maxlength
        ></b-form-textarea
        ><b-button type="submit" class="ButtonClass">送出</b-button></b-form
      >
    </div>
  </b-container>
</template>

<script>
import { db } from "../db";
const fStore = db.firestore();

export default {
  name: "Feedback",
  data() {
    return {
      prombleType: null,
      username: "",
      email: "",
      description: "",
      options: [
        { value: "null", text: "選擇您的問題型態" },
        { value: "帳號安全", text: "帳號安全" },
        { value: "關於行事曆", text: "關於行事曆" },
        { value: "關於倒數計時器", text: "關於倒數計時器" },
        { value: "關於相簿", text: "關於相簿" },
        { value: "關於辨識功能", text: "關於辨識功能" },
        { value: "關於風格轉換", text: "關於風格轉換" },
        { value: "給iPets的建議", text: "給iPets的建議" }
      ]
    };
  },
  methods: {
    onSubmit(e) {
      e.preventDefault();
      var docRef = fStore.collection("contact").doc();
      docRef
        .set({
          username: this.username,
          email: this.email,
          prombleType: this.prombleType,
          description: this.description
        })
        .then(() => {
          this.$router.go({ path: this.$router.path });
        })
        .finally(() => {
          alert("您的建議已經送出囉~!");
        });
    }
  }
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
  max-width: auto;
  border-radius: 40px;
  margin-bottom: 20px;
  margin-top: 10px;
}

.TextareaClass {
  height: 120px;
  max-width: auto;
  border-radius: 16px;
  margin-bottom: 20px;
}

.ButtonClass {
  height: 48px;
  width: 100%;
  border-radius: 40px;
  margin-bottom: 20px;
  background: -webkit-linear-gradient(
    left,
    rgb(148, 115, 221) 0%,
    rgb(26, 201, 228) 100%
  );
}
</style>
