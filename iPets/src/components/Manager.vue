<template>
  <div id="manager">
    <div id="content" class="container">
      <div>
        <div id="manager-info" style="margin:20px 0">
          <b-row class="my-1" style="text-align:center">
            <b-col cols="12">目前登入帳號：10646043</b-col>
            <b-col cols="12">管理員名稱：蔡欣恬</b-col>
            <b-col cols="12">管理權限：最高管理員</b-col>
          </b-row>
        </div>
        <b-tabs content-class="mt-3" style="margin:20px 0" justified>
          <b-tab title="新增文章" active>
            <b-row class="justify-content-md-center" style="margin:20px 0">
              <b-col cols="10" style="margin:0 auto">
                <b-card bg-variant="light">
                  <b-row class="my-1">
                    <b-col sm="2">
                      <label for="article">標題:</label>
                    </b-col>
                    <b-col sm="10">
                      <b-form-input id="title" type="text" placeholder="Title" v-model.trim="title"></b-form-input>
                    </b-col>
                  </b-row>
                  <b-row class="my-1">
                    <b-col sm="2">
                      <label for="article">圖片位址:</label>
                    </b-col>
                    <b-col sm="10">
                      <b-form-input id="image" type="url" placeholder="Input image url" v-model.trim="image"></b-form-input>
                    </b-col>
                  </b-row>
                  <b-row class="my-1">
                    <b-col sm="2">
                      <label for="article">簡介:</label>
                    </b-col>
                    <b-col sm="10">
                      <b-form-input id="subTitle" type="text" placeholder="Introduction" v-model.trim="introduction"></b-form-input>
                    </b-col>
                  </b-row>
                  <b-row class="my-1">
                    <b-col sm="2">
                      <label for="article">標題1:</label>
                    </b-col>
                    <b-col sm="10">
                      <b-form-input id="title1" type="text" placeholder="Title 1" v-model.trim="title1"></b-form-input>
                    </b-col>
                  </b-row>
                  <b-row class="my-1">
                    <b-col sm="2">
                      <label for="article">內容1:</label>
                    </b-col>
                    <b-col sm="10">
                      <b-form-input
                        id="content1"
                        type="text"
                        placeholder="Enter content 1"
                        v-model.trim="content1"
                      ></b-form-input>
                    </b-col>
                  </b-row>
                  <b-row class="my-1">
                    <b-col sm="2">
                      <label for="article">標題2:</label>
                    </b-col>
                    <b-col sm="10">
                      <b-form-input id="title2" type="text" placeholder="Title 2" v-model.trim="title2"></b-form-input>
                    </b-col>
                  </b-row>
                  <b-row class="my-1">
                    <b-col sm="2">
                      <label for="article">內容2:</label>
                    </b-col>
                    <b-col sm="10">
                      <b-form-input
                        id="content2"
                        type="text"
                        placeholder="Enter content 2"
                        v-model.trim="content2"
                      ></b-form-input>
                    </b-col>
                  </b-row>
                  <b-row class="my-1">
                    <b-col sm="2">
                      <label for="article">標題3:</label>
                    </b-col>
                    <b-col sm="10">
                      <b-form-input id="title3" type="text" placeholder="Title 3" v-model.trim="title3"></b-form-input>
                    </b-col>
                  </b-row>
                  <b-row class="my-1">
                    <b-col sm="2">
                      <label for="article">內容3:</label>
                    </b-col>
                    <b-col sm="10">
                      <b-form-input
                        id="content3"
                        type="text"
                        placeholder="Enter content 3"
                        v-model.trim="content3"
                      ></b-form-input>
                    </b-col>
                  </b-row>
                  <b-row class="my-1">
                    <b-col sm="2">
                      <label for="article">標題4:</label>
                    </b-col>
                    <b-col sm="10">
                      <b-form-input id="title4" type="text" placeholder="Title 4" v-model.trim="title4"></b-form-input>
                    </b-col>
                  </b-row>
                  <b-row class="my-1">
                    <b-col sm="2">
                      <label for="article">內容4:</label>
                    </b-col>
                    <b-col sm="10">
                      <b-form-input
                        id="content4"
                        type="text"
                        placeholder="Enter content 4"
                        v-model.trim="content4"
                      ></b-form-input>
                    </b-col>
                  </b-row>
                  <b-row class="my-1">
                    <b-col sm="2">
                      <label for="article">標題5:</label>
                    </b-col>
                    <b-col sm="10">
                      <b-form-input id="title5" type="text" placeholder="Title 5" v-model.trim="title5"></b-form-input>
                    </b-col>
                  </b-row>
                  <b-row class="my-1">
                    <b-col sm="2">
                      <label for="article">內容5:</label>
                    </b-col>
                    <b-col sm="10">
                      <b-form-input
                        id="content5"
                        type="text"
                        placeholder="Enter content 5"
                        v-model.trim="content5"
                      ></b-form-input>
                    </b-col>
                  </b-row>
                  <b-row class="my-1">
                    <b-col sm="2">
                      <label for="article">結語:</label>
                    </b-col>
                    <b-col sm="10">
                      <b-form-input
                        id="ending"
                        type="text"
                        placeholder="Ending..."
                        v-model.trim="ending"
                      ></b-form-input>
                    </b-col>
                  </b-row>
                  <b-button class="right" v-on:click="createArticle">確認新增文章</b-button>
                </b-card>
              </b-col>
            </b-row>
          </b-tab>
          <b-tab title="新增領養機構">
            <b-row class="justify-content-md-center" style="margin:20px 0">
              <b-col cols="10" style="margin:0 auto">
                <b-card bg-variant="light">
                  <b-row class="my-1" v-for="(i, index) in adoptions" :key="index">
                    <b-col sm="2">
                      <label :for="`title-${i.title}`">{{ i.title }}:</label>
                    </b-col>
                    <b-col sm="10">
                      <b-form-input :id="`type-${i.id}`" :type="i.type"></b-form-input>
                    </b-col>
                  </b-row>
                  <b-row class="my-1">
                    <b-col sm="2">
                      <label for="article">簡介:</label>
                    </b-col>
                    <b-col sm="10">
                      <b-form-textarea
                        id="textarea-auto-height"
                        placeholder="Enter content..."
                        rows="3"
                      ></b-form-textarea>
                    </b-col>
                  </b-row>
                  <b-button class="right">確認新增領養機構</b-button>
                </b-card>
              </b-col>
            </b-row>
          </b-tab>
          <b-tab title="使用者帳號管理">
            <b-row class="justify-content-md-center" style="margin:20px 0">
              <b-col cols="10" style="margin:0 auto">
                <p>查詢使用者帳號密碼</p>
                <b-card bg-variant="light">
                  <b-row class="my-1">
                    <b-col sm="3">
                      <label>使用者帳號:</label>
                    </b-col>
                    <b-col sm="9">
                      <b-form-input id="user_account_get" type="text"></b-form-input>
                    </b-col>
                  </b-row>
                  <b-button class="right">查詢帳號密碼</b-button>
                </b-card>
                <p style="margin:20px 0">編輯使用者帳號密碼</p>
                <b-card bg-variant="light" style="margin:20px 0">
                  <b-row class="my-1">
                    <b-col sm="3">
                      <label>使用者帳號:</label>
                    </b-col>
                    <b-col sm="9">
                      <b-form-input id="user_account_update" type="text"></b-form-input>
                    </b-col>
                    <b-col sm="3">
                      <label>更改使用者密碼:</label>
                    </b-col>
                    <b-col sm="9">
                      <b-form-input id="user_password_update" type="text"></b-form-input>
                    </b-col>
                    <b-col sm="3">
                      <label>再次確認使用者密碼:</label>
                    </b-col>
                    <b-col sm="9">
                      <b-form-input id="user_password_check" type="text"></b-form-input>
                    </b-col>
                  </b-row>
                  <b-button class="right">確認編輯</b-button>
                </b-card>
              </b-col>
            </b-row>
          </b-tab>
        </b-tabs>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "Manager",
  data() {
    return {
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
      comments: [],
      adoptions: [
        { title: "名稱", type: "text", id: "adoption-title" },
        { title: "地址", type: "text", id: "adoption-subtitle" },
        { title: "聯絡電話", type: "tel", id: "adoption-date" }
      ]
    };
  },
  methods: {
    createArticle() {
      console.log("CLICK", this.title);
      if (!(this.title | this.image | this.introduction | this.content | this.title1 | this.content1 | this.title2 | this.content2 | this.title3 | this.content3 | this.title4 | this.content4 | this.title5 | this.content5 | this.ending)) return false;

      axios.post("http://localhost:3000/comments", {
        title: this.title,
        image: this.image,
        introduction: this.introduction,
        title1: this.title1,
        content1: this.content1,
        title2: this.title2,
        content2: this.content2,
        title3: this.title3,
        content3: this.content3,
        title4: this.title4,
        content4: this.content4,
        title5: this.title5,
        content5: this.content5,
        ending: this.ending
      }).then((res) => {
        console.log(res);
        this.title = "";
        this.image = "";
        this.introduction = "";
        this.title1 = "";
        this.content1 = "";
        this.title2 = "";
        this.content2 = "";
        this.title3 = "";
        this.content3 = "";
        this.title4 = "";
        this.content4 = "";
        this.title5 = "";
        this.content5 = "";
        this.ending = "";
        this.comments.push(res.data);
      });
    }
  },
  // post: {
  //   input: "",
  //   comments: []
  // },
  mounted() {
    axios.get("http://localhost:3000/comments").then((res) => {
      console.log(res);
      this.comments = res.data;
    });
  }
};
</script>

<style>
.right {
  margin: 20px 0 0;
  float: right;
}
</style>
