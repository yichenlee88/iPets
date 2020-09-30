<template>
  <b-container>
    <div class="border-bottom">
      <div class="row">
        <h1 class="high">相簿</h1>
        <b-button
          class="ml-auto mt-auto"
          style="height: 45px"
          variant="outline-primary"
          v-b-modal="'my-modal'"
          >+新增相簿</b-button
        >
        <b-modal size="lg" id="my-modal">
          <b-button class="btn btn-block mb-3" variant="info">
            Brose
          </b-button>
        </b-modal>
      </div>
    </div>
    <b-img
      class="banner_png center"
      src="../static/img/gray.png"
      style="width:468px;"
    ></b-img>
  </b-container>
</template>

<script>
import firebase from "firebase";
export default {
  name: "Album",
  data() {
    return {
      caption: "",
      img1: "",
      imageData: null
    };
  },
  methods: {
    create() {
      const post = {
        photo: this.img1,
        caption: this.caption
      };
      firebase
        .database()
        .ref("PhotoGallery")
        .push(post)
        .then(response => {
          console.log(response);
        })
        .catch(err => {
          console.log(err);
        });
    },
    click1() {
      this.$refs.input1.click();
    },
    previewImage(event) {
      this.uploadValue = 0;
      this.img1 = null;
      this.imageData = event.target.files[0];
      this.onUpload();
    },
    onUpload() {
      this.img1 = null;
      const storageRef = firebase
        .storage()
        .ref(`${this.imageData.name}`)
        .put(this.imageData);
      storageRef.on(
        "state_changed",
        snapshot => {
          this.uploadValue =
            (snapshot.bytesTransferred / snapshot.totalBytes) * 100;
        },
        error => {
          console.log(error.message);
        },
        () => {
          this.uploadValue = 100;
          storageRef.snapshot.ref.getDownloadURL().then(url => {
            this.img1 = url;
            console.log(this.img1);
          });
        }
      );
    }
  }
};
</script>

<style scoped>
.high {
  margin-top: 20px;
}

.center {
  display: block;
  margin-left: auto;
  margin-right: auto;
  margin-top: 20px;
}

.border-bottom {
  height: 90px;
  border-bottom: 2px solid #888888 !important;
}
</style>
