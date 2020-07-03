<template>
  <b-container>
    <!-- Start -- 新增寵物 -->
    <b-card id="card-create-pet" v-if="!show">
      <b-card-header header-bg-variant="dark" header-text-variant="white">
        <div>
          <i class="fas fa-plus fa-2x mr-1"></i>
          <span style="font-size: 28px;">新增毛小孩</span>
        </div>
      </b-card-header>
      <b-card-body>
        <b-form class="px-3" @submit="onSubmit">
          <b-form-group
            id="group-pet-name"
            label="名字"
            label-for="input-pet-name"
          >
            <b-form-input
              id="input-pet-name"
              type="text"
              v-model="form.name"
            ></b-form-input>
          </b-form-group>
          <b-form-group
            id="group-pet-breed"
            label="品種"
            label-for="select-pet-breed"
          >
            <b-form-select
              id="select-pet-breed"
              v-model="form.breed"
              :options="breed_options"
            ></b-form-select>
          </b-form-group>
          <b-form-group
            id="group-pet-gender"
            label="性別"
            label-for="select-pet-gender"
          >
            <b-form-select
              id="select-pet-gender"
              v-model="form.gender"
              :options="gender_options"
            ></b-form-select>
          </b-form-group>
          <div class="d-flex flex-row-reverse">
            <b-button type="submit" variant="dark">建立</b-button>
          </div>
        </b-form>
      </b-card-body>
    </b-card>
    <!-- End -- 新增寵物 -->

    <!-- Start -- 寵物簡介 -->
    <b-card
      v-if="show"
      v-bind:img-src="pet.image"
      class="mb-3"
      img-left
      img-width="250"
    >
      <b-card-body :title="pet.name">
        <b-card-text v-if="pet.gender">
          沒看過帥哥膩
        </b-card-text>
        <b-card-text v-else>
          人家是女森
        </b-card-text>
        <b-card-text>
          {{ pet.breed }}
        </b-card-text>
      </b-card-body>
    </b-card>
    <!-- End -- 寵物簡介 -->

    <!-- Start -- 進行中 -->
    <b-list-group v-if="show">
      <h1>進行中</h1>
      <b-list-group-item
        v-for="info in pet_info"
        :key="info.event_name"
        class="d-flex align-items-center"
      >
        <input class="mr-3" type="checkbox" />
        <div class="overview mr-auto">
          <span class="title mb-0">{{ info.event_name }}</span>
          <div>
            <b-badge
              v-if="badge_today(info.next_time)"
              variant="primary"
              class="mr-1"
              >今日</b-badge
            >
            <b-badge
              v-if="badge_overdue(info.next_time)"
              variant="danger"
              class="mr-1"
              >逾期</b-badge
            >
            <i class="far fa-calendar-alt"></i>
            <span class="date mb-0">{{
              convert_timestamp(info.next_time)
            }}</span>
          </div>
        </div>
        <button
          type="button"
          class="close"
          @click="$bvModal.show(`${info.event_name}`)"
        >
          <!-- <i class="arrow right"></i> -->
          <i class="fas fa-cog"></i>
        </button>
        <b-modal
          :id="`${info.event_name}`"
          centered
          title="編輯頁面"
          ok-title="保存"
          cancel-title="取消"
          @ok="handleOk($event, info.event_name)"
        >
          <template v-slot:modal-chancel>取消</template>
          <b-form>
            <b-form-group id="group-frequence" label="重複頻率" label-cols="3">
              <b-form-input v-model="info.period"></b-form-input>
              <b-form-select
                v-model="info.periodUnit"
                :options="periodUnitOptions"
                value-field="item"
                text-field="name"
              ></b-form-select>
            </b-form-group>
          </b-form>
        </b-modal>
      </b-list-group-item>
    </b-list-group>
    <!-- End -- 進行中 -->
  </b-container>
</template>

<script>
// Import required dependencies
import "bootstrap/dist/css/bootstrap.css";

// Import this component
import datePicker from "vue-bootstrap-datetimepicker";

// Import date picker css
import "pc-bootstrap4-datetimepicker/build/css/bootstrap-datetimepicker.css";
import { Info } from "../firebase/pet";
import { db } from "../db";
const fStore = db.firestore();

export default {
  data() {
    return {
      name: "HomeLogin",
      date: new Date(),
      options: {
        format: "DD/MM/YYYY",
        useCurrent: false
      },
      breed_options: [
        { value: null, text: "選擇品種", disabled: true },
        { value: "台灣土狗", text: "台灣土狗" },
        { value: "柴犬", text: "柴犬" },
        { value: "黃金獵犬", text: "黃金獵犬" },
        { value: "拉布拉多", text: "拉布拉多" },
        { value: "哈士奇", text: "哈士奇" },
        { value: "藏獒", text: "藏獒" },
        { value: "貴賓犬", text: "貴賓犬," },
        { value: "薩摩耶", text: "薩摩耶" },
        { value: "博美", text: "博美" },
        { value: "迷你雪納瑞", text: "迷你雪納瑞" },
        { value: "蘇格蘭牧羊犬", text: "蘇格蘭牧羊犬" },
        { value: "松獅", text: "松獅" },
        { value: "獅子犬", text: "獅子犬" },
        { value: "吉娃娃", text: "吉娃娃" },
        { value: "阿拉斯加雪橇犬", text: "阿拉斯加雪橇犬" },
        { value: "喜樂蒂牧羊犬", text: "喜樂蒂牧羊犬" },
        { value: "美國可卡犬", text: "美國可卡犬" },
        { value: "英國古代牧羊犬", text: "英國古代牧羊犬" },
        { value: "捲毛比熊犬", text: "捲毛比熊犬" },
        { value: "義大利靈緹", text: "義大利靈緹" },
        { value: "蝴蝶犬", text: "蝴蝶犬" },
        { value: "雪納瑞", text: "雪納瑞" },
        { value: "邊境牧羊犬", text: "邊境牧羊犬" },
        { value: "德國牧羊犬", text: "德國牧羊犬" },
        { value: "阿富汗獵犬", text: "阿富汗獵犬" },
        { value: "大麥町", text: "大麥町" },
        { value: "西施犬", text: "西施犬" },
        { value: "英國可卡犬", text: "英國可卡犬" },
        { value: "臘腸犬", text: "臘腸犬" },
        { value: "巴哥犬", text: "巴哥犬" },
        { value: "約克夏", text: "約克夏" },
        { value: "柯基", text: "柯基" },
        { value: "日本銀狐", text: "日本銀狐" },
        { value: "迷你杜賓犬", text: "迷你杜賓犬" },
        { value: "大白熊犬", text: "大白熊犬" },
        { value: "米格魯", text: "米格魯" },
        { value: "羅威納犬", text: "羅威納犬" },
        { value: "中國沙皮犬", text: "中國沙皮犬" },
        { value: "西部高地白梗", text: "西部高地白梗" },
        { value: "愛斯基摩犬", text: "愛斯基摩犬" },
        { value: "聖伯納犬", text: "聖伯納犬" },
        { value: "鬥牛犬", text: "鬥牛犬" },
        { value: "西部高地白梗", text: "西部高地白梗" },
        { value: "拉薩犬", text: "拉薩犬" },
        { value: "馬爾濟斯犬", text: "馬爾濟斯犬" },
        { value: "萬能梗", text: "萬能梗" },
        { value: "狼狗", text: "狼狗" },
        { value: "德國杜賓犬", text: "德國杜賓犬" },
        { value: "牛頭梗", text: "牛頭梗" },
        { value: "日本柴犬", text: "日本柴犬" }
      ],
      gender_options: [
        { value: null, text: "選擇性別", disabled: true },
        { value: true, text: "男孩" },
        { value: false, text: "女孩" }
      ],
      form: {
        name: null,
        breed: null,
        gender: null,
        image:
          "http://icons.iconarchive.com/icons/google/noto-emoji-animals-nature/1024/22214-dog-face-icon.png"
      },
      periodUnitOptions: [
        { item: "day", name: "天" },
        { item: "week", name: "週" },
        { item: "month", name: "個月" }
      ]
    };
  },
  components: {
    datePicker
  },
  methods: {
    onSubmit(e) {
      e.preventDefault();
      var docRef = fStore.collection("pets").doc();
      docRef
        .set({
          name: this.form.name,
          breed: this.form.breed,
          gender: this.form.gender,
          master_uid: this.uid,
          image: this.form.image,
          timestamp: new Date()
        })
        .then(() => {
          var infoRef = docRef.collection("info");
          var infoList = [
            new Info("洗澡", 0, "day"),
            new Info("除蟲", 2, "week"),
            new Info("指甲", 1, "month")
          ];

          infoList.forEach(function(item) {
            infoRef.doc(item.eventName).set({
              next_time: item.getNext(),
              period: item.period,
              periodUnit: item.periodUnit,
              done: false
            });
          });
          this.$router.go({ path: this.$router.path });
        });
    },
    convert_timestamp(unixTimestamp) {
      var date = unixTimestamp.toDate();
      var year = date.getFullYear();
      var month = date.getMonth() + 1;
      var day = date.getDate();
      if (month < 10) month = "0" + month;
      if (day < 10) day = "0" + day;
      var formattedTime = [year, month, day].join("-");
      return formattedTime;
    },
    badge_today(unixTimestamp) {
      var date = unixTimestamp.toDate();
      var today = new Date();
      if (date.toDateString() === today.toDateString()) {
        return true;
      } else {
        return false;
      }
    },
    badge_overdue(unixTimestamp) {
      var date = unixTimestamp.toDate();
      var today = new Date();
      date = new Date(date.toDateString());
      today = new Date(today.toDateString());
      if (today > date) {
        return true;
      } else {
        return false;
      }
    },
    handleOk(bvModalEvt, eventName) {
      bvModalEvt.preventDefault();
      // var bathInfo = new Info(
      //   eventName,
      //   this.period_form.period,
      //   this.period_form.selected
      // );
    }
  },
  computed: {
    uid() {
      return this.$store.state.uid;
    },
    pet() {
      return this.$store.state.pet;
    },
    pet_doc_id() {
      return this.$store.state.pet_doc_id;
    },
    pet_info() {
      return this.$store.state.pet_info;
    },
    show() {
      if (this.$store.state.pet === null) {
        return false;
      } else {
        return true;
      }
    }
  }
};
</script>

<style scoped>
.container {
  margin-top: 20px;
  margin-bottom: 20px;
  min-height: 600px;
}

.overview .title {
  font-size: 24px;
}

.overview .date {
  color: gray;
}

#card-create-pet > .card-body {
  padding: 0%;
}
</style>
