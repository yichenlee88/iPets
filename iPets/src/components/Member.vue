<template>
  <b-container>
    <div class="intro">
      <h1>What kind of dog?</h1>
      <p class="lead mb-0">
        上傳狗狗的萌照吧! &#128054;
      </p>
    </div>
    <b-input-group class="my-3 px-2 py-2 bg-white shadow-sm">
      <input
        id="upload"
        type="file"
        @change="handleFileUpload"
        accept="image/jpeg, image/png"
        class="form-control border-0"
      />
      <label id="upload-label" for="upload" class="font-weight-light text-muted"
        >Choose file</label
      >
      <div class="input-group-append">
        <label for="upload" class="btn btn-light m-0 px-4">
          <i class="fa fa-cloud-upload mr-2 text-muted"></i
          ><small class="text-uppercase font-weight-bold text-muted"
            >Choose file</small
          ></label
        >
      </div>
    </b-input-group>
    <b-card-group deck>
      <b-card header-tag="header">
        <template v-slot:header>
          <i class="fas fa-eye"></i>
          <p class="mb-0" style="display: inline-block">Preview</p>
        </template>
        <b-img
          class="img-preview"
          :src="preview"
          fluid
          rounded
          alt="Responsive image"
        ></b-img>
      </b-card>
      <b-card header-tag="header" no-body>
        <template v-slot:header>
          <i class="fas fa-trophy"></i>
          <p class="mb-0" style="display: inline-block">Leaderboard</p>
        </template>
        <b-list-group flush>
          <b-list-group-item
            align="center"
            justify="center"
            v-for="leader in leaderboard"
            :key="leader.top"
          >
            <b-row>
              <b-col> #{{ leader.top }} </b-col>
              <b-col>
                {{ leader.breed }}
              </b-col>
              <b-col>
                <b-badge :variant="leader.top | badge_state" pill>
                  <i v-if="leader.top === 1" class="fas fa-star"></i>
                  {{ leader.score | round(3) }}
                </b-badge>
              </b-col>
            </b-row>
          </b-list-group-item>
        </b-list-group>
      </b-card>
    </b-card-group>
  </b-container>
</template>

<script>
import axios from "axios";
export default {
  name: "Member",
  data() {
    return {
      preview: null,
      leaderboard: []
    };
  },
  filters: {
    round: function(value, decimals) {
      return (
        Math.round(value * Math.pow(10, decimals)) / Math.pow(10, decimals)
      );
    },
    badge_state: function(value) {
      if (value === 1) {
        return "dark";
      } else {
        return "light";
      }
    }
  },
  methods: {
    handleFileUpload(e) {
      this.leaderboard = [];

      var file = e.target.files[0];
      var reader = new FileReader();
      reader.onload = e => {
        this.preview = e.target.result;
      };
      reader.readAsDataURL(file);

      let formData = new FormData();
      formData.append("photo", file);
      axios
        .post("/upload", formData, {
          baseURL: "http://localhost:3000",
          timeout: 30000,
          headers: {}
        })
        .then(response => {
          var i = 1;
          for (const [key, value] of Object.entries(response.data)) {
            this.leaderboard.push({
              top: i,
              breed: key,
              score: value
            });
            i = i + 1;
          }
        });
    }
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.intro {
  text-align: center;
}

#upload {
  opacity: 0;
}

#upload-label {
  position: absolute;
  top: 50%;
  left: 1rem;
  transform: translateY(-50%);
}

.img-preview {
  max-height: 300px;
  display: block;
  margin-left: auto;
  margin-right: auto;
}
</style>
