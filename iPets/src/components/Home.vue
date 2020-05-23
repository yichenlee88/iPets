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
              取名「再生保護協會」的初衷就是希望將這些流浪在街頭上，無論是飢餓瘦到剩皮包骨的、或是因為誤踩到捕獸夾而斷了一條腿到兩條腿的。
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
              <div class="card-body">
                <p>張媽媽流浪動物之家張媽媽流浪動物之家張媽媽流浪動物之家</p>
                <img class="img-fluid" src="../assets/4.jpg" />
                <p>
                  張媽媽流浪動物之家張媽媽流浪動物之家張媽媽流浪動物之家張媽媽流浪動物之家張媽媽流浪動物之家
                </p>
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
              <div class="card-body">
                <p>徐文良動物保護協會徐文良動物保護協會徐文良動物保護協會</p>
                <img class="img-fluid" src="../assets/4.jpg" />
                <p>
                  徐文良動物保護協會徐文良動物保護協會徐文良動物保護協會徐文良動物保護協會徐文良動物保護協會
                </p>
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
              <div class="card-body">
                <p>臺北市動物之家臺北市動物之家臺北市動物之家</p>
                <img class="img-fluid" src="../assets/4.jpg" />
                <p>
                  臺北市動物之家臺北市動物之家臺北市動物之家臺北市動物之家臺北市動物之家
                </p>
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
    return {};
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
</style>
