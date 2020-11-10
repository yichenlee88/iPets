<template>
  <div class="accordion" role="tablist" v-b-scrollspy:listgroup-ex>
    <b-card no-body class="mb-1" v-for="(item, index) in questions" :key="index">
      <b-card-header header-tag="header" class="p-1" role="tab">
        <b-button class="buttonColor" block v-b-toggle="item.text">{{item.text}}</b-button>
      </b-card-header>
      <b-collapse :id="item.text" accordion="my-accordion" role="tabpanel">
        <b-card-body>
          <b-card-text v-html="item.content"></b-card-text>
        </b-card-body>
      </b-collapse>
    </b-card>
  </div>
</template>

<script>
export default {
  name: "FAQ",
  data() {
    return {
      questions: [
        {
          text: "寵物資訊不能修改",
          content:
            "若您遇到此相關問題，請於「聯絡我們」頁面提供給我們異常問題，我們將盡快為排除並為您解答，謝謝您。(http://localhost:8080/#/setting/feedback )"
        },
        {
          text: "寵物不能正常新增",
          content:
            "若您遇到此相關問題，請於「聯絡我們」頁面提供給我們異常問題，我們將盡快為排除並為您解答，謝謝您。(http://localhost:8080/#/setting/feedback )"
        },
        {
          text: "行事曆事件異常",
          content:
            "若您遇到此相關問題，請於「聯絡我們」頁面提供給我們異常問題，我們將盡快為排除並為您解答，謝謝您。(http://localhost:8080/#/setting/feedback )"
        },
        {
          text: "相簿異常",
          content:
            "若您遇到此相關問題，請於「聯絡我們」頁面提供給我們異常問題，我們將盡快為排除疑難雜症並為您解答，謝謝您。(http://localhost:8080/#/setting/feedback )"
        },
        {
          text: "如何刪除使用帳號/寵物",
          content:
            "由於 iPets 系統並未開放刪除帳號及寵物的功能，若需協助刪除，請至「聯絡我們」頁面給予我們回應，我們將盡快回覆您相關事項，謝謝您。(http://localhost:8080/#/setting/feedback )"
        },
        {
          text: "無法取得定位資訊",
          content:
            "若您無法取得定位資訊，請先檢查是否開啟定位權限，並變更你的預設位置資訊設定：<br/>1.在電腦上開啟Chrome。<br/>2.依序按一下右上方的「更多」圖示 [設定]。<br/>3.在「隱私權和安全性」底下，按一下[網站設定]。<br/>4.按一下[位置]。<br/>5.開啟或關閉[存取前詢問我] 設定。"
        },
        {
          text: "寵物辨識無法使用",
          content:
            "若您遇到此相關問題，可能表示未能連線至 iPets 伺服器，您可以嘗試重新登入，或於「聯絡我們」頁面給予我們回應，我們將盡快為您解答，謝謝您。(http://localhost:8080/#/setting/feedback )"
        },
        {
          text: "寵物風格轉換無法使用",
          content:
            "若您遇到此相關問題，可能表示未能連線至 iPets 伺服器，您可以嘗試重新登入，或於「聯絡我們」頁面給予我們回應，我們將盡快為您解答，謝謝您。(http://localhost:8080/#/setting/feedback )"
        },
        {
          text: "錯誤404",
          content:
            "若您的頁面出現「錯誤404」，表示您的網址可能有誤，或是相關連結已經移除，導致無法連結至該頁面。"
        },
        {
          text: "常見問題沒有我的問題",
          content:
            "若以上未能解答您的問題，請至「聯絡我們」頁面，詢問我們相關問題，我們將盡快為您解答！謝謝您！(http://localhost:8080/#/setting/feedback )"
        }
      ]
    };
  }
};
</script>

<style scoped>
.buttonColor {
  background: #b0c4de;
  border: 0;
}
</style>
