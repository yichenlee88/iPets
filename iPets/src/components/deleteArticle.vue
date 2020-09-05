<template>
  <b-container>
    <b-row class="justify-content-md-center" style="margin:20px 0">
      <b-col cols="10" style="margin:0 auto">
        <div v-for="(item, index) in comments" :key="item.id">
          <b-row class="text-left" style="margin-top:20px">
            <b-col cols="8">{{index + 1}}.{{item.title}}</b-col>
            <b-col>
              <b-button pill v-on:click="$bvModal.show(`${index}`)">查看文章</b-button>
              <b-button
                pill
                v-on:click="deletePost(index)"
                style="background: #CD5C5C;border: #CD5C5C"
              >刪除文章</b-button>
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
                  <img class="rounded" v-if="items.img" :src="items.img" style="max-width: 30%">
                </div>
                <p class="content">{{items.content}}</p>
              </div>
              <p class="content-end">{{item.ending}}</p>
            </b-container>
            <b-button block v-on:click="$bvModal.hide(`${index}`)">關閉視窗</b-button>
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
    deletePost(index) {
      let target = this.comments[index];
      if (confirm(`是否刪除 ${target.title} ?`)) {
        axios.delete(`http://localhost:3000/comments/${target.id}`).then((res) => {
          console.log(res);
          this.comments.splice(index, 1);
        }).catch((err) => {
          console.log(err);
        });
      };
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
</style>
