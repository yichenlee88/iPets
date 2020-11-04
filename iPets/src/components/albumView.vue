<template>
  <b-container>
    <div class="border-bottom">
      <div class="row">
        <h1 class="high">{{ name }}</h1>
        <b-button
          class="ml-auto high"
          style="height: 50px;"
          variant="outline-primary"
          v-b-modal="'my-modal'"
        >+新增相片</b-button>

        <b-modal size="lg" id="my-modal" hide-footer title="新增相片">
          <b-form>
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
                @click="createPhoto();$bvModal.hide('my-modal')"
              >確認新增</b-button>
            </b-col>
          </b-form>
        </b-modal>
      </div>
    </div>
    <b-row lg="6" style="margin:20px 20px">
      <b-col cols="6" md="4" v-for="(item, index) in url" :key="index">
        <span @click="$bvModal.show(`${index}`);">
          <b-card overlay :img-src="item"></b-card>
        </span>
        <b-modal size="lg" hide-footer :id="`${index}`">
          <template #modal-title>
            <b-icon
              icon="trash"
              variant="secondary"
              @click="deleteImage(index)"
              style="margin-top:20px"
            ></b-icon>
          </template>
          <img :src="item" style="max-width: 100%;">
        </b-modal>
      </b-col>
    </b-row>
  </b-container>
</template>

<script>
import firebase from "firebase";
import { db } from "../db";
const fStore = db.firestore();

export default {
  name: "albumView",
  data() {
    return {
      name: this.$route.params.name,
      file: [],
      album: [],
      url: []
    };
  },
  computed: {
    uid() {
      return this.$store.state.uid;
    }
  },
  mounted() {
    let name = this.$route.params.name;
    this.name = name;
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
    var storageRef = firebase.storage().ref(this.uid + "/" + this.name);
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
    handleFileUpload(e) {
      this.imageData = e.target.files[0];
    },
    createPhoto() {
      var storageRef = firebase
        .storage()
        .ref(this.uid + "/" + this.name + "/" + this.imageData.name);
      storageRef.put(this.imageData).then(function(snapshot) {
        console.log("Uploaded files!");
        this.$router.go({ path: this.$router.path });
      });
    },
    deleteImage(index) {
      let target = this.album[index];
      if (confirm(`是否刪除 ${target.name} ?`)) {
        var storageRef = firebase
          .storage()
          .ref(this.uid + "/" + this.name + "/" + target.name);
        storageRef
          .delete()
          .then(function() {
            console.log("刪除成功!");
          })
          .catch(function(error) {
            console.log(error);
          });
      }
      this.$router.go({ path: this.$router.path });
    }
  }
};
</script>

<style>
.border-bottom {
  height: 90px;
  border-bottom: 2px solid #888888 !important;
}
.high {
  margin-top: 20px;
}
</style>
