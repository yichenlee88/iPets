<template>
  <b-container style="margin-top: 40px">
    <div class="title_name">{{this.comments.title}}</div>
    <img v-bind:src="`${this.comments.img}`" style="max-width: 30%">
    <p class="content">{{this.comments.introduction}}</p>
    <span class="content-title">{{this.comments.title1}}</span>
    <img class="rounded" :src="`${this.comments.img1}`" style="max-width: 30%">
    <p class="content">{{this.comments.content1}}</p>
    <span class="content-title">{{this.comments.title2}}</span>
    <img class="rounded" :src="`${this.comments.img2}`" style="max-width: 30%">
    <p class="content">{{this.comments.content2}}</p>
    <span class="content-title">{{this.comments.title3}}</span>
    <img class="rounded" :src="`${this.comments.img3}`" style="max-width: 30%">
    <p class="content">{{this.comments.content3}}</p>
    <span class="content-title">{{this.comments.title4}}</span>
    <img class="rounded" :src="`${this.comments.img4}`" style="max-width: 30%">
    <p class="content">{{this.comments.content4}}</p>
    <span class="content-title">{{this.comments.title5}}</span>
    <img class="rounded" :src="`${this.comments.img5}`" style="max-width: 30%">
    <p class="content">{{this.comments.content5}}</p>
    <p class="content-end">{{this.comments.ending}}</p>
  </b-container>
</template>

<script>
import axios from "axios";

export default {
  name: "Post",
  data() {
    return {
      id: this.$route.params.id,
      title: "",
      image: "",
      introduction: "",
      title1: "",
      content1: "",
      title2: "",
      content2: "",
      title3: "",
      content3: "",
      title4: "",
      content4: "",
      title5: "",
      content5: "",
      ending: "",
      comments: []
    };
  },
  mounted() {
    axios.get("http://localhost:3000/comments/" + this.id).then(res => {
      console.log(res.data);
      this.comments = res.data;
    });
  }
};
</script>

<style scoped>
.title_name {
  font-size: 30px;
  font-family: "微軟正黑體", "Microsoft JhengHei", Arial, Georgia, serif;
  text-align: center;
}
.content {
  font-size: 20px;
}
.content-title {
  border-bottom: 2px dotted #dccb8a;
  padding: 10px 25px;
  background: rgba(255, 246, 212, 0.71);
  line-height: 4;
  border-radius: 25px;
  font-size: 16pt;
  color: #8f7061;
  font-weight: 900;
}
.content-end {
  font-size: 25px;
  color: #003377;
}
</style>
