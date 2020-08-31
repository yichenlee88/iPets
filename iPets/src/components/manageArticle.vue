<template>
  <div id="manageArticle">
    <b-container style="margin-top:20px">
      <div class="center">
        <router-link to="/Manage/manageArticle/createArticle">新增文章</router-link>
        <router-link to="/Manage/manageArticle/deleteArticle">刪除文章</router-link>
        <router-link to="/Manage/manageArticle/deleteArticle">編輯文章</router-link>
        <router-view></router-view>
      </div>
    </b-container>
  </div>
</template>

<script>
export default {
  name: "manageArticle",
  data() {}
};
</script>

<style>
.center {
  text-align: center;
}
</style>
