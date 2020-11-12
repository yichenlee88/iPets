<template>
  <b-container>
    <b-row cols-md="2" style="margin:20px 20px">
      <div v-for="(item, index) in question" :key="index">
        <b-card style="margin:20px;width: 80%">
          <b-col>
            <b-col>{{index + 1}}. {{ item.problemType }}</b-col>
            <b-col>使用者姓名：{{ item.userName }}</b-col>
            <b-col>使用者信箱：{{ item.email }}</b-col>
            <b-col>問題類別：{{ item.problemType }}</b-col>
            <b-col>回饋內容：{{ item.description }}</b-col>
          </b-col>
        </b-card>
      </div>
    </b-row>
  </b-container>
</template>

<script>
import { db } from "../db";
import "firebase/firestore";

const fStore = db.firestore();

export default {
  name: "manageContact",
  data() {
    return {
      question: []
    };
  },
  mounted() {
    this.getContactData();
  },
  methods: {
    getContactData() {
      fStore.collection("contact").get();
      fStore
        .collection("contact")
        .get()
        .then(querySnapshot => {
          querySnapshot.forEach(doc => {
            this.question.push(doc.data());
            console.log(doc.id, doc.data());
          });
          console.log(this.question);
        });
    }
  }
};
</script>

<style>
</style>
