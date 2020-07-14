<template>
  <div class="container" style="margin-top: 20px;">
    <img src="../assets/狗狗科普.png" class="center">
    <br>
    <div class="container marketing">
      <div class="row">
        <div class="col-12 col-sm-4"  v-for="(item, id) in comments" :key="id">
          <div class="img-thumbnail" style="margin-left: 15px; height: 520px;">
            <img
              class="rounded"
              :src="item.image"
              style="max-width: 100%"
            >
            <H4>
              <br>{{item.title}}
            </H4>
            <p>{{ item.introduction }}</p>
            <a href="#/dogScienceArticle" target="_blank">
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
    <div class="container marketing" style="margin-top: 20px;">
      <div class="row">
        <div class="col-12 col-sm-4">
          <div class="img-thumbnail" style="margin-left: 15px; height: 520px;">
            <img
              class="rounded"
              src="https://maoup.com.tw/wp-content/uploads/2018/02/180206_23-1024x768.png"
              style="max-width: 100%"
            >
            <H4>
              <br>狗狗發抖危險程度大不同！快來釐清發抖原因！
            </H4>
            <p>狗狗發抖一定是覺得冷嗎？小心！各種發抖的危險程度大不同！當狗狗興奮、緊張、氣溫過低、疾病疼痛、中毒時都會發抖～請飼主釐清發抖原因，萬一是疾病或中毒引起，一定要趕快就醫喔！</p>
            <a href="https://maoup.com.tw/?p=13939" target="_blank">
              <button
                type="button"
                class="btn btn-info"
                style="position: absolute; right: 20px; bottom: 5px;"
              >了解更多</button>
            </a>
          </div>
        </div>
        <div class="col-12 col-sm-4">
          <div class="img-thumbnail" style="height: 520px;">
            <img
              class="rounded"
              src="https://maoup.com.tw/wp-content/uploads/2020/02/191009_%E5%B0%8F%E7%8B%97%E8%83%961%E5%85%AC%E6%96%A4%E7%AD%89%E6%96%BC%E4%BA%BA%E8%83%9620%E5%85%AC%E6%96%A4.jpg"
              style="max-width: 100%;"
            >
            <H4>
              <br>驚！小狗胖1公斤，竟等於人胖20公斤？！
            </H4>
            <p>根據國外的研究調查，以一隻3公斤的小狗來說，每增重1公斤約等於：160公分的女性增重18公斤！180公分的男性增加22公斤！</p>
            <a href="https://maoup.com.tw/?p=23482" target="_blank">
              <button
                type="button"
                class="btn btn-info"
                style="position: absolute; right: 20px; bottom: 5px;"
              >了解更多</button>
            </a>
          </div>
        </div>
        <div class="col-12 col-sm-4">
          <div class="img-thumbnail" style="height: 520px;">
            <img
              class="rounded"
              src="https://maoup.com.tw/wp-content/uploads/2020/03/180802_3-1024x768.png"
              style="max-width: 100%;ASDF"
            >
            <H4>
              <br>對零食沒興趣的狗狗，該怎麼訓練？
            </H4>
            <p>看別人家的汪汪都會乖乖的坐下、握手，怎麼我家的就是學不會呢？別生氣～就像人類小孩也需要因材施教，其實你可能只是沒找到狗狗的心頭好，快跟著毛起來看下去～</p>
            <a href="https://maoup.com.tw/?p=23571" target="_blank">
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
    <br>
    <br>
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

.center {
  display: block;
  margin-left: auto;
  margin-right: auto;
  width: 80%;
}
</style>
