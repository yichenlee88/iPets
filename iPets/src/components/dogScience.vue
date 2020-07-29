<template>
  <div class="container" style="margin-top: 20px;">
    <img src="../assets/狗狗科普.png" class="banner">
    <div class="container marketing" style="margin-top:20px">
      <div class="row">
        <div class="col-12 col-sm-4" v-for="(item, index) in comments" :key="index">
          <div class="img-thumbnail" style="margin-left: 15px; height: 520px;">
            <img class="rounded" :src="item.image" style="max-width: 100%">
            <H5>
              <br>
              {{item.title}}
            </H5>
            <p>{{ item.introduction }}</p>
            <a href="#/dogScience/post" target="_blank">
              <button
                type="button"
                class="btn btn-info"
                style="position: absolute; right: 20px; bottom: 5px;"
              >了解更多</button>
            </a>
          </div>
        </div>
      </div>
    </div>
    <router-view></router-view>
  </div>
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
    axios.get("http://localhost:3000/comments").then(res => {
      console.log(res);
      this.comments = res.data;
    });
  }
};
</script>

<style scoped>
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
</style>
