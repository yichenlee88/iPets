<template>
  <div id="Manage">
      <b-container>
        <b-row class="my-1" style="text-align:center">
          <b-col cols="12">目前登入帳號：10646043</b-col>
          <b-col cols="12">管理員名稱：蔡欣恬</b-col>
          <b-col cols="12">管理權限：最高管理員</b-col>
        </b-row>
        <div class="center">
          <router-link to="/manage/manageArticle">管理文章</router-link>
          <router-link to="/manage/manageAdoption">管理領養機構</router-link>
          <router-link to="/manage/manageContact">管理使用者回饋</router-link>
          <router-view></router-view>
        </div>
      </b-container>
  </div>
</template>

<script>
export default {
  name: "Manage",
  data() {
    return {};
  }
};
</script>

<style>
.center {
  text-align: center;
}
</style>
