<template>
  <div class="container">
    <div class="timerPicker">
      <h3>倒數計時器</h3>
      <div class="progress">
        <div
          class="progress-bar"
          role="progressbar"
          style="width: 25%"
          aria-valuenow="25"
          aria-valuemin="0"
          aria-valuemax="100"
        ></div>
      </div>
    </div>
    <h3>編輯倒數計時器</h3>
    <div
      class="accordion md-accordion accordion-blocks"
      id="accordionEx78"
      role="tablist"
      aria-multiselectable="true"
    >
      <div class="card">
        <div class="card-header" role="tab" id="headingUnfiled">
          <div class="dropdown float-left">
            <button
              class="btn btn-info btn-sm m-0 mr-3 p-2 dropdown-toggle"
              type="button"
              data-toggle="dropdown"
              aria-haspopup="true"
              aria-expanded="false"
            >
              <i class="fas fa-pencil-alt"></i>
            </button>
            <ul class="dropdown-menu dropdown-info" role="menu">
              <li>
                <a href="#add" data-toggle="tab">新增</a>
              </li>
              <li>
                <a href="#delete" data-toggle="tab">刪除</a>
              </li>
              <li>
                <a href="#update" data-toggle="tab">修改</a>
              </li>
            </ul>
          </div>
          <a
            data-toggle="collapse"
            data-parent="#accordionEx78"
            href="#collapseUnfiled"
            aria-expanded="true"
            aria-controls="collapseUnfiled"
          >
            <h5 class="mt-1 mb-0">
              <span>洗澡</span>
              <i class="fas fa-angle-down rotate-icon"></i>
            </h5>
          </a>
        </div>
        <div
          id="collapseUnfiled"
          class="collapse"
          role="tabpanel"
          aria-labelledby="headingUnfiled"
          data-parent="#accordionEx78"
        >
          <div class="card-body">
            <div class="tab-content">
              <div class="tab-pane active fade in" id="add">
                <div class="form-group row" style="margin-top: 15px">
                  <label for="staticEmail" class="col-sm-2 col-form-label"
                    >活動名稱</label
                  >
                  <div class="col-sm-10">
                    <input
                      type="text"
                      class="form-control"
                      id="activityTitle"
                    />
                  </div>
                </div>
                <div class="form-group row" style="margin-top: 15px">
                  <label for="staticEmail" class="col-sm-2 col-form-label"
                    >活動類別</label
                  >
                  <div class="col-sm-10">
                    <input
                      type="text"
                      class="form-control-plaintext"
                      readonly
                      id="activityClass"
                      value="洗澡"
                    />
                  </div>
                </div>
                <div class="form-group row">
                  <label for="startingTime" class="col-sm-2 col-form-label"
                    >開始時間</label
                  >
                  <div class="col-sm-10">
                    <date-picker
                      v-model="date"
                      @dp-hide="doSomethingOnHide"
                      @dp-change="doSomethingOnChange"
                    ></date-picker>
                  </div>
                </div>
                <div class="form-group row">
                  <label for="endingTime" class="col-sm-2 col-form-label"
                    >結束時間</label
                  >
                  <div class="col-sm-10">
                    <date-picker
                      v-model="date"
                      @dp-hide="doSomethingOnHide"
                      @dp-change="doSomethingOnChange"
                    ></date-picker>
                  </div>
                </div>
                <div class="form-group row" style="margin-top: 15px">
                  <label for="staticEmail" class="col-sm-2 col-form-label"
                    >重複時間</label
                  >
                  <div class="col-sm-2">
                    <input
                      type="text"
                      class="form-control"
                      id="activityTitle"
                    />
                  </div>
                  <form>
                    <div class="form-group">
                      <select id="diploma" name="diploma" class="form-control">
                        <option value="day" disabled>天</option
                        ><option value="week">週</option
                        ><option value="month" selected>個月 </option
                        ><option value="year">年</option></select
                      >
                    </div>
                  </form>
                  <div class="w-100"></div>
                  <div>
                    <button
                      type="submit"
                      class="btn btn-primary"
                      style="width: 100%;"
                      @click="save"
                    >
                      儲存
                    </button>
                  </div>
                </div>
              </div>
              <div class="tab-pane fade" id="delete">
                <h4>Cat Cage</h4>
              </div>
              <div class="tab-pane fade" id="update">
                <h4>Cat Drunk</h4>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
// Import required dependencies
import "bootstrap/dist/css/bootstrap.css";

// Import this component
import datePicker from "vue-bootstrap-datetimepicker";

// Import date picker css
import "pc-bootstrap4-datetimepicker/build/css/bootstrap-datetimepicker.css";

export default {
  data() {
    return {
      name: "HomeLogin",
      date: new Date(),
      options: {
        format: "DD/MM/YYYY",
        useCurrent: false
      }
    };
  },
  components: {
    datePicker
  }
};
</script>

<style scoped>
.container {
  margin-top: 20px;
  margin-bottom: 20px;
  min-height: 600px;
}
li a {
  text-decoration: none;
  color: black;
}
a {
  text-decoration: none;
  color: black;
  size: 14px;
  text-align: left;
}
a:hover {
  text-decoration: underline;
}
progress {
  margin-top: 5px;
}
h3 {
  text-align: left;
  font-family: "Avenir", Helvetica, Arial, sans-serif;
}
.timerPicker {
  margin-bottom: 20px;
}
.timerPicker h3 {
  text-align: left;
}

.dropdown .nav-link {
  color: #fff;
  text-decoration: none;
}
.dropdown .dropdown-menu a {
  color: #000;
  text-decoration: none;
}
.dropdown .btn {
  color: #fff;
}
.dropdown-menu .dropdown-item {
  display: inline-block;
  width: 100%;
  padding: 10px 5px;
}
</style>
