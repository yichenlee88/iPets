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
        <b-form @submit="onSubmit">
          <!-- petImage -->
          <v-row no-gutters justify="center" align="center">
            <v-col>
              <input
                class="inputPic"
                type="file"
                @change="handleFileUpload"
                accept="image/jpeg, image/png"
                placeholder="選擇寵物相片"
                name="petImage"
                :v-model="petImage"
              >
            </v-col>
          </v-row>
          <!-- petName -->
          <b-form-input
            class="InputClass center"
            name="petName"
            v-model="petName"
            placeholder="寵物名稱"
            required
          ></b-form-input>
          <!-- petGender -->
          <b-form-select
            class="InputClass center"
            name="petGender"
            v-model="petGender"
            :options="petGenderOptions"
          ></b-form-select>
          <!-- petBirth -->
          <date-picker
            class="InputClass center"
            v-model="petBirth"
            name="petBirth"
            type="date"
            placeholder="寵物生日"
          ></date-picker>
          <!-- breed -->
          <b-form-group id="group-pet-breed" label-for="select-pet-breed">
            <b-form-select
              class="InputClass center"
              id="select-pet-breed"
              v-model="breed"
              name="breed"
              :options="breed_options"
              placeholder="寵物品種"
            ></b-form-select>
          </b-form-group>
          <!-- petHobby -->
          <b-form-textarea
            class="TextareaClass"
            name="petHobby"
            id="petHobby"
            v-model="petHobby"
            placeholder="寵物喜好"
            maxlength
          ></b-form-textarea>
          <!-- petNote -->
          <b-form-textarea
            class="TextareaClass"
            name="petNote"
            id="petNote"
            v-model="petNote"
            placeholder="備註"
            maxlength
          ></b-form-textarea>
          <div class="d-flex flex-row-reverse">
            <b-button type="submit" variant="dark">建立</b-button>
          </div>
        </b-form>
      </b-card-body>
    </b-card>
    <!-- End -- 新增寵物 -->

    <!-- Start -- 寵物簡介 -->
    <b-card-group class="PetProfile" deck v-if="show">
      <!-- Start -- 修改寵物資訊 -->
      <b-card :img-src="pet.profile_picture" class="mb-3" style="max-width:300px;border:0" img-top>
        <b-card-text>
          <b-button
            class="btn btn-block mb-3"
            href="#"
            variant="primary"
            v-b-modal.modal-prevent-closing
          >編輯寵物資訊</b-button>
          <b-modal
            title="編輯寵物資訊"
            size="xl"
            id="modal-prevent-closing"
            ref="modal"
            @ok="edit_pet_ok"
          >
            <form ref="form" @submit.stop.prevent="handleSubmit">
              <div class="border-bottom">
                <b-row>
                  <b-col>
                    <!-- 寵物照片 -->
                    <b-img :src="pet.profile_picture" style="max-height:300px" class="center"></b-img>
                    <!-- 開啟寵物照片-->
                    <input class="hidden" id="file1" @change="handleFileUpload" type="file">
                    <label
                      name="petImage"
                      class="center btn btn-block mb-3"
                      for="file1"
                      style="max-width:300px;margin-top:10px"
                      accept="image/jpeg, image/png"
                      placeholder="選擇寵物相片"
                      :v-model="petImage"
                    >
                      <b-icon icon="image"></b-icon>上傳照片
                    </label>
                  </b-col>
                  <b-col>
                    <!-- 修改寵物姓名 -->
                    <b-form-group label-cols-sm="2" label="姓名：" label-for="petName">
                      <b-form-input
                        class="modifyInputClass"
                        v-model.trim="pet.petName"
                        id="petName"
                        style="margin-top:-5px"
                      ></b-form-input>
                    </b-form-group>
                    <!-- 修改寵物性別 -->
                    <b-form-group label-cols-sm="2" label="性別：" label-for="petGender">
                      <b-form-select
                        class="modifyInputClass"
                        v-model="pet.petGender"
                        :options="petGenderOptions"
                        id="petGender"
                        style="margin-top:-5px"
                      ></b-form-select>
                    </b-form-group>
                    <!-- 修改寵物生日 -->
                    <b-form-group label-cols-sm="2" label="生日：" label-for="petBirth">
                      <date-picker
                        v-model="edit_profile_birth"
                        name="petBirth"
                        type="date"
                        style="margin-top:-5px;"
                      ></date-picker>
                    </b-form-group>
                    <!-- 修改寵物喜好 -->
                    <b-form-group label-cols-sm="2" label="喜好：" label-for="petHobby">
                      <b-form-textarea
                        class="modifyInputClass"
                        v-model.trim="pet.petHobby"
                        id="petHobby"
                        style="margin-top:-10px"
                      ></b-form-textarea>
                    </b-form-group>
                    <!-- 修改寵物備註 -->
                    <b-form-group label-cols-sm="2" label="備註：" label-for="petNote">
                      <b-form-textarea
                        class="modifyInputClass"
                        v-model.trim="pet.petNote"
                        id="petNote"
                        style="margin-top:-10px"
                      ></b-form-textarea>
                    </b-form-group>
                  </b-col>
                </b-row>
              </div>
            </form>
          </b-modal>
        </b-card-text>
      </b-card>
      <!-- End -- 修改寵物資訊 -->

      <!-- Start -- 寵物資訊 -->
      <b-card style="max-height:365px">
        <b-card-body :title="pet.petName">
          <b-card-text>性別：{{ pet.petGender }}</b-card-text>
          <b-card-text>生日：{{ pet.petBirth }}</b-card-text>
          <b-card-text>年齡：{{ calculateAge(pet.petBirth) }}</b-card-text>
          <b-card-text>品種：{{ pet.breed }}</b-card-text>
          <b-card-text>喜好：{{ pet.petHobby }}</b-card-text>
          <b-card-text>備註：{{ pet.petNote }}</b-card-text>
          <b-card-text class="small text-muted">最後更新 {{ convert_timestamp(pet.timestamp) }}</b-card-text>
        </b-card-body>
      </b-card>
      <!-- End -- 寵物資訊 -->
    </b-card-group>
    <!-- End -- 寵物簡介 -->

    <!-- Start -- 當月事件 -->
    <b-list-group>
      <b-list-group-item
        v-for="(event, idx) in month_calendar"
        :key="idx"
        href="#"
        disabled
        class="flex-column align-items-start"
      >
        <div class="d-flex w-100 justify-content-between">
          <h5 class="mb-1">{{ event.name }}</h5>
          <small>3 days ago</small>
        </div>
        <small>{{ event.start }} ~ {{ event.end }}</small>
      </b-list-group-item>
    </b-list-group>
    <!-- End -- 當月事件 -->
  </b-container>
</template>

<script>
// Import required dependencies
import "bootstrap/dist/css/bootstrap.css";

// Import date picker css
import "pc-bootstrap4-datetimepicker/build/css/bootstrap-datetimepicker.css";
import firebase from "firebase";
import { db } from "../db";
const fStore = db.firestore();

export default {
  name: "petProfile",
  data() {
    return {
      pet_profile_picture: null,
      date: new Date(),
      petImage: null,
      getUrl: [],
      url: "",
      age: "",
      imageUrl: "",
      petName: "",
      file: [],
      petGender: "null",
      petGenderOptions: [
        { value: "null", text: "寵物性別" },
        { value: "不透漏", text: "不透漏" },
        { value: "男孩", text: "男孩" },
        { value: "女孩", text: "女孩" }
      ],
      petBirth: "",
      petHobby: "",
      petNote: "",
      breed: "null",
      breed_options: [
        { value: null, text: "選擇品種", disabled: true },
        { value: "台灣土狗", text: "台灣土狗" },
        { value: "柴犬", text: "柴犬" },
        { value: "黃金獵犬", text: "黃金獵犬" },
        { value: "拉布拉多", text: "拉布拉多" },
        { value: "哈士奇", text: "哈士奇" },
        { value: "藏獒", text: "藏獒" },
        { value: "貴賓犬", text: "貴賓犬" },
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
      ]
    };
  },
  methods: {
    edit_pet_ok(e) {
      e.preventDefault();
      fStore
        .collection("users")
        .doc(this.uid)
        .collection("pets")
        .doc(this.pet_doc_id)
        .update({
          petName: this.pet.petName,
          petGender: this.pet.petGender,
          petBirth: this.pet.petBirth,
          petHobby: this.pet.petHobby,
          petNote: this.pet.petNote,
          timestamp: new Date()
        });
      this.$nextTick(() => {
        this.$bvModal.hide("modal-prevent-closing");
      });
      if (this.petImage) {
        this.createAlbum();
        this.getProfilePicture();
      }
      setTimeout(function() {
        location.reload();
      }, 2000);
    },
    handleFileUpload(e) {
      this.petImage = e.target.files[0];
      console.log(this.petImage);
    },
    createAlbum() {
      var storageRef = firebase
        .storage()
        .ref(this.uid + "/" + this.pet.petName);
      storageRef.put(this.petImage).then(function(snapshot) {
        console.log("Uploaded files!");
      });
    },
    getProfilePicture() {
      let uid = firebase.auth().currentUser.uid;
      var storageRef = firebase.storage().ref(uid + "/" + this.pet.petName);
      console.log(storageRef);
      storageRef
        .listAll()
        .then(res => {
          console.log(res);
          res.items.forEach(itemRef => {
            console.log(itemRef);
            itemRef.getDownloadURL().then(url => {
              this.getUrl.push(url);
              console.log(url);
            });
          });
        })
        .catch(function(error) {
          console.log(error);
        });
    },
    async save_pet_profile(file) {
      // reference:https://codelabs.developers.google.com/codelabs/firebase-web/#9
      await fStore
        .collection("users")
        .doc(this.uid)
        .collection("pets")
        .add({
          petName: this.petName,
          petGender: this.petGender,
          petBirth:
            this.petBirth.getUTCFullYear() +
            "-" +
            (this.petBirth.getMonth() + 1) +
            "-" +
            this.petBirth.getDate(),
          petBirth_year: this.petBirth.getUTCFullYear(),
          petBirth_month: this.petBirth.getMonth() + 1,
          petBirth_date: this.petBirth.getDate(),
          breed: this.breed,
          petHobby: this.petHobby,
          petNote: this.petNote,
          uid: this.uid,
          timestamp: new Date(),
          profile_picture: ""
        })
        .then(function(ref) {
          return firebase
            .storage()
            .ref(firebase.auth().currentUser.uid + "/" + ref.id)
            .put(file)
            .then(function(fileSnapshot) {
              return fileSnapshot.ref.getDownloadURL().then(function(url) {
                return ref.update({
                  profile_picture: url
                });
              });
            });
        })
        .catch(function(error) {
          console.error(
            "There was an error uploading a file to Cloud Storage:",
            error
          );
        });
      this.$router.go({ path: this.$router.path });
    },
    onSubmit(e) {
      e.preventDefault();
      this.save_pet_profile(this.petImage);
    },
    convert_timestamp(unixTimestamp) {
      var date = unixTimestamp.toDate();
      var year = date.getFullYear();
      var month = date.getMonth() + 1;
      var day = date.getDate();
      var hour = date.getHours();
      var min = date.getMinutes();
      var sec = date.getSeconds();
      if (month < 10) month = "0" + month;
      if (day < 10) day = "0" + day;
      if (hour < 10) hour = "0" + hour;
      if (min < 10) min = "0" + min;
      if (sec < 10) sec = "0" + sec;
      var formattedTime =
        [year, month, day].join("-") + " " + hour + ":" + min + ":" + sec;
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
    },
    calculateAge(birth) {
      var ageDiff = new Date(Date.now() - new Date(birth).getTime());
      return Math.abs(ageDiff.getUTCFullYear() - 1970);
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
    month_calendar() {
      return this.$store.state.month_calendar;
    },
    show() {
      if (this.$store.state.pet === null) {
        return false;
      } else {
        return true;
      }
    },
    edit_profile_birth: {
      // support string format, not Date object
      get() {
        return new Date(this.pet.petBirth);
      },
      set(newDate) {
        newDate.setDate(newDate.getDate() + 1);
        this.pet.petBirth = newDate.toISOString().slice(0, 10);
      }
    }
  }
};
</script>

<style scoped>
.PetProfile {
  padding-top: 20px;
}

.hidden {
  visibility: hidden;
}

.cloud {
  position: absolute;
  height: 24px;
  width: auto;
  top: 14%;
  right: 50px;
  margin-top: 3px;
  z-index: 1;
  cursor: pointer;
}

.modifyInputClass {
  max-width: 80%;
  border-radius: 20px;
}

.center {
  display: block;
  margin-left: auto;
  margin-right: auto;
}

.inputPic {
  max-width: auto;
  height: 48px;
  margin-left: 5px;
}

.InputClass {
  height: 48px;
  max-width: auto;
  border-radius: 40px;
  margin-bottom: 20px;
}

.TextareaClass {
  height: 100px;
  max-width: auto;
  border-radius: 16px;
  margin-bottom: 20px;
}

.mx-datepicker {
  width: auto;
}

.mx-datepicker >>> .mx-icon-calendar,
.mx-datepicker >>> .mx-icon-clear {
  margin-right: 8px;
}
.mx-datepicker >>> .mx-input {
  height: 48px;
  max-width: auto;
  border-radius: 40px;
  margin-bottom: 20px;
  display: block;
  margin-left: auto;
  margin-right: auto;
}

.border-bottom {
  border-bottom: 1px solid #e1e4e8 !important;
}
.TimerTitle {
  padding-top: 10px;
  padding-bottom: 10px;
}

.btn {
  color: #24292e;
  background-color: #fafbfc;
  border-color: rgba(27, 31, 35, 0.15);
}
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
