<template>
  <b-container>
    <b-row lg="6" style="margin:20px 20px">
      <b-col cols="6" md="4" v-for="(item, index) in url" :key="index">
        <b-card overlay :img-src="item"></b-card>
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
    var storageRef = firebase.storage().ref("user1/" + this.name);
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
  }
};
</script>

<style>
</style>
