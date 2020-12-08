<template>
  <b-container>
    <h1 class="title top">狗狗科普</h1>
    <img src="../assets/狗狗科普.png" class="banner" style="margin-top:20px" />
    <div class="container marketing">
      <div class="row">
        <div
          class="col-12 col-sm-4"
          v-for="(item, index) in comments.reverse()"
          :key="index"
          style="margin-top: 20px"
        >
          <div
            class="img-thumbnail"
            style="margin-left: 15px; min-height: 540px;"
          >
            <img class="rounded" :src="item.image" style="max-width: 100%;" />
            <H5>
              <br />
              {{ item.title }}
            </H5>
            <p>{{ item.introduction }}</p>
            <a :href="'#/post/' + item.id" target="_blank">
              <b-button class="btn learnMoreButton" variant="outline-primary"
                >了解更多</b-button
              >
            </a>
          </div>
        </div>
      </div>
    </div>
    <p style="margin-left: 20px; font-size: 20px">
      本文來自《毛小孩》網站：<b-link href="https://maoup.com.tw/"
        >https://maoup.com.tw/</b-link
      >
    </p>
  </b-container>
</template>

<script>
import axios from "axios";
export default {
  name: "dogScience",
  data() {
    return {
      title: "",
      image: "",
      introduction: "",
      comments: []
    };
  },
  mounted() {
    this.getAricle();
  },
  methods: {
    getAricle() {
      axios.get("http://localhost:3000/comments").then(res => {
        console.log(res);
        this.comments = res.data;
      });
    }
  }
};
</script>

<style scoped>
.top {
  margin-top: 20px;
}

.title {
  padding: 10px;
  text-align: center;
  margin: auto;
  max-width: 300px;
}

.columnberight {
  display: flex;
  justify-content: center;
  align-items: center;
}

.banner {
  display: block;
  margin-left: auto;
  margin-right: auto;
  width: 100%;
}

.learnMoreButton {
  position: absolute;
  right: 20px;
  bottom: 20px;
}
</style>
