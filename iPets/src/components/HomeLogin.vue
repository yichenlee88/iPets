<template>
  <div class="container">
    <div class="row">
      <div class="col-6">
        <div class="list-group">
          <h1>執行中</h1>
          <div class="list-group-item">
            <div class="row">
              <div class="col-1" md-2>
                <div class="round">
                  <input type="checkbox" id="checkbox" />
                  <label for="checkbox"></label>
                </div>
              </div>
              <div class="col-9">
                <h3 class="mb-0" style=" text-align: left;">洗澡</h3>
              </div>
              <div class="col-2" md-2>
                <button
                  type="button"
                  class="btn"
                  data-toggle="modal"
                  data-target="#close"
                >
                  <i class="arrow right"></i>
                </button>
                <div
                  class="modal fade"
                  id="close"
                  tabindex="-1"
                  role="dialog"
                  aria-hidden="true"
                >
                  <div
                    class="modal-dialog modal-dialog-centered"
                    role="document"
                  >
                    <div class="modal-content">
                      <div class="modal-header">
                        <h5 class="modal-title">
                          編輯頁面
                        </h5>
                        <button
                          type="button"
                          class="close"
                          data-dismiss="modal"
                          aria-label="Close"
                        >
                          <span aria-hidden="true">&times;</span>
                        </button>
                      </div>
                      <div class="modal-body">
                        <div class="form-group row" style="margin-top: 15px">
                          <label
                            for="staticEmail"
                            class="col-sm-3 col-form-label"
                            >活動名稱</label
                          >
                          <div class="col-sm-9">
                            <input
                              type="text"
                              class="form-control"
                              id="activityTitle"
                            />
                          </div>
                        </div>
                        <div class="form-group row">
                          <label
                            for="startingTime"
                            class="col-sm-3 col-form-label"
                            >開始時間</label
                          >
                          <div class="col-sm-9">
                            <date-picker
                              id="startingTime"
                              v-model="date"
                              @dp-hide="doSomethingOnHide"
                              @dp-change="doSomethingOnChange"
                            ></date-picker>
                          </div>
                        </div>
                        <div class="form-group row">
                          <label
                            for="endingTime"
                            class="col-sm-3 col-form-label"
                            >結束時間</label
                          >
                          <div class="col-sm-9">
                            <date-picker
                              id="endingTime"
                              v-model="date"
                              @dp-hide="doSomethingOnHide"
                              @dp-change="doSomethingOnChange"
                            ></date-picker>
                          </div>
                        </div>
                        <div class="form-group row" style="margin-top: 15px">
                          <label
                            for="staticEmail"
                            class="col-sm-3 col-form-label"
                            >重複時間</label
                          >
                          <div class="col-sm-3">
                            <input
                              type="text"
                              class="form-control"
                              id="activityTitle"
                            />
                          </div>
                          <form>
                            <div class="form-group">
                              <select
                                id="diploma"
                                name="diploma"
                                class="form-control"
                              >
                                <option value="day" disabled>天</option
                                ><option value="week">週</option
                                ><option value="month" selected>個月 </option
                                ><option value="year">年</option></select
                              >
                            </div>
                          </form>
                        </div>
                      </div>
                      <div class="modal-footer">
                        <button
                          type="button"
                          class="btn btn-secondary"
                          data-dismiss="modal"
                        >
                          關閉
                        </button>
                        <button type="button" class="btn btn-primary">
                          儲存
                        </button>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="col-6">
        <div class="list-group">
          <h1>即將到來</h1>
          <div class="list-group-item">
            <div class="row">
              <div class="col-1" md-2>
                <div class="round">
                  <input type="checkbox" id="checkbox" />
                  <label for="checkbox"></label>
                </div>
              </div>
              <div class="col-9">
                <h3 class="mb-0" style=" text-align: left;">洗澡</h3>
              </div>
              <div class="col-2" md-2>
                <button
                  type="button"
                  class="btn"
                  data-toggle="modal"
                  data-target="#close"
                >
                  <i class="arrow right"></i>
                </button>
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

.arrow {
  border: solid black;
  border-width: 0 3px 3px 0;
  display: inline-block;
  padding: 3px;
}

.right {
  transform: rotate(-45deg);
  -webkit-transform: rotate(-45deg);
}

.round {
  position: relative;
}

.round label {
  background-color: #fff;
  border: 1px solid #ccc;
  border-radius: 50%;
  cursor: pointer;
  height: 28px;
  left: 0;
  position: absolute;
  top: 5px;
  width: 28px;
}

.round h3 {
  margin-right: auto;
}

.round label:after {
  border: 2px solid #fff;
  border-top: none;
  border-right: none;
  content: "";
  height: 6px;
  left: 7px;
  opacity: 0;
  position: absolute;
  top: 8px;
  transform: rotate(-45deg);
  width: 12px;
}

.round input[type="checkbox"] {
  visibility: hidden;
}

.round input[type="checkbox"]:checked + label {
  background-color: #66bb6a;
  border-color: #66bb6a;
}

.round input[type="checkbox"]:checked + label:after {
  opacity: 1;
}
</style>
