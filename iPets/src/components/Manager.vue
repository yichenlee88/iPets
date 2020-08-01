<template>
  <div id="manager">
    <b-container>
      <div id="manager-info" style="margin:20px 0">
        <b-row class="my-1" style="text-align:center">
          <b-col cols="12">目前登入帳號：10646043</b-col>
          <b-col cols="12">管理員名稱：蔡欣恬</b-col>
          <b-col cols="12">管理權限：最高管理員</b-col>
          <b-col cols="12">
            <b-button style="margin:20px 0" v-b-toggle.sidebar-variant>Toggle Sidebar</b-button>
          </b-col>
        </b-row>
      </div>
    <b-sidebar id="sidebar-variant" title="Sidebar" bg-variant="dark" text-variant="light" shadow>
      <div class="px-3 py-2">
        <p>
          Cras mattis consectetur purus sit amet fermentum. Cras justo odio, dapibus ac facilisis
          in, egestas eget quam. Morbi leo risus, porta ac consectetur ac, vestibulum at eros.
        </p>
        <b-img src="https://picsum.photos/500/500/?image=54" fluid thumbnail></b-img>
      </div>
    </b-sidebar>
      <b-tabs content-class="mt-3" style="margin:20px 0" justified>
        <b-tab title="新增文章" active>
          <b-row class="justify-content-md-center" style="margin:20px 0">
            <b-col cols="10" style="margin:0 auto">
              <b-card bg-variant="light" style="margin-top:20px">
                <b-row class="my-1">
                  <b-col sm="2">
                    <label for="article_title">標題:</label>
                  </b-col>
                  <b-col sm="10">
                    <b-form-input
                      id="article_title" type="text" placeholder="Title" v-model.trim="title"
                    ></b-form-input>
                  </b-col>
                </b-row>
                <b-row class="my-1">
                  <b-col sm="2">
                    <label for="article_image">標題圖片位址:</label>
                  </b-col>
                  <b-col sm="10">
                    <b-form-input
                      id="article_image" type="url" placeholder="Input title_image url" v-model.trim="image"
                    ></b-form-input>
                  </b-col>
                </b-row>
                <b-row class="my-1">
                  <b-col sm="2">
                    <label for="article_introduction">簡介:</label>
                  </b-col>
                  <b-col sm="10">
                    <b-form-input
                      id="article_introduction" type="text" placeholder="Introduction(建議50-100字)" v-model.trim="introduction"
                    ></b-form-input>
                  </b-col>
                </b-row>
                <b-row class="my-1">
                  <b-col sm="2">
                    <label for="article_title1">標題1:</label>
                  </b-col>
                  <b-col sm="10">
                    <b-form-input
                      id="article_title1" type="text" placeholder="Title 1" v-model.trim="title1"
                    ></b-form-input>
                  </b-col>
                </b-row>
                <b-row class="my-1">
                  <b-col sm="2">
                    <label for="article_image">標題1 圖片位址:</label>
                  </b-col>
                  <b-col sm="10">
                    <b-form-input
                      id="article_image1" type="url" placeholder="Input image_1 url" v-model.trim="img1"
                    ></b-form-input>
                  </b-col>
                </b-row>
                <b-row class="my-1">
                  <b-col sm="2">
                    <label for="article_content1">內容1:</label>
                  </b-col>
                  <b-col sm="10">
                    <b-form-textarea
                      id="article_content1" placeholder="Enter content 1" rows="2" v-model.trim="content1"
                    ></b-form-textarea>
                  </b-col>
                </b-row>
                <b-row class="my-1">
                  <b-col sm="2">
                    <label for="article_title2">標題2:</label>
                  </b-col>
                  <b-col sm="10">
                    <b-form-input id="article_title2" placeholder="Title 2" v-model.trim="title2"></b-form-input>
                  </b-col>
                </b-row>
                <b-row class="my-1">
                  <b-col sm="2">
                    <label for="article_image">標題2 圖片位址:</label>
                  </b-col>
                  <b-col sm="10">
                    <b-form-input
                      id="article_image2" type="url" placeholder="Input image_2 url" v-model.trim="img2"
                    ></b-form-input>
                  </b-col>
                </b-row>
                <b-row class="my-1">
                  <b-col sm="2">
                    <label for="article_content2">內容2:</label>
                  </b-col>
                  <b-col sm="10">
                    <b-form-textarea
                      id="article_content2" placeholder="Enter content 2" rows="2" v-model.trim="content2"
                    ></b-form-textarea>
                  </b-col>
                </b-row>
                <b-row class="my-1">
                  <b-col sm="2">
                    <label for="article_title3">標題3:</label>
                  </b-col>
                  <b-col sm="10">
                    <b-form-input
                      id="article_title3" type="text" placeholder="Title 3" v-model.trim="title3"
                    ></b-form-input>
                  </b-col>
                </b-row>
                <b-row class="my-1">
                  <b-col sm="2">
                    <label for="article_image">標題3 圖片位址:</label>
                  </b-col>
                  <b-col sm="10">
                    <b-form-input
                      id="article_image3" type="url" placeholder="Input image_3 url" v-model.trim="img3"
                    ></b-form-input>
                  </b-col>
                </b-row>
                <b-row class="my-1">
                  <b-col sm="2">
                    <label for="article_content3">內容3:</label>
                  </b-col>
                  <b-col sm="10">
                    <b-form-textarea
                      id="article_content3" placeholder="Enter content 3" rows="2" v-model.trim="content3"
                    ></b-form-textarea>
                  </b-col>
                </b-row>
                <b-row class="my-1">
                  <b-col sm="2">
                    <label for="article_title4">標題4:</label>
                  </b-col>
                  <b-col sm="10">
                    <b-form-input
                      id="article_title4" type="text" placeholder="Title 4" v-model.trim="title4"
                    ></b-form-input>
                  </b-col>
                </b-row>
                <b-row class="my-1">
                  <b-col sm="2">
                    <label for="article_image">標題4 圖片位址:</label>
                  </b-col>
                  <b-col sm="10">
                    <b-form-input
                      id="article_image4" type="url" placeholder="Input image_4 url" v-model.trim="img4"
                    ></b-form-input>
                  </b-col>
                </b-row>
                <b-row class="my-1">
                  <b-col sm="2">
                    <label for="article_content4">內容4:</label>
                  </b-col>
                  <b-col sm="10">
                    <b-form-textarea
                      id="article_content4" placeholder="Enter content 4" rows="2" v-model.trim="content4"
                    ></b-form-textarea>
                  </b-col>
                </b-row>
                <b-row class="my-1">
                  <b-col sm="2">
                    <label for="article_title5">標題5:</label>
                  </b-col>
                  <b-col sm="10">
                    <b-form-input
                      id="article_title5" type="text" placeholder="Title 5" v-model.trim="title5"
                    ></b-form-input>
                  </b-col>
                </b-row>
                <b-row class="my-1">
                  <b-col sm="2">
                    <label for="article_image">標題5 圖片位址:</label>
                  </b-col>
                  <b-col sm="10">
                    <b-form-input
                      id="article_image5" type="url" placeholder="Input image_5 url" v-model.trim="img5"
                    ></b-form-input>
                  </b-col>
                </b-row>
                <b-row class="my-1">
                  <b-col sm="2">
                    <label for="article_content5">內容5:</label>
                  </b-col>
                  <b-col sm="10">
                    <b-form-textarea
                      id="article_content5" placeholder="Enter content 5" rows="2" v-model.trim="content5"
                    ></b-form-textarea>
                  </b-col>
                </b-row>
                <b-row class="my-1">
                  <b-col sm="2">
                    <label for="article_ending">結語:</label>
                  </b-col>
                  <b-col sm="10">
                    <b-form-input
                      id="article_ending" type="text" placeholder="Ending..." v-model.trim="ending"
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
              <b-card bg-variant="light" style="margin-top:20px">
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
    </b-container>
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
      img1: "",
      content1: "",
      title2: "",
      img2: "",
      content2: "",
      title3: "",
      img3: "",
      content3: "",
      title4: "",
      img4: "",
      content4: "",
      title5: "",
      img5: "",
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
      axios
        .post("http://localhost:3000/comments", {
          title: this.title,
          image: this.image,
          introduction: this.introduction,
          title1: this.title1,
          img1: this.img1,
          content1: this.content1,
          title2: this.title2,
          img2: this.img1,
          content2: this.content2,
          title3: this.title3,
          img3: this.img1,
          content3: this.content3,
          title4: this.title4,
          img4: this.img1,
          content4: this.content4,
          title5: this.title5,
          img5: this.img1,
          content5: this.content5,
          ending: this.ending
        })
        .then(res => {
          console.log(res);
          this.title = "";
          this.image = "";
          this.introduction = "";
          this.title1 = "";
          this.img1 = "";
          this.content1 = "";
          this.title2 = "";
          this.img2 = "";
          this.content2 = "";
          this.title3 = "";
          this.img3 = "";
          this.content3 = "";
          this.title4 = "";
          this.img4 = "";
          this.content4 = "";
          this.title5 = "";
          this.img5 = "";
          this.content5 = "";
          this.ending = "";
          this.comments.push(res.data);
        });
    }
  },
  mounted() {
    axios.get("http://localhost:3000/comments").then(res => {
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
