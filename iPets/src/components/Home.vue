<template>
  <div>
    <b-carousel
      id="carousel-1"
      v-model="slide"
      :interval="4000"
      controls
      indicators
      background="#ababab"
      img-width="1024"
      img-height="480"
      style="text-shadow: 1px 1px 2px #333;"
      @sliding-start="onSlideStart"
      @sliding-end="onSlideEnd"
    >
      <b-carousel-slide>
        <template v-slot:img>
          <img class="d-block img-fluid" src="../assets/carousel-01.jpg" alt="image slot">
        </template>
      </b-carousel-slide>
      <b-carousel-slide>
        <template v-slot:img>
          <img class="d-block img-fluid" src="../assets/carousel-02.jpg" alt="image slot">
        </template>
      </b-carousel-slide>
      <b-carousel-slide>
        <template v-slot:img>
          <img class="d-block img-fluid" src="../assets/carousel-03.jpg" alt="image slot">
        </template>
      </b-carousel-slide>
    </b-carousel>
    <!-- 領養機構資訊 -->
    <div style="background-color:#F5F5F5">
      <b-container>
        <b-row class="adoptionAgenciesTitle text-center">
          <b-col>領養機構資訊</b-col>
        </b-row>
        <b-row class="adoptionAgencies" cols-md="3">
          <div v-for="(item, i) in comments" :key="i">
            <b-col>
              <b-card
                v-bind:title="item.name"
                v-bind:img-src="item.src"
                img-alt="Image"
                img-top
                tag="article"
              >
                <b-card-text>{{ item.introduction }}</b-card-text>
                <b-button
                  href="#"
                  class="learnMore"
                  @click="
                    $bvModal.show(`${i}`);
                    convertCenter();
                  "
                >了解更多</b-button>
                <b-modal :id="`${i}`" v-bind:title="item.name">
                  <b-container>
                    <b-card v-bind:img-src="item.src" img-alt="Image" img-top>
                      <b-card-text>
                        <b-row>
                          <b-col cols="4">
                            <strong>地址</strong>：
                          </b-col>
                          <b-col>{{ item.address }}</b-col>
                        </b-row>
                        <b-row>
                          <b-col cols="4">
                            <strong>連絡電話</strong>：
                          </b-col>
                          <b-col>{{ item.phone }}</b-col>
                        </b-row>
                        <b-row>
                          <b-col cols="4">
                            <strong>簡介</strong>:
                          </b-col>
                          <b-col>{{ item.content }}</b-col>
                        </b-row>
                      </b-card-text>
                      <GmapMap
                        :id="`map_${i}`"
                        :center="convertCenter(i)"
                        :zoom="15"
                        map-type-id="roadmap"
                        style="  width: 100%;  height: 200px;"
                      ></GmapMap>
                    </b-card>
                  </b-container>
                </b-modal>
              </b-card>
            </b-col>
          </div>
        </b-row>
      </b-container>
    </div>
  </div>
</template>

<script>
import axios from "axios";
export default {
  name: "home",
  data() {
    return {
      slide: 0,
      center: { lat: 25.0325917, lng: 121.5624999 },
      comments: [],
      currentPlace: null
    };
  },

  mounted() {
    this.getAdoption();
  },

  methods: {
    geolocate: function() {
      navigator.geolocation.getCurrentPosition(position => {
        this.center = {
          lat: position.coords.latitude,
          lng: position.coords.longitude
        };
      });
    },
    convertCenter(i) {
      let target = this.comments[i];
      return {
        lat: parseFloat(target.center.lat),
        lng: parseFloat(target.center.lng)
      };
    },
    getAdoption() {
      axios.get("http://localhost:4000/comments").then(res => {
        console.log(res);
        this.comments = res.data;
      });
    },
    onSlideStart(slide) {
      this.sliding = true;
    },
    onSlideEnd(slide) {
      this.sliding = false;
    }
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
/* .carousel {
  float: left;
  list-style: none;
  position: static;
  width: 1519px;
  zoom: 1;
  margin: 0;
  padding: 0;
  overflow: hidden;
} */

.adoptionAgenciesTitle {
  font-size: 30px;
  line-height: 100px;
}

.card {
  min-height: 600px;
}

.learnMore {
  position: absolute;
  right: 15px;
  bottom: 15px;
}

.marketing {
  margin-top: 20px;
  margin-bottom: 20px;
  max-width: 20rem;
}

.card-text {
  font-family: "Microsoft JhengHei", Helvetica, Arial, sans-serif;
}

.google-map {
  width: 100%;
  height: 200px;
}

/* @media screen and (max-width: 768px) {
  .carousel {
    width: 400px;
  }
  .adoptionAgenciesTitle {
    font-size: 18px;
  }
} */
</style>
