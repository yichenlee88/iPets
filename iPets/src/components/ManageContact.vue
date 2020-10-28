<template>
  <b-container>
    <b-row cols-md="2" style="margin:20px 20px">
      <div v-for="(item, key, index) in question" :key="index">
        <b-card style="margin:20px;width: 80%">
          <b-col>
            <b-col>{{index + 1}}. {{ question.problemType }}</b-col>
            <b-col>使用者姓名：{{ question.userName }}</b-col>
            <b-col>使用者信箱：{{ question.email }}</b-col>
            <b-col>問題類別：{{ question.problemType }}</b-col>
            <b-col>回饋內容：{{ question.description }}</b-col>
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
  name: "ManageContact",
  data() {
    return {
      question: []
    };
  },
  mounted() {
    this.getData();
    fStore
      .collection("contact")
      .get()
      .then(querySnapshot => {
        querySnapshot.forEach(doc => {
          console.log(doc.id, doc.data());
          this.question = doc.data();
        });
      });
  },
  methods: {
    getData() {
      fStore.collection("contact").get();
    }
  }
};
</script>

<style>
</style>
