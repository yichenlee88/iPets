<template>
  <div>
    <div class="header">
      <!--輪播圖-->
      <b-carousel
        id="carousel-1"
        class="carousel"
        :interval="3000"
        controls
        indicators
        background="#ababab"
      >
        <b-carousel-slide>
          <template v-slot:img>
            <img
              class="d-block img-fluid w-100"
              width="1024"
              src="../assets/carousel-01.jpg"
              alt="image slot"
            />
          </template>
        </b-carousel-slide>
        <b-carousel-slide>
          <template v-slot:img>
            <img
              class="d-block img-fluid w-100"
              width="1024"
              src="../assets/carousel-02.jpg"
              alt="image slot"
            />
          </template>
        </b-carousel-slide>
        <b-carousel-slide>
          <template v-slot:img>
            <img
              class="d-block img-fluid w-100"
              width="1024"
              src="../assets/carousel-03.jpg"
              alt="image slot"
            />
          </template>
        </b-carousel-slide>
      </b-carousel>
    </div>

    <b-container>
      <b-row class="adoptionAgenciesTitle text-center"
        ><b-col>領養機構資訊</b-col>
      </b-row>
      <b-row class="adoptionAgencies" cols-lg="3">
        <div v-for="(item, i) in post" :key="i">
          <b-col>
            <b-card
              v-bind:title="item.Name"
              v-bind:img-src="item.src"
              img-alt="Image"
              img-top
              tag="article"
            >
              <b-card-text>
                {{ item.Introduction }}
              </b-card-text>
              <b-button
                href="#"
                class="learnMore"
                @click="$bvModal.show(`${i}`)"
                >了解更多</b-button
              >
              <b-modal :id="`${i}`" v-bind:title="item.Name">
                <b-container>
                  <b-card v-bind:img-src="item.src" img-alt="Image" img-top>
                    <b-card-text>
                      <b-row>
                        <b-col cols="4"><strong>地址</strong>：</b-col>
                        <b-col>{{ item.Addres }}</b-col>
                      </b-row>
                      <b-row>
                        <b-col cols="4"><strong>連絡電話</strong>：</b-col>
                        <b-col>{{ item.Phone }}</b-col>
                      </b-row>
                      <b-row>
                        <b-col cols="4"><strong>簡介</strong>:</b-col>
                        <b-col>{{ item.Content }}</b-col>
                      </b-row>
                    </b-card-text>
                    <GmapMap
                      :id="`map_${i}`"
                      :center="convertCenter(item.Center)"
                      :zoom="15"
                      map-type-id="roadmap"
                      style="  width: 100%;  height: 200px;"
                    >
                    </GmapMap>
                  </b-card>
                </b-container>
              </b-modal>
            </b-card>
          </b-col>
        </div>
      </b-row>
    </b-container>
    <!--"longitude":"", //經度
      "latitude":"" //緯度
      -->
    <!--領養機構資訊
                <GmapMap
                  :center="{ lat: 25.011085, lng: 121.752719 }"
                  :zoom="15"
                  map-type-id="roadmap"
                  style="  width: 100%;  height: 200px;"
                >
                  <GmapMarker
                    :key="index"
                    v-for="(m, index) in markers"
                    :position="m.position"
                    :clickable="true"
                    :draggable="true"
                    @click="center = m.position"
                  />
                </GmapMap>
                <div id="map"></div>
              </div>
            </div>
          </div>
          <div class="modal-footer">
            <button
              type="button"
              class="btn btn-outline-light text-dark border-dark"
              data-dismiss="modal"
            >
              關閉視窗
            </button>
          >-->
  </div>
</template>

<script>
export default {
  name: "Home",
  data() {
    return {
      center: { lat: 25.0325917, lng: 121.5624999 },
      markers: [],
      places: [],
      infowindow: [],
      currentPlace: null,
      post: []
    };
  },

  mounted() {
    this.geolocate();
    this.$http.get("/static/AdoptionAgencies.json").then(response => {
      this.post = response.data;
    });
  },

  methods: {
    // receives a place object via the autocomplete component
    // setPlace(place) {
    //   this.currentPlace = place;
    // },
    // addMarker() {
    //   if (this.currentPlace) {
    //     const marker = {
    //       lat: this.currentPlace.geometry.location.lat(),
    //       lng: this.currentPlace.geometry.location.lng()
    //     };
    //     this.markers.push({ position: marker });
    //     this.places.push(this.currentPlace);
    //     this.center = marker;
    //     this.currentPlace = null;
    //   }
    // },
    geolocate: function() {
      navigator.geolocation.getCurrentPosition(position => {
        this.center = {
          lat: position.coords.latitude,
          lng: position.coords.longitude
        };
      });
    },
    convertCenter(center) {
      return {
        lat: parseFloat(center.lat),
        lng: parseFloat(center.lng)
      };
    }
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.carousel {
  float: left;
  list-style: none;
  position: static;
  width: 1519px;
  zoom: 1;
  margin: 0;
  padding: 0;
  overflow: hidden;
}

.adoptionAgenciesTitle {
  font-size: 30px;
  line-height: 100px;
}

.card {
  min-height: 600px;
  margin-bottom: 20px;
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

@media screen and (max-width: 768px) {
  .carousel {
    width: 400px;
  }
  .adoptionAgenciesTitle {
    font-size: 18px;
  }
}
</style>
