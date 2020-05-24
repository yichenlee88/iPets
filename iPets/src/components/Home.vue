<template>
  <div class="container index">
    <!--輪播圖-->
    <div id="myCarousel" class="carousel slide" data-ride="carousel">
      <!--連結導引項目-->
      <ul class="carousel-indicators">
        <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
        <li data-target="#myCarousel" data-slide-to="1" class="active"></li>
        <li data-target="#myCarousel" data-slide-to="2" class="active"></li>
      </ul>
      <!--圖片顯示區域-->
      <div class="carousel-inner">
        <div class="carousel-item active">
          <img
            style="padding-left: 20px; width: 1500px; height: 900px;"
            src="../assets/1.jpg"
          />
        </div>
        <div class="carousel-item">
          <img
            style="padding-left: 20px; width: 1500px; height: 900px;"
            src="../assets/2.jpg"
          />
        </div>
        <div class="carousel-item">
          <img
            style="padding-left: 20px; width: 1500px; height: 900px;"
            src="../assets/3.jpg"
          />
        </div>
      </div>
      <!--前後切換連結-->
      <a class="carousel-control-prev" href="#myCarousel" data-slide="prev">
        <span class="carousel-control-prev-icon"></span>
      </a>
      <a class="carousel-control-next" href="#myCarousel" data-slide="next">
        <span class="carousel-control-next-icon"></span>
      </a>
    </div>
    <!--領養機構資訊-->
    <div class="container marketing">
      <div class="row" style="margin-left: 5px;">
        <div class="col-12 col-sm-4">
          <div class="img-thumbnail" style="height: 550px;">
            <img
              class="rounded"
              src="../assets/mama.jpg"
              style="max-width: 100%"
            />
            <H2>張媽媽流浪動物之家</H2>
            <p style="text-align:left">
              不管你相信不相信，只要我們願意付出，哪怕只有一點點，牠們會無條件用牠們一輩子的愛回報給我們。就從這一點點開始，讓愛延續，讓生命成長茁壯。
            </p>
            <button
              type="button"
              class="btn btn-info"
              data-toggle="modal"
              data-target="#myModal01"
              style="position: absolute; right: 20px; bottom: 5px;"
            >
              了解更多
            </button>
          </div>
        </div>
        <div class="col-12 col-sm-4">
          <div class="img-thumbnail" style="height: 550px;">
            <img
              class="rounded"
              src="../assets/dogpark.jpg"
              style="max-width: 100%"
            />
            <H2>徐文良動物保護協會</H2>
            <p style="text-align:left">
              關於園區長期不斷的搶救收容所的死刑犬和救援在外生病和受傷的狗狗,目前已經有三千多個孩子
            </p>
            <button
              type="button"
              class="btn btn-info"
              data-toggle="modal"
              data-target="#myModal02"
              style="position: absolute; right: 20px; bottom: 5px;"
            >
              了解更多
            </button>
          </div>
        </div>
        <div class="col-12 col-sm-4">
          <div class="img-thumbnail" style="height: 550px;">
            <img
              class="rounded"
              src="../assets/taipeicity.png"
              style="max-width: 100%;ASDF"
            />
            <H2>臺北市動物之家</H2>
            <p style="text-align:left">
              臺北市動物之家，隸屬於臺北市動物保護處，位於台北市內湖區，負責台北市流浪動物及野生動物之收容、認領、認養、絕育、救傷醫療、定期篩檢及管理，動物收容教育推廣等事項
            </p>
            <button
              type="button"
              class="btn btn-info float-right"
              data-toggle="modal"
              data-target="#myModal03"
              style="position: absolute; right: 20px; bottom: 5px;"
            >
              了解更多
            </button>
          </div>
        </div>
      </div>
    </div>
    <!--張媽媽內文-->
    <div class="modal fade" id="myModal01">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h6 class="modal-title">張媽媽流浪動物之家</h6>
            <button type="button" class="close" data-dismiss="modal">x</button>
          </div>
          <div class="modal-body">
            <div class="card border-0">
              <img class="card-img-top" src="../assets/mama.jpg" />
              <div class="card-body">
                <p class="card-text" style="text-align:left">
                  <strong>地址</strong>： 226新北市平溪區51之7號
                  <br />
                  <strong>連絡電話</strong>： 0936108187 <br />
                  <strong>簡介</strong>:<br />
                  取名「再生保護協會」的初衷就是希望將這些流浪在街頭上，無論是飢餓瘦到剩皮包骨的、或是因為誤踩到捕獸夾而斷了一條腿到兩條腿的、車禍撞傷導致癱瘓，還有整窩母帶子的浪浪們，只要還在我們能力範圍內，每一隻都會受到我們完善的保護與志工細心的呵護，並且在結紮過後，為牠們找尋第二個家，讓新家人能夠代替我們牽著牠繼續幸福的走下去。
                </p>
                <br />
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
          </div>
        </div>
      </div>
    </div>
    <!--徐文良內文-->
    <div class="modal fade" id="myModal02">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h6 class="modal-title">徐文良動物保護協會</h6>
            <button type="button" class="close" data-dismiss="modal">x</button>
          </div>
          <div class="modal-body">
            <div class="card border-0">
              <img class="card-img-top" src="../assets/dogpark.jpg" />
              <div class="card-body">
                <p class="card-text" style="text-align:left">
                  <strong>地址</strong>： 72543台南市將軍區西湖112號
                  <br />
                  <strong>連絡電話</strong>： <br />
                  <strong>簡介</strong>:<br />
                  園區長期不斷的搶救收容所的死刑犬和救援在外生病和受傷的狗狗,目前已經有三千多個孩子
                  龐大的飼料罐頭和醫療支出 希望大家一起來幫忙
                  做園區最堅強的後盾~感恩
                </p>
                <br />
                <GmapMap
                  :center="{ lat: 23.194341, lng: 120.140508 }"
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
          </div>
        </div>
      </div>
    </div>
    <!--臺北市動物之家內文-->
    <div class="modal fade" id="myModal03">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h6 class="modal-title">臺北市動物之家</h6>
            <button type="button" class="close" data-dismiss="modal">x</button>
          </div>
          <div class="modal-body">
            <div class="card border-0">
              <img class="card-img-top" src="../assets/taipeicity.png" />
              <div class="card-body">
                <p class="card-text" style="text-align:left">
                  <strong>地址</strong>： 114台北市內湖區潭美街852號
                  <br />
                  <strong>連絡電話</strong>：02 8791 3254 <br />
                  <strong>簡介</strong>:<br />
                  臺北市自105年起透過推廣動物友善空間計畫宣傳友善動物環境及空間的觀念，希望有更多民間的餐飲、遊樂地點、公共場所、交通設施能夠讓寵物進入。並藉由此計畫輔導各業者開放空間「歡迎飼主與寵物一同進入」，及輔導店家於門口張貼「寵愛貓狗」的動物友善空間標識貼紙，讓飼主們明暸與寵物休憩好去處，構築寵物友善環境，讓寵物在食、衣、住、行各方面能真正融入飼主及人們的生活。
                </p>
                <br />
                <GmapMap
                  :center="{ lat: 25.063132, lng: 121.608937 }"
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
          </div>
        </div>
      </div>
    </div>
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
      currentPlace: null
    };
  },

  mounted() {
    this.geolocate();
  },

  methods: {
    // receives a place object via the autocomplete component
    setPlace(place) {
      this.currentPlace = place;
    },
    addMarker() {
      if (this.currentPlace) {
        const marker = {
          lat: this.currentPlace.geometry.location.lat(),
          lng: this.currentPlace.geometry.location.lng()
        };
        this.markers.push({ position: marker });
        this.places.push(this.currentPlace);
        this.center = marker;
        this.currentPlace = null;
      }
    },
    geolocate: function() {
      navigator.geolocation.getCurrentPosition(position => {
        this.center = {
          lat: position.coords.latitude,
          lng: position.coords.longitude
        };
      });
    }
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h1,
h2 {
  font-weight: normal;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
.carousel {
  overflow: hidden;
  max-height: 500px;
  height: 1600px;
  margin-top: 20px;
}

.marketing {
  margin-top: 20px;
  margin-bottom: 20px;
}

.card-text {
  font-family: "Microsoft JhengHei", Helvetica, Arial, sans-serif;
}

.google-map {
  width: 100%;
  height: 200px;
}
</style>
