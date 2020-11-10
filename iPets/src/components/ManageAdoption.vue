<template>
  <b-container style="margin-top:20px">
    <div class="center">
      <b-container>
        <b-row class="justify-content-md-center" style="margin:20px 0">
          <b-col cols="10" style="margin:0 auto">
            <b-button
              block
              variant="outline-secondary"
              v-on:click="$bvModal.show(`createAdoption`); cleanData();"
            >
              <b-icon icon="plus"></b-icon>
            </b-button>
            <div v-for="(item, index) in comments" :key="index">
              <b-row class="text-left" style="margin-top:20px">
                <b-col cols="8">{{index + 1}}.{{item.name}}</b-col>
                <b-col>
                  <b-button pill v-on:click="$bvModal.show(`${index}`)">
                    <b-icon icon="eye"></b-icon>
                  </b-button>
                  <b-button
                    pill
                    v-on:click="cleanData(); $bvModal.show(`${item.name}`);"
                    style="background: #6495ED;border: #6495ED"
                  >
                    <b-icon icon="pencil"></b-icon>
                  </b-button>
                  <b-button
                    pill
                    v-on:click="deleteAdoption(index)"
                    style="background: #CD5C5C;border: #CD5C5C"
                  >
                    <b-icon icon="x"></b-icon>
                  </b-button>
                </b-col>
              </b-row>
              <b-modal hide-footer size="lg" :id="`${index}`" :title="item.name">
                <div class="center" style="margin-top:20px">
                  <img :src="`${item.src}`" style="max-width: 60%;">
                </div>
                <b-container style="padding:20px 120px;">
                  <p class="content">
                    簡介：
                    <br>
                    {{item.introduction}}
                  </p>
                  <p>地址：{{item.address}}</p>
                  <p>電話：{{item.phone}}</p>
                  <p>介紹：{{item.content}}</p>
                  <p>座標：{{item.center.lat}}/{{item.center.lng}}</p>
                </b-container>
                <b-button block v-on:click="$bvModal.hide(`${index}`)">關閉視窗</b-button>
              </b-modal>
              <b-modal
                hide-footer
                size="lg"
                :id="`${item.name}`"
                title="編輯領養機構"
                @hidden="cleanData();"
              >
                <b-container>
                  <b-row class="justify-content-md-center" style="margin:20px 0">
                    <b-col cols="10" style="margin:0 auto">
                      <b-card bg-variant="light">
                        <b-row class="my-1">
                          <b-col sm="2">
                            <label for="adoption_id">id:</label>
                          </b-col>
                          <b-col sm="10">{{item.id}}</b-col>
                        </b-row>
                        <b-row class="my-1">
                          <b-col sm="2">
                            <label for="adoption_name">標題:</label>
                          </b-col>
                          <b-col sm="10">
                            <b-form-input id="adoption_name" type="text" v-model.trim="item.name"></b-form-input>
                          </b-col>
                        </b-row>
                        <b-row class="my-1">
                          <b-col sm="2">
                            <label for="adoption_src">圖片位址:</label>
                          </b-col>
                          <b-col sm="10">
                            <b-form-input
                              id="adoption_src"
                              type="url"
                              placeholder="Input image url"
                              v-model.trim="item.src"
                            ></b-form-input>
                          </b-col>
                        </b-row>
                        <b-row class="my-1">
                          <b-col sm="2">
                            <label for="adoption_introduction">簡介:</label>
                          </b-col>
                          <b-col sm="10">
                            <b-form-input
                              id="adoption_introduction"
                              type="text"
                              v-model.trim="item.introduction"
                            ></b-form-input>
                          </b-col>
                        </b-row>
                        <b-row class="my-1">
                          <b-col sm="2">
                            <label for="adoption_address">地址:</label>
                          </b-col>
                          <b-col sm="10">
                            <b-form-input
                              id="adoption_address"
                              type="text"
                              v-model.trim="item.address"
                            ></b-form-input>
                          </b-col>
                        </b-row>
                        <b-row class="my-1">
                          <b-col sm="2">
                            <label for="adoption_phone">電話:</label>
                          </b-col>
                          <b-col sm="10">
                            <b-form-input id="adoption_phone" type="text" v-model.trim="item.phone"></b-form-input>
                          </b-col>
                        </b-row>
                        <b-row class="my-1">
                          <b-col sm="2">
                            <label for="adoption_content">介紹:</label>
                          </b-col>
                          <b-col sm="10">
                            <b-form-textarea
                              id="adoption_content"
                              placeholder="Enter content"
                              rows="2"
                              v-model.trim="item.content"
                            ></b-form-textarea>
                          </b-col>
                        </b-row>
                        <b-row class="my-1">
                          <b-col sm="2">
                            <label for="adoption_center">座標:</label>
                          </b-col>
                          <b-col sm="5">
                            <b-form-input
                              id="adoption_lat"
                              type="text"
                              v-model.trim="item.center.lat"
                            ></b-form-input>
                          </b-col>
                          <b-col sm="5">
                            <b-form-input
                              id="adoption_lng"
                              type="text"
                              v-model.trim="item.center.lng"
                            ></b-form-input>
                          </b-col>
                        </b-row>
                        <b-button
                          class="right"
                          v-on:click="updateAdoption(index); $bvModal.hide(`${item.name}`)"
                        >確認儲存文章</b-button>
                      </b-card>
                    </b-col>
                  </b-row>
                </b-container>
              </b-modal>
            </div>
          </b-col>
        </b-row>
      </b-container>
      <b-modal hide-footer size="lg" title="新增領養機構" id="createAdoption">
        <b-container>
          <b-row class="justify-content-md-center" style="margin:20px 0">
            <b-col cols="10" style="margin:0 auto">
              <b-card bg-variant="light">
                <b-row class="my-1">
                  <b-col sm="2">
                    <label for="adoption_name">機構名稱:</label>
                  </b-col>
                  <b-col sm="10">
                    <b-form-input
                      id="adoption_name"
                      type="text"
                      placeholder="Input Adoption Agencie's Name"
                      v-model.trim="name"
                    ></b-form-input>
                  </b-col>
                </b-row>
                <b-row class="my-1">
                  <b-col sm="2">
                    <label for="adoption_src">圖片位址:</label>
                  </b-col>
                  <b-col sm="10">
                    <b-form-input
                      id="adoption_src"
                      type="url"
                      placeholder="Input image url"
                      v-model.trim="src"
                    ></b-form-input>
                  </b-col>
                </b-row>
                <b-row class="my-1">
                  <b-col sm="2">
                    <label for="adoption_introduction">機構簡介:</label>
                  </b-col>
                  <b-col sm="10">
                    <b-form-input
                      id="adoption_introduction"
                      type="text"
                      placeholder="Introduction"
                      v-model.trim="introduction"
                    ></b-form-input>
                  </b-col>
                </b-row>
                <b-row class="my-1">
                  <b-col sm="2">
                    <label for="adoption_address">地址:</label>
                  </b-col>
                  <b-col sm="10">
                    <b-form-input
                      id="adoption_address"
                      type="text"
                      placeholder="Input address"
                      v-model.trim="address"
                    ></b-form-input>
                  </b-col>
                </b-row>
                <b-row class="my-1">
                  <b-col sm="2">
                    <label for="adoption_phone">電話:</label>
                  </b-col>
                  <b-col sm="10">
                    <b-form-input
                      id="adoption_phone"
                      type="text"
                      placeholder="Input phone number"
                      v-model.trim="phone"
                    ></b-form-input>
                  </b-col>
                </b-row>
                <b-row class="my-1">
                  <b-col sm="2">
                    <label for="adoption_content">介紹:</label>
                  </b-col>
                  <b-col sm="10">
                    <b-form-textarea
                      id="adoption_content"
                      type="text"
                      placeholder="Input content"
                      rows="2"
                      v-model.trim="content"
                    ></b-form-textarea>
                  </b-col>
                </b-row>
                <b-row class="my-1">
                  <b-col sm="2">
                    <label for="adoption_center">座標:</label>
                  </b-col>
                  <b-col sm="5">
                    <b-form-input
                      id="adoption_lat"
                      type="text"
                      placeholder="Input adoption_lat"
                      v-model.trim="center.lat"
                    ></b-form-input>
                  </b-col>
                  <b-col sm="5">
                    <b-form-input
                      id="adoption_lng"
                      type="text"
                      placeholder="Input adoption_lng"
                      v-model.trim="center.lng"
                    ></b-form-input>
                  </b-col>
                </b-row>
                <b-button
                  class="right"
                  v-on:click="$bvModal.hide(`createAdoption`); createAdoption()"
                >確認新增文章</b-button>
              </b-card>
            </b-col>
          </b-row>
        </b-container>
      </b-modal>
    </div>
  </b-container>
</template>

<script>
import axios from "axios";
export default {
  name: "manageAdoption",
  data() {
    return {
      name: "",
      src: "",
      introduction: "",
      address: "",
      phone: "",
      content: "",
      comments: [],
      center: { lat: "", lng: "" }
    };
  },
  mounted() {
    this.getAdoption();
  },
  methods: {
    deleteAdoption(index) {
      let target = this.comments[index];
      if (confirm(`是否刪除 ${target.name} ?`)) {
        axios
          .delete(`http://localhost:4000/comments/${target.id}`)
          .then(res => {
            console.log(res);
            this.comments.splice(index, 1);
          })
          .catch(err => {
            console.log(err);
          });
      }
    },
    cleanData() {
      this.name = "";
      this.src = "";
      this.introduction = "";
      this.address = "";
      this.phone = "";
      this.content = "";
      this.center = { lat: "", lng: "" };
    },
    createAdoption() {
      var adoption = {
        name: this.name,
        src: this.src,
        introduction: this.introduction,
        address: this.address,
        phone: this.phone,
        content: this.content
      };
      adoption["center"] = {};
      this.center = { lat: this.center.lat, lng: this.center.lng };
      adoption["center"] = this.center;
      axios.post("http://localhost:4000/comments", adoption).then(res => {
        console.log(res);
        this.name = "";
        this.src = "";
        this.introduction = "";
        this.address = "";
        this.phone = "";
        this.content = "";
        this.center.lat = "";
        this.center.lng = "";
        this.comments.push(res.data);
      });
    },
    updateAdoption(index) {
      let target = this.comments[index];
      var adoption = {
        name: target.name,
        src: target.src,
        introduction: target.introduction,
        address: target.address,
        phone: target.phone,
        content: target.content
      };
      adoption["center"] = {};
      this.center = { lat: target.center.lat, lng: target.center.lng };
      adoption["center"] = this.center;
      axios
        .patch(`http://localhost:4000/comments/${target.id}`, adoption)
        .then(res => {
          console.log(res);
        });
    },
    getAdoption() {
      axios.get("http://localhost:4000/comments").then(res => {
        console.log(res);
        this.comments = res.data;
      });
    }
  }
};
</script>

<style>
.center {
  text-align: center;
}
.right {
  margin: 20px 0 0;
  float: right;
}
.left {
  margin: 20px 0 0;
  float: left;
}
.left2 {
  margin: 20px 20px 0;
  float: left;
}
</style>
