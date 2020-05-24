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
        { value: "柴犬", text: "柴犬" },
        { value: "黃金獵犬", text: "黃金獵犬" }
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
