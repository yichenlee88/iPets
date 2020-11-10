<template>
  <b-container id="tunit-app" class="p-4">
    <div>
      <b-jumbotron class="cat_jumbotron" lead="The cat is so awesome!">
        <template v-slot:header>Cats Bang Bang Bang</template>
        <b-button variant="primary" href="#">Love</b-button>
      </b-jumbotron>
    </div>
    <b-form-file
      @change="handleFileUpload"
      accept="image/jpeg, image/png"
      placeholder="Choose..."
      multiple
    ></b-form-file>
    <div v-if="processing" class="d-flex justify-content-center m-5">
      <b-spinner label="Loading..."></b-spinner>
    </div>
    <div v-else class="p-2">
      <b-row>
        <b-col v-show="imgA"
          ><b-img :src="`data:image/png;base64,${imgA}`" fluid></b-img
        ></b-col>
        <b-col v-show="imgB"
          ><b-img :src="`data:image/png;base64,${imgB}`" fluid></b-img
        ></b-col>
      </b-row>
      <b-row>
        <b-col v-show="imgC"
          ><b-img :src="`data:image/png;base64,${imgC}`" fluid></b-img
        ></b-col>
        <b-col v-show="imgD"
          ><b-img :src="`data:image/png;base64,${imgD}`" fluid></b-img
        ></b-col>
      </b-row>
    </div>
  </b-container>
</template>

<script>
import axios from "axios";
export default {
  name: "tunit",
  data() {
    return {
      processing: false,
      imgA: null,
      imgB: null,
      imgC: null,
      imgD: null
    };
  },
  methods: {
    handleFileUpload(e) {
      this.processing = true;
      var file1 = e.target.files[0];
      var file2 = e.target.files[1];
      var reader1 = new FileReader();
      var reader2 = new FileReader();
      reader1.onload = e => {
        this.imgA = e.target.result;
      };
      reader2.onload = e => {
        this.imgB = e.target.result;
      };
      reader1.readAsDataURL(file1);
      reader2.readAsDataURL(file2);

      let formData = new FormData();
      formData.append("photo", file1);
      formData.append("photo", file2);

      axios
        .post("/tunit", formData, {
          baseURL: "http://140.124.44.8:3000",
          timeout: 10000,
          headers: {
            "Content-Type": "multipart/form-data"
          }
        })
        .then(response => {
          this.imgA = response.data["A"];
          this.imgB = response.data["B"];
          this.imgC = response.data["C"];
          this.imgD = response.data["D"];
          this.processing = false;
        });
    }
  }
};
</script>
<style>
#tunit-app {
  text-align: center;
}

.cat_jumbotron {
  background: url("../assets/tunit_bkg.png") no-repeat center center;
  background-size: 100% 100%;
}
</style>
