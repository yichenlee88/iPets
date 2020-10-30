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
        >+新增相簿</b-button>

        <b-modal size="lg" id="my-modal" hide-footer title="新增相簿">
          <b-form>
            <b-col>
              <b-input v-model="albumName" placeholder="請輸入相簿名稱"></b-input>
            </b-col>
            <b-col>
              <b-input-group class="my-3 px-2 py-2 bg-white shadow-sm">
                <input
                  multiple
                  id="fileButton"
                  type="file"
                  accept="image/jpeg, image/png"
                  class="form-control border-0"
                >
                <div class="input-group-append">
                  <label for="fileButton" class="btn btn-light m-0 px-4">
                    <i class="fa fa-cloud-upload mr-2 text-muted"></i>
                    <small class="text-uppercase font-weight-bold text-muted">Choose file</small>
                  </label>
                </div>
              </b-input-group>
            </b-col>
            <b-col>
              <b-button
                class="ml-auto mt-auto"
                style="height: 45px;float:right"
                variant="outline-primary"
                @click="createAlbum"
              >確認新增</b-button>
            </b-col>
          </b-form>
        </b-modal>
      </div>
    </div>
    <b-row cols-md="3" style="margin:20px 20px">
      <div v-for="(item, index) in url" :key="index">
        <b-card style="margin:20px">
          <b-col>
            <b-col>
              <img style="max-width: 100%" :src="item">
            </b-col>
          </b-col>
        </b-card>
        <!-- if there is no any album -->
        <div v-if="!album">
          <b-img class="banner_png center" src="../static/img/gray.png" style="width:468px;"></b-img>
        </div>
      </div>
    </b-row>
  </b-container>
</template>

<script>
import firebase from "firebase";
import { db } from "../db";
const fStore = db.firestore();

export default {
  name: "Album",
  data() {
    return {
      albumName: "",
      file: [],
      album: [],
      url: []
    };
  },
  mounted() {
    let album = this.album;
    let imageUrl = this.url;
    fStore
      .collection("user")
      .get()
      .then(querySnapshot => {
        querySnapshot.forEach(doc => {
          this.user.push(doc.data());
          console.log(doc.id, doc.data());
        });
      });
    var storageRef = firebase.storage().ref("user1/albumTest/");
    storageRef
      .listAll()
      .then(function(res) {
        res.prefixes.forEach(function(folderRef) {
          alert(folderRef);
          console.log(folderRef);
        });
        res.items.forEach(function(itemRef) {
          album.push(itemRef);
          itemRef.getDownloadURL().then(function(url) {
            imageUrl.push(url);
          });
        });
      })
      .catch(function(error) {
        console.log(error);
      });
  },
  methods: {
    createAlbum() {
      var fileButton = document.getElementById("fileButton");
      fileButton.addEventListener("change", function(e) {
        var file = e.target.files[0];
        var storageRef = firebase
          .storage()
          .ref("user1/" + "albumTest/" + this.albumName);
        storageRef.put(file).then(function(snapshot) {
          console.log("Uploaded files!");
          console.log(e);
          console.log(file, this.albumName);
        });
      });
      console.log(this.e);
      console.log(this.file, this.albumName);
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
