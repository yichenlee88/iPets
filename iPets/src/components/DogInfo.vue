<template>
  <!--https://www.hk01.com/%E5%AF%B5%E7%89%A9/202205/%E4%BA%86%E8%A7%A35%E5%80%8B%E7%8B%97%E7%8B%97%E5%B8%B8%E8%A6%8B%E5%BF%83%E7%90%86%E7%8B%80%E6%85%8B-%E8%AE%93%E4%BA%BA%E5%AF%B5%E9%97%9C%E4%BF%82%E6%9B%B4%E5%92%8C%E7%9D%A6-->
  <div>
    <h1 class="title top">人與狗的關係</h1>
    <img
      v-for="n in 5" :key="n"
      class="d-block img-fluid"
      :src="`../static/img/dogInfo-0${ n }.png`"
    >
  </div>
</template>

<script>
export default {
  name: "dogInfo"
};
</script>

<style scoped>
.top {
  margin-top: 20px;
}
.img-shadow {
  box-shadow:3px 3px 12px gray;
}
.title {
  padding: 10px;
  text-align: center;
  margin: auto;
  max-width: 300px;
}
</style>
