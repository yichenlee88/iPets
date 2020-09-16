<template>
  <div>
    <b-container>
      <b-row class="justify-content-md-center" style="margin:20px 0">
        <b-col cols="10" style="margin:0 auto">
          <div v-for="(item, index) in comments" :key="index">
            <b-row class="text-left" style="margin-top:20px">
              <b-col cols="9">{{item.id}}.{{item.title}}</b-col>
              <b-col>
                <b-button pill v-on:click="$bvModal.show(`${index}`)">查看 / 編輯文章</b-button>
              </b-col>
            </b-row>
            <b-modal hide-footer size="lg" :id="`${index}`" :title="item.title">
              <div class="center" style="margin-top:20px">
                <img :src="`${item.image}`" style="max-width: 60%;">
              </div>
              <b-container style="padding:20px 120px;">
                <p class="content">{{item.introduction}}</p>
                <div v-for="(items, i) in item.contents" :key="i">
                  <div style="text-align: center">
                    <span class="content-title" v-if="items.title">{{items.title}}</span>
                    <br>
                    <img v-if="items.img" :src="items.img" style="max-width: 30%">
                  </div>
                  <p class="content">{{items.content}}</p>
                </div>
                <p class="content-end">{{item.ending}}</p>
              </b-container>
              <b-button block v-on:click="$bvModal.show(`${item.title}`)">編輯文章</b-button>
              <b-button block v-on:click="$bvModal.hide(`${index}`)">關閉視窗</b-button>
            </b-modal>
            <b-modal hide-footer size="lg" :id="`${item.title}`">
              <b-container>
                <b-row class="justify-content-md-center" style="margin:20px 0">
                  <b-col cols="10" style="margin:0 auto">
                    <b-card bg-variant="light">
                      <b-row class="my-1">
                        <b-col sm="2">
                          <label for="article_title">id:</label>
                        </b-col>
                        <b-col sm="10">
                          {{item.id}}
                        </b-col>
                      </b-row>
                      <b-row class="my-1">
                        <b-col sm="2">
                          <label for="article_title">標題:</label>
                        </b-col>
                        <b-col sm="10">
                          <b-form-input
                            id="article_title"
                            type="text"
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
                            :placeholder="item.introduction"
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
                              :value="comment.title"
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
                      <b-button class="left2" v-on:click="deleteRow">刪除內容</b-button>
                      <b-button class="right" v-on:click="updateArticle">確認儲存文章</b-button>
                    </b-card>
                  </b-col>
                </b-row>
              </b-container>
            </b-modal>
          </div>
        </b-col>
      </b-row>
    </b-container>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "EditArticle",
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
    viewPost(index) {
      this.$refs["index"].show();
    },
    addRow() {
      console.log("Add new row");
      this.contents.push({ content: "", title: "", img: "" });
    },
    deleteRow(index) {
      console.log("Delete row");
      this.contents.splice(this.contents.length - 1, 1);
    },
    updateArticle() {
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
  margin-top: 20px;
  float: right;
}
.left {
  margin-top: 20px;
  float: left;
}
.left2 {
  margin: 20px 20px 0;
  float: left;
}
</style>
