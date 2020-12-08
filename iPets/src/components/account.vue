<template>
  <b-container>
    <b-row>
      <b-col cols="12" sm="12" md="12">
        <h3>更改帳號：</h3>
      </b-col>
      <b-col class="coltitle" cols="4" sm="4" md="4">
        <p>更改帳號：</p>
      </b-col>
      <b-col cols="8" sm="8" md="8">
        <div>
          <b-form-input
            id="updateEmail"
            class="InputClass"
            name="updateEmail"
            v-model="updateEmail"
            placeholder="Email"
          ></b-form-input>
        </div>
      </b-col>
      <b-col cols="4" sm="4" md="4"></b-col>
      <b-col cols="8" sm="8" md="8">
        <b-button class="ButtonClass" @click="update_Email(updateEmail)"
          >送出</b-button
        >
      </b-col>
    </b-row>
  </b-container>
</template>

<script>
import firebase from "firebase";
var user = firebase.auth().currentUser;

export default {
  data() {
    return {
      updateEmail: ""
    };
  },
  methods: {
    update_Email: function(e) {
      user.updateEmail(this.updateEmail).then(function() {
        alert("變更成功");
      });
    }
  }
};
</script>

<style scoped>
.coltitle {
  padding: 16px 0;
  text-align: right;
}

.InputClass {
  max-width: 70%;
  border-radius: 40px;
}

.ButtonClass {
  height: 48px;
  width: 70%;
  border-radius: 40px;
  margin-bottom: 20px;
  background: -webkit-linear-gradient(
    left,
    rgb(148, 115, 221) 0%,
    rgb(26, 201, 228) 100%
  );
}
</style>
