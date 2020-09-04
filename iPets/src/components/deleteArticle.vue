<template>
  <b-container>
    <b-row class="justify-content-md-center" style="margin:20px 0">
      <b-col cols="10" style="margin:0 auto">
        <div v-for="(item, index) in comments" :key="index">
          <b-row class="text-left" style="margin-top:20px">
            <b-col cols="8">{{item.id}}.{{item.title}}</b-col>
            <b-col>
              <b-button pill v-on:click="$bvModal.show(`${index}`)">查看文章</b-button>
              <b-button pill v-on:click="deletePost" style="background: #CD5C5C;border: #CD5C5C">刪除文章</b-button>
            </b-col>
          </b-row>
          <b-modal size="lg" :id="`${index}`" :title="item.title">
            <div class="center" style="margin-top:20px">
              <img :src="`${item.image}`" style="max-width: 60%;">
            </div>
            <b-container style="padding:20px 120px;">
              <p class="content">{{item.introduction}}</p>
              <div v-for="(item, i) in comments.contents" :key="i">
                <div style="text-align: center">
                  <span class="content-title">{{item.title}}</span>
                  <br>
                  <img class="rounded" :src="item.img" style="max-width: 30%">
                </div>
                <p class="content">{{item.content}}</p>
              </div>
              <p class="content-end">{{item.ending}}</p>
            </b-container>
          </b-modal>
        </div>
      </b-col>
    </b-row>
  </b-container>
</template>

<script>
import axios from "axios";

export default {
  name: "DeleteArticle",
  data() {
    return {
      title: "",
      image: "",
      introduction: "",
      ending: "",
      comments: [],
      contents: [{ content: "", title: "", img: "" }]
    };
  },
  mounted() {
    axios.get("http://localhost:3000/comments").then(res => {
      console.log(res);
      this.comments = res.data;
    });
  },
  methods: {
    viewPost() {
      this.$refs["my-modal"].show();
    },
    deletePost() {}
  }
};
</script>

<style>
.right {
  margin-top: 20px;
  float: right;
}
.left {
  margin-top: 20px;
  float: left;
}
</style>
