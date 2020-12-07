<template>
  <b-container>
    <div class="intro">
      <!-- <h1>What kind of dog?</h1> -->
      <img src="../assets/辨識圖.png" style="width:75%" />
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
  name: "Identify",
  data() {
    return {
      preview: "../static/img/6.jpg",
      leaderboard: [],
      translate_tables: {
        Chihuaha: "吉娃娃",
        "Japanese Spaniel": "日本狆",
        "Maltese Dog": "馬爾濟斯",
        Pekinese: "獅子狗",
        "Shih-Tzu": "西施犬",
        "Blenheim Spaniel": "查理王小獵犬",
        Papillon: "蝴蝶犬",
        "Toy Terrier": "英國玩賞㹴",
        "Rhodesian Ridgeback": "羅得西亞背脊犬",
        "Afghan Hound": "阿富汗獵狗",
        "Basset Hound": "巴吉度獵犬",
        Beagle: "小獵犬",
        Bloodhound: "尋血獵犬",
        Bluetick: "布魯克浣熊獵犬",
        "Black-and-tan Coonhound": "黑褐色獵浣熊犬",
        "Walker Hound": "獵浣熊犬",
        "English Foxhound": "英國獵狐犬",
        Redbone: "紅骨獵浣熊犬",
        Borzoi: "蘇俄牧羊犬",
        "Irish Wolfhound": "愛爾蘭獵狼犬",
        "Italian Greyhound": "義大利灰狗",
        Whippet: "惠比特犬",
        "Ibizian Hound": "依比沙獵犬 (Ibizan Hound)",
        "Norwegian Elkhound": "挪威獵麋犬",
        Otterhound: "奧達獵犬",
        Saluki: "東非獵犬",
        "Scottish Deerhound": "蘇格蘭獵鹿犬",
        Weimaraner: "威瑪獵犬",
        "Staffordshire Bullterrier": "斯塔福郡鬥牛㹴",
        "American Staffordshire Terrier": "美國比特鬥牛㹴",
        "Bedlington Terrier": "貝靈頓㹴",
        "Border Terrier": "邊境㹴",
        "Kerry Blue Terrier": "凱利藍㹴 ",
        "Irish Terrier": "愛爾蘭㹴",
        "Norfolk Terrier": "諾福克㹴",
        "Norwich Terrier": "挪利其㹴",
        "Yorkshire Terrier": "約克夏㹴",
        "Wirehaired Fox Terrier": "剛毛獵狐",
        "Lakeland Terrier": "湖畔㹴",
        "Sealyham Terrier": "錫利哈姆㹴",
        Airedale: "萬能㹴",
        Cairn: "凱恩㹴",
        "Australian Terrier": " 澳大利亞㹴",
        "Dandi Dinmont": "矮腳狄文㹴",
        "Boston Bull": "波士頓㹴",
        "Miniature Schnauzer": "迷你雪納瑞",
        "Giant Schnauzer": "巨型雪納瑞",
        "Standard Schnauzer": "雪納瑞",
        "Scotch Terrier": "蘇格蘭㹴",
        "Tibetan Terrier": "西藏㹴",
        "Silky Terrier": "澳洲絲毛㹴",
        "Soft-coated Wheaten Terrier": "愛爾蘭軟毛㹴",
        "West Highland White Terrier": "西高地白㹴",
        Lhasa: "拉薩犬",
        "Flat-coated Retriever": "順毛獵犬",
        "Curly-coater Retriever": "捲毛獵犬",
        "Golden Retriever": "黃金獵犬",
        "Labrador Retriever": "拉不拉多",
        "Chesapeake Bay Retriever": "乞沙比克獵犬",
        "German Short-haired Pointer": "德國指標犬",
        Vizsla: "維茲拉犬",
        "English Setter": "英格蘭獵犬",
        "Irish Setter": "愛爾蘭獵犬",
        "Gordon Setter": "哥頓雪達犬",
        Brittany: "布列塔尼犬",
        Clumber: "克倫柏獵犬",
        "English Springer Spaniel": "英國史賓格犬",
        "Welsh Springer Spaniel": "     威爾斯史賓格犬",
        "Cocker Spaniel": "英國可卡犬",
        "Sussex Spaniel": "蘇士塞獵犬",
        "Irish Water Spaniel": "愛爾蘭水獵犬",
        Kuvasz: "庫瓦茲犬",
        Schipperke: "史奇派克犬",
        Groenendael: "格羅安達牧羊犬",
        Malinois: "馬林諾斯牧羊犬",
        Briard: "伯瑞犬",
        Kelpie: "卡爾比",
        Komondor: "可蒙犬",
        "Old English Sheepdog": "英國古代牧羊犬",
        "Shetland Sheepdog": "喜樂蒂牧羊犬",
        Collie: "牧羊犬",
        "Border Collie": "邊境牧羊犬",
        "Bouvier des Flandres": "法蘭德斯畜牧犬",
        Rottweiler: "羅威納犬",
        "German Shepard": "德國牧羊犬",
        Doberman: "杜賓犬",
        "Miniature Pinscher": "迷你杜賓犬",
        "Greater Swiss Mountain Dog": "大瑞士山地犬",
        "Bernese Mountain Dog": "伯恩山犬",
        Appenzeller: "安托巴克牧牛犬",
        EntleBucher: "安潘培勒牧牛犬",
        Boxer: "拳師犬",
        "Bull Mastiff": "鬥牛獒",
        "Tibetan Mastiff": "藏獒",
        "French Bulldog": "法國鬥牛犬",
        "Great Dane": "大丹犬",
        "Saint Bernard": "聖伯納犬",
        "Eskimo Dog": "愛斯基摩犬",
        Malamute: "雪橇犬",
        "Siberian Husky": "西伯利亞雪橇犬",
        Affenpinscher: "艾芬品",
        Basenji: "貝生吉犬",
        Pug: "巴哥犬",
        Leonberg: "藍波格犬",
        Newfoundland: "紐芬蘭犬",
        "Great Pyrenees": "庇里牛斯山犬",
        Samoyed: "薩摩耶犬",
        Pomeranian: "博美犬",
        Chow: "鬆獅犬",
        Keeshond: "荷蘭獅毛犬",
        "Brabancon Griffon": "布魯塞爾格林芬犬",
        Pembroke: "潘布魯克",
        Cardigan: "卡提根",
        "Toy Poodle": "玩具型貴賓",
        "Miniature Poodle": "迷你貴賓犬",
        "Standard Poodle": "貴賓犬",
        "Mexican Hairless": "墨西哥無毛犬",
        Dingo: "澳洲野犬",
        Dhole: "豺",
        "African Hunting Dog": "非洲野狗"
      }
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
              breed: this.translate_tables[key],
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
