<template>
  <div>
    <div class="container" style="margin-top: 20px;">
      <div class="col-10" style="margin:0 auto 20px auto;">
        <div class="post-inner">
          <div v-for="(item, i) in post.P0001" :key="i">
            <h1 class="title_name">{{item.title}}</h1>
          <img class="rounded" :src="item.src" style="max-width: 80%">
          <p class="content">
            {{item.content[0]}}<br>
            {{item.content[1]}}<br>
            {{item.content[2]}}
          </p>
          <span class="content-title1">{{item.content1[0]}}</span>
          <p class="content">
            {{item.content1[1]}}
            <br>{{item.content1[2]}}
          </p>
          <img
            class="rounded"
            src="https://maoup.com.tw/wp-content/uploads/2018/02/180206_11.png"
            style="max-width: 30%"
          >
          <br>
          <span class="content-title1">•緊張發抖</span>
          <p class="content">
            狗狗緊張恐懼時會立即反應在身體行為上。
            <br>所以遇到兇惡的大狗、看醫生、洗澡、打雷時，都可能讓狗狗抖個不停！
            <br>通常還會伴隨耳朵尾巴下垂、身體蜷曲的行為，這些都是不安的表現喔～！
          </p>
          <img
            class="rounded"
            src="https://maoup.com.tw/wp-content/uploads/2018/02/180206_10.png"
            style="max-width: 30%"
          >
          <br>
          <span class="content-title1">•氣溫過低發抖</span>
          <p class="content">
            氣溫過低的時候，狗狗也會透過發抖來產生熱能。
            <br>當狗狗已經冷到發抖，快帶狗狗回室內、增添衣物吧！
            <br>尤其高齡毛孩身體虛弱更不受凍，冬天更要注意保暖喔！
          </p>
          <img
            class="rounded"
            src="https://maoup.com.tw/wp-content/uploads/2018/02/180206_8.png"
            style="max-width: 30%"
          >
          <br>
          <span class="content-title1">•疾病發抖</span>
          <p class="content">
            若狗狗發抖並非上述原因，同期間出現疲勞、嗜睡、虛弱的症狀時，
            <br>很可能是身體疾病引發的發抖，例如：腎臟病、神經疾病、關節疼痛…等等。
            <br>尤其高齡狗狗更容易莫名發抖，這些都是身體警訊！請飼主趕緊送醫檢查喔！
          </p>
          <img
            class="rounded"
            src="https://maoup.com.tw/wp-content/uploads/2018/02/180206_9.png"
            style="max-width: 30%"
          >
          <br>
          <span class="content-title1">•中毒發抖</span>
          <p class="content">
            各種發抖原因中，最危險的就是中毒反應！
            <br>當誤食有害物質，狗狗可能出現發抖、抽搐、全身無力、嘔吐…等等反應。
            <br>一旦發現異常，請及時送到寵物醫院接受正確治療喔！
          </p>
          <img
            class="rounded"
            src="https://maoup.com.tw/wp-content/uploads/2018/02/180206_7.png"
            style="max-width: 30%"
          >
          <br>
          <br>
          <p class="content-end">
            原來狗狗發抖的原因這麼多！
            <br>請飼主釐清發抖原因，萬一是疾病或中毒引起，一定要趕快就醫喔！
          </p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "dogScienceArticle",
  data() {
    return {
      post: ""
    };
  },
  mounted() {
    this.$http.get("/static/article.json").then(response => {
      console.log(response.data);
      this.post = response.data;
    });
  }
};
</script>

<style scoped>
.post-inner {
  padding: 20px;
  /* box-shadow: 0px 0px 2px gray ; */
}
.title_name {
  font-size: 30px;
  font-family: "微軟正黑體", "Microsoft JhengHei", Arial, Georgia, serif;
}
.content {
  font-size: 20px;
}
.content-title1 {
  border-bottom: 2px dotted #dccb8a;
  padding: 10px 25px;
  background: rgba(255, 246, 212, 0.71);
  line-height: 4;
  border-radius: 25px;
  font-size: 16pt;
  color: #8f7061;
  font-weight: 900;
}
.content-end {
  font-size: 25px;
  color: #003377;
}
</style>
