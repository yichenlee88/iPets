<template>
  <b-container style="margin-top: 40px">
    <u><a href="#/home">首頁</a> > <a href="#/dogScience">狗狗科普</a> > {{this.comments.title}}</u>
    <div class="title_name" style="margin-top:20px">{{this.comments.title}}<br>
    <img :src="`${this.comments.image}`" style="max-width: 60%"></div>
    <b-container style="padding:20px 120px;">
      <p class="content">{{this.comments.introduction}}</p>
      <div style="text-align: center">
        <span class="content-title" v-if="this.comments.title1">{{this.comments.title1}}</span><br>
        <img class="rounded" v-if="this.comments.img1" :src="`${this.comments.img1}`" style="max-width: 30%">
      </div>
      <p class="content">{{this.comments.content1}}</p>
      <div style="text-align: center">
        <span class="content-title" v-if="this.comments.title2">{{this.comments.title2}}</span><br>
        <img class="rounded" v-if="this.comments.img2" :src="`${this.comments.img2}`" style="max-width: 30%">
      </div>
      <p class="content">{{this.comments.content2}}</p>
      <div style="text-align: center">
        <span class="content-title" v-if="this.comments.title3">{{this.comments.title3}}</span><br>
        <img class="rounded" v-if="this.comments.img3" :src="`${this.comments.img3}`" style="max-width: 30%">
      </div>
      <p class="content" v-if="this.comments.content3">{{this.comments.content3}}</p>
      <div style="text-align: center">
        <span class="content-title" v-if="this.comments.title4">{{this.comments.title4}}</span><br>
        <img class="rounded" v-if="this.comments.img4" :src="`${this.comments.img4}`" style="max-width: 30%">
      </div>
      <p class="content" v-if="this.comments.content4">{{this.comments.content4}}</p>
      <div style="text-align: center;">
        <span class="content-title" v-if="this.comments.title5">{{this.comments.title5}}</span><br>
        <img class="rounded" v-if="this.comments.img5" :src="`${this.comments.img5}`" style="max-width: 30%">
      </div>
      <p class="content" v-if="this.comments.content5">{{this.comments.content5}}</p>
      <p class="content-end">{{this.comments.ending}}</p>
    </b-container>
  </b-container>
</template>

<script>
import axios from "axios";

export default {
  name: "Post",
  data() {
    return {
      id: this.$route.params.id,
      comments: []
    };
  },
  mounted() {
    let id = this.$route.params.id;
    this.id = id;
    axios.get("http://localhost:3000/comments/" + this.id).then(res => {
      console.log(res.data);
      this.comments = res.data;
    });
  },
  watch: {
    $route(now) {
      let id = this.$route.params.id;
      this.id = id;
      axios.get("http://localhost:3000/comments/" + this.id).then(res => {
        console.log(now);
        this.comments = res.data;
      });
    }
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
