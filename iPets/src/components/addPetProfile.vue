<template>
  <b-container>
    <b-form @submit="onSubmit">
      <!-- petImage -->
      <v-row no-gutters justify="center" align="center">
        <v-col>
          <input
            class="input"
            style="margin-right: auto; margin-left: auto"
            type="file"
            @change="handleFileUpload"
            accept="image/jpeg, image/png"
            placeholder="選擇寵物相片"
            name="petImage"
            :v-model="petImage"
          />
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
      <b-form-input
        class="InputClass center"
        type="date"
        name="petBirth"
        v-model="petBirth"
        placeholder="寵物生日"
      ></b-form-input>
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
        class="TextareaClass center"
        name="petHobby"
        id="petHobby"
        v-model="petHobby"
        placeholder="寵物喜好"
        maxlength
      ></b-form-textarea>
      <!-- petNote -->
      <b-form-textarea
        class="TextareaClass center"
        name="petNote"
        id="petNote"
        v-model="petNote"
        placeholder="備註"
        maxlength
      ></b-form-textarea>
      <div class="d-flex flex-row-reverse">
        <b-button type="submit" class="ButtonClass center">建立</b-button>
      </div>
    </b-form>
  </b-container>
</template>

<script>
import "bootstrap/dist/css/bootstrap.css";

// Import date picker css
import "pc-bootstrap4-datetimepicker/build/css/bootstrap-datetimepicker.css";
import { db } from "../db";
import firebase from "firebase";
const fStore = db.firestore();

export default {
  name: "addPetProfile",
  data() {
    return {
      date: new Date(),
      petImage: null,
      petName: "",
      file: [],
      petGender: "null",
      petGenderOptions: [
        { value: "null", text: "寵物性別" },
        { value: "不透漏", text: "不透漏" },
        { value: "男孩", text: "男孩" },
        { value: "女孩", text: "女孩" }
      ],
      petBirth: new Date().toISOString().slice(0, 10),
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
    handleFileUpload(e) {
      this.petImage = e.target.files[0];
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
          petBirth: this.petBirth,
          breed: this.breed,
          petHobby: this.petHobby,
          petNote: this.petNote,
          timestamp: new Date(),
          profile_picture: "",
          // useless
          petBirth_year: 999999,
          petBirth_month: 999999,
          petBirth_date: 999999,
          uid: this.uid
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
        .then(() => {
          alert("新增成功!");
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
    }
  }
};
</script>

<style scoped>
.center {
  display: block;
  margin-left: auto;
  margin-right: auto;
}

.InputClass {
  height: 48px;
  max-width: 70%;
  border-radius: 40px;
  margin-bottom: 20px;
}

.input {
  width: 70%;
  height: 48px;
  display: block;
  margin-left: auto;
  margin-right: auto;
}

.TextareaClass {
  height: 100px;
  max-width: 70%;
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

.ButtonClass {
  height: 48px;
  width: 70%;
  border-radius: 40px;
  background: -webkit-linear-gradient(
    left,
    rgb(148, 115, 221) 0%,
    rgb(26, 201, 228) 100%
  );
}
</style>
