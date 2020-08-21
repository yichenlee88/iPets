<template>
  <div id="manageArticle">
    <div id="content" class="container">
      <div id="manager-info" style="margin:20px 0">
        <b-row class="my-1" style="text-align:center">
          <b-col cols="12">目前登入帳號：10646043</b-col>
          <b-col cols="12">管理員名稱：蔡欣恬</b-col>
          <b-col cols="12">管理權限：最高管理員</b-col>
        </b-row>
      </div>
      <b-row class="justify-content-md-center" style="margin:20px 0">
        <b-col cols="10" style="margin:0 auto">
          <b-card bg-variant="light">
            <b-row class="my-1">
              <b-col sm="2">
                <label for="article_title">標題:</label>
              </b-col>
              <b-col sm="10">
                <b-form-input
                  id="article_title"
                  type="text"
                  placeholder="Title"
                  v-model.trim="title"
                ></b-form-input>
              </b-col>
            </b-row>
            <b-row class="my-1">
              <b-col sm="2">
                <label for="article_image">圖片位址:</label>
              </b-col>
              <b-col sm="10">
                <b-form-input
                  id="article_image"
                  type="url"
                  placeholder="Input image url"
                  v-model.trim="image"
                ></b-form-input>
              </b-col>
            </b-row>
            <b-row class="my-1">
              <b-col sm="2">
                <label for="article_introduction">簡介:</label>
              </b-col>
              <b-col sm="10">
                <b-form-input
                  id="article_introduction"
                  type="text"
                  placeholder="Introduction"
                  v-model.trim="introduction"
                ></b-form-input>
              </b-col>
            </b-row>
            <div v-for="(comment, index) in contents" :key="index">
              <b-row class="my-1">
                <b-col sm="2">
                  <label for="article_title1">標題 {{index+1}}:</label>
                </b-col>
                <b-col sm="10">
                  <b-form-input
                    id="article_title1"
                    type="text"
                    placeholder="Enter title"
                    v-model.trim="comment.title"
                  ></b-form-input>
                </b-col>
              </b-row>
              <b-row class="my-1">
                <b-col sm="2">
                  <label for="article_img1">圖片 {{index+1}}:</label>
                </b-col>
                <b-col sm="10">
                  <b-form-input
                    id="article_img1"
                    type="text"
                    placeholder="Enter img url"
                    v-model.trim="comment.img"
                  ></b-form-input>
                </b-col>
              </b-row>
              <b-row class="my-1">
                <b-col sm="2">
                  <label for="article_content1">內容 {{index+1}}:</label>
                </b-col>
                <b-col sm="10">
                  <b-form-textarea
                    id="article_content1"
                    placeholder="Enter content"
                    rows="2"
                    v-model.trim="comment.content"
                  ></b-form-textarea>
                </b-col>
              </b-row>
            </div>
            <b-row class="my-1">
              <b-col sm="2">
                <label for="article_ending">結語:</label>
              </b-col>
              <b-col sm="10">
                <b-form-input
                  id="article_ending"
                  type="text"
                  placeholder="Ending..."
                  v-model.trim="ending"
                ></b-form-input>
              </b-col>
            </b-row>
            <b-button class="left" v-on:click="addRow">新增內容</b-button>
            <b-button class="right" v-on:click="createArticle">確認新增文章</b-button>
          </b-card>
        </b-col>
      </b-row>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "manageArticle",
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
  methods: {
    addRow() {
      console.log("Add new row");
      this.contents.push({ content: "", title: "", img: "" });
    },
    createArticle() {
      var article = {
        title: this.title,
        image: this.image,
        introduction: this.introduction,
        ending: this.ending
      };
      var content = {};
      article["contents"] = [];
      for (var i = 1; i < this.contents.length; i++) {
        content["content"] = this.contents[i].content;
        content["img"] = this.contents[i].img;
        content["title"] = this.contents[i].title;
        article["contents"].push(content[i]);
      }
      article["contents"] = this.contents;
      console.log(article);
      axios.post("http://localhost:3000/comments", article).then(res => {
        console.log(res);
        this.title = "";
        this.image = "";
        this.introduction = "";
        this.ending = "";
        for (var i = 0; i < this.contents.length; i++) {
          this.contents[i].content = "";
          this.contents[i].img = "";
          this.contents[i].title = "";
        }
        this.comments.push(res.data);
      });
    }
  }
};
</script>

<style>
.right {
  margin: 20px 0 0;
  float: right;
}
.left {
  margin: 20px 0 0;
  float: left;
}
</style>
