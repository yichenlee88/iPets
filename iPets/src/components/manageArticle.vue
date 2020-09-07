<template>
  <b-container style="margin-top:20px">
    <div class="center">
      <router-link to="/manage/manageArticle/createArticle">新增文章</router-link>
      <router-link to="/manage/manageArticle/deleteArticle">刪除文章</router-link>
      <router-link to="/manage/manageArticle/editArticle">編輯文章</router-link>
      <router-view></router-view>
    </div>
  </b-container>
</template>

<script>
export default {
  name: "ManageArticle",
  data() {}
};
</script>

<style>
.center {
  text-align: center;
}
</style>
