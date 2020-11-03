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
              <b-input v-model="albumName" placeholder="請輸入相簿名稱，並選取一張照片"></b-input>
            </b-col>
            <b-col>
              <b-input-group class="my-3 px-2 py-2 bg-white shadow-sm">
                <input
                  @change="handleFileUpload"
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
                @click="createAlbum();$bvModal.hide('my-modal')"
              >確認新增</b-button>
            </b-col>
          </b-form>
        </b-modal>
      </div>
    </div>

    <b-row lg="4" style="margin:20px 20px">
      <b-col cols="6" md="4" v-for="(item, index) in album" :key="index">
        <a :href="'#/albumView/' + item.name">
          <b-card
            v-if="album"
            overlay
            style="margin:20px"
            text-variant="white"
            :img-src="url[index]"
            :title="item.name"
          ></b-card>
        </a>
      </b-col>
    </b-row>
    <!-- if there is no any album -->
    <div v-if=" !album ">
      <b-img class="banner_png center" src="../static/img/gray.png" style="width:468px;"></b-img>
    </div>
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
      imageData: null,
      file: [],
      album: [],
      url: []
    };
  },
  mounted() {
    let album = this.album;
    let imageUrl = this.url;
    fStore.collection("user").get();
    fStore
      .collection("user")
      .get()
      .then(querySnapshot => {
        querySnapshot.forEach(doc => {
          this.user.push(doc.data());
          console.log(doc.id, doc.data());
        });
      });
    var storageRef = firebase.storage().ref("user1/");
    let folderName = [];
    storageRef
      .listAll()
      .then(function(res) {
        res.prefixes.forEach(function(folderRef) {
          folderName.push(folderRef.name);
          album.push(folderRef);
          console.log(folderRef, folderRef.name, folderName);
          var imageRef = firebase.storage().ref("user1/" + folderRef.name);
          imageRef.listAll().then(function(res) {
            res.items.forEach(function(itemRef) {
              itemRef.getDownloadURL().then(function(url) {
                imageUrl.push(url);
              });
            });
          });
        });
      })
      .catch(function(error) {
        console.log(error);
      });
  },
  methods: {
    handleFileUpload(e) {
      this.imageData = e.target.files[0];
    },
    createAlbum() {
      var storageRef = firebase
        .storage()
        .ref("user1/" + this.albumName + "/" + this.imageData.name);
      storageRef.put(this.imageData).then(function(snapshot) {
        console.log("Uploaded files!");
      });
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

.albumTitle {
  background-color: #ffd382;
}

.albumTitle:hover {
  background-color: rgb(248, 168, 20);
}

#uploader {
  width: 50%;
  margin-bottom: 10px;
}
</style>
