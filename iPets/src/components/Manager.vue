<template>
  <div id="manager">
    <div id="content" class="container">
      <div>
        <div id="manager-info" style="margin:20px 0">
          <b-row class="my-1" style="text-align:center">
            <b-col cols="12">目前登入帳號：10646043</b-col>
            <b-col cols="12">管理員名稱：蔡欣恬</b-col>
            <b-col cols="12">管理權限：最高管理員</b-col>
          </b-row>
        </div>
        <b-tabs content-class="mt-3" style="margin:20px 0" justified>
          <b-tab title="新增文章" active>
            <b-row class="justify-content-md-center" style="margin:20px 0">
              <b-col cols="10" style="margin:0 auto">
                <b-card bg-variant="light">
                  <b-row class="my-1" v-for="(index) in articles" :key="index">
                    <b-col sm="2">
                      <label :for="`title-${index.title}`">{{ index.title }}:</label>
                    </b-col>
                    <b-col sm="10">
                      <b-form-input :id="`type-${index.id}`" :type="index.type"></b-form-input>
                    </b-col>
                  </b-row>
                  <b-row class="my-1">
                    <b-col sm="2">
                      <label for="title-textarea">文章內容:</label>
                    </b-col>
                    <b-col sm="10">
                      <b-form-textarea
                        id="textarea-auto-height"
                        placeholder="Enter content..."
                        rows="3"
                      ></b-form-textarea>
                    </b-col>
                  </b-row>
                  <b-button class="right">確認新增文章</b-button>
                </b-card>
              </b-col>
            </b-row>
          </b-tab>
          <b-tab title="新增領養機構">
            <b-row class="justify-content-md-center" style="margin:20px 0">
              <b-col cols="10" style="margin:0 auto">
                <b-card bg-variant="light">
                  <b-row class="my-1" v-for="(index) in adoptions" :key="index">
                    <b-col sm="2">
                      <label :for="`title-${index.title}`">{{ index.title }}:</label>
                    </b-col>
                    <b-col sm="10">
                      <b-form-input :id="`type-${index.id}`" :type="index.type"></b-form-input>
                    </b-col>
                  </b-row>
                  <b-row class="my-1">
                    <b-col sm="2">
                      <label for="title-textarea">簡介:</label>
                    </b-col>
                    <b-col sm="10">
                      <b-form-textarea
                        id="textarea-auto-height"
                        placeholder="Enter content..."
                        rows="3"
                      ></b-form-textarea>
                    </b-col>
                  </b-row>
                  <b-button class="right">確認新增領養機構</b-button>
                </b-card>
              </b-col>
            </b-row>
          </b-tab>
          <b-tab title="使用者帳號管理">
            <b-row class="justify-content-md-center" style="margin:20px 0">
              <b-col cols="10" style="margin:0 auto">
                <p>查詢使用者帳號密碼</p>
                <b-card bg-variant="light">
                  <b-row class="my-1">
                    <b-col sm="3">
                      <label>使用者帳號:</label>
                    </b-col>
                    <b-col sm="9">
                      <b-form-input :id="user_account" type="text"></b-form-input>
                    </b-col>
                  </b-row>
                  <b-button class="right">查詢帳號密碼</b-button>
                </b-card>
                <p style="margin:20px 0">編輯使用者帳號密碼</p>
                <b-card bg-variant="light" style="margin:20px 0">
                  <b-row class="my-1">
                    <b-col sm="3">
                      <label>使用者帳號:</label>
                    </b-col>
                    <b-col sm="9">
                      <b-form-input :id="user_account" type="text"></b-form-input>
                    </b-col>
                    <b-col sm="3">
                      <label>更改使用者密碼:</label>
                    </b-col>
                    <b-col sm="9">
                      <b-form-input :id="user_account" type="text"></b-form-input>
                    </b-col>
                    <b-col sm="3">
                      <label>再次確認使用者密碼:</label>
                    </b-col>
                    <b-col sm="9">
                      <b-form-input :id="user_account" type="text"></b-form-input>
                    </b-col>
                  </b-row>
                  <b-button class="right">確認編輯</b-button>
                </b-card>
              </b-col>
            </b-row>
          </b-tab>
        </b-tabs>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "Manager",
  data() {
    return {
      articles: [
        { title: "標題", type: "text", id: "title" },
        { title: "副標題", type: "text", id: "subtitle" },
        { title: "新增日期", type: "date", id: "date" }
      ],
      adoptions: [
        { title: "名稱", type: "text", id: "title" },
        { title: "地址", type: "text", id: "subtitle" },
        { title: "聯絡電話", type: "tel", id: "date" }
      ]
    };
  }
};
</script>

<style>
.right {
  margin: 20px 0 0;
  float: right;
}
</style>
