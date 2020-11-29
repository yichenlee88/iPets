<template>
  <b-container>
    <img src="../assets/狗狗科普.png" class="banner" style="margin-top:20px">
    <div class="container marketing">
      <div class="row">
        <div
          class="col-12 col-sm-4"
          v-for="(item, index) in comments.reverse()"
          :key="index"
          style="margin-top: 20px"
        >
          <div class="img-thumbnail" style="margin-left: 15px; height: 520px;">
            <img class="rounded" :src="item.image" style="max-width: 100%">
            <H5>
              <br>
              {{item.title}}
            </H5>
            <p>{{ item.introduction }}</p>
            <a :href="'#/post/' + item.id" target="_blank">
              <button
                type="button"
                class="btn btn-info"
                style="position: absolute; right: 20px; bottom: 5px; background-color: #5F9EA0; border:0;"
              >了解更多</button>
            </a>
          </div>
        </div>
      </div>
    </div>
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
