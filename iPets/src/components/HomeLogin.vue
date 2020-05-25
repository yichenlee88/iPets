<template>
  <b-container>
    <!-- Start -- 新增寵物 -->
    <b-card id="card-create-pet">
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
            label="名子"
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
  </b-container>
</template>

<script>
// Import required dependencies
import "bootstrap/dist/css/bootstrap.css";

// Import this component
import datePicker from "vue-bootstrap-datetimepicker";

// Import date picker css
import "pc-bootstrap4-datetimepicker/build/css/bootstrap-datetimepicker.css";
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
        gender: null
      }
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
          timestamp: new Date()
        })
        .then(() => {
          // var infoRef = docRef.collection("info");
          console.log("asdsad");
          // var infoList = [
          //   new Info("洗澡", 0, "day"),
          //   new Info("除蟲", 2, "week"),
          //   new Info("指甲", 1, "month")
          // ];

          // infoList.forEach(function(item) {
          //   infoRef.doc(item.eventName).set({
          //     next_time: item.getNext(),
          //     period: item.period,
          //     periodUnit: item.periodUnit,
          //     done: false
          //   });
          // });
          this.$router.go({ path: this.$router.path });
        });
    }
  },
  computed: {
    uid() {
      return this.$store.state.uid;
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

.arrow {
  border: solid black;
  border-width: 0 3px 3px 0;
  display: inline-block;
  padding: 3px;
}

.right {
  transform: rotate(-45deg);
  -webkit-transform: rotate(-45deg);
}

.round {
  position: relative;
}

.round label {
  background-color: #fff;
  border: 1px solid #ccc;
  border-radius: 50%;
  cursor: pointer;
  height: 28px;
  left: 0;
  position: absolute;
  top: 5px;
  width: 28px;
}

.round h3 {
  margin-right: auto;
}

.round label:after {
  border: 2px solid #fff;
  border-top: none;
  border-right: none;
  content: "";
  height: 6px;
  left: 7px;
  opacity: 0;
  position: absolute;
  top: 8px;
  transform: rotate(-45deg);
  width: 12px;
}

.round input[type="checkbox"] {
  visibility: hidden;
}

.round input[type="checkbox"]:checked + label {
  background-color: #66bb6a;
  border-color: #66bb6a;
}

.round input[type="checkbox"]:checked + label:after {
  opacity: 1;
}

#card-create-pet > .card-body {
  padding: 0%;
}
</style>
