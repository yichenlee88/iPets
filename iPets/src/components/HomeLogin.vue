<template>
  <div class="container">
    <div
      class="accordion md-accordion accordion-blocks"
      id="accordion"
      role="tablist"
      aria-multiselectable="true"
    >
      <div class="card">
        <div class="card-header" role="tab" id="headingUnfiled">
          <div class="text-left">
            <!-- <div class="dropdown float-left">
            <button
              class="btn btn-info dropdown-toggle"
              type="button"
              data-toggle="dropdown"
              aria-haspopup="true"
              aria-expanded="false"
            >
              <i class="fas fa-pencil-alt"></i>
            </button>
            <div class="dropdown-menu dropdown-info">
              <a class="dropdown-item" href="#">新增</a>
              <a class="dropdown-item" href="#">刪除</a>
              <a class="dropdown-item" href="#">修改</a>
            </div> -->
            <div
              data-toggle="collapse"
              data-parent="#accordion"
              href="#collapseUnfiled"
              aria-expanded="true"
              aria-controls="collapseUnfiled"
            >
              <h5 class="mt-1 mb-0">
                <p>洗澡</p>
                <div class="progress">
                  <div
                    class="progress-bar"
                    role="progressbar"
                    style="width: 25%"
                    aria-valuenow="25"
                    aria-valuemin="0"
                    aria-valuemax="100"
                    background-color="#e3f2fd"
                  ></div>
                </div>
              </h5>
            </div>
          </div>
        </div>
        <div class="card-body">
          <div>
            <label for="datepicker-invalid"
              >Choose a date (invalid style)</label
            >
            <b-form-datepicker
              id="datepicker-invalid"
              :state="false"
              class="mb-2"
            ></b-form-datepicker>
            <label for="datepicker-valid">Choose a date (valid style)</label>
            <b-form-datepicker
              id="datepicker-valid"
              :state="true"
            ></b-form-datepicker>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
const now = new Date();
const today = new Date(now.getFullYear(), now.getMonth(), now.getDate());

export default {
  name: "HomeLogin",
  data() {
    // 15th two months prior
    const minDate = new Date(today);
    minDate.setMonth(minDate.getMonth() - 2);
    minDate.setDate(15);
    // 15th in two months
    const maxDate = new Date(today);
    maxDate.setMonth(maxDate.getMonth() + 2);
    maxDate.setDate(15);

    return {
      value: "",
      min: minDate,
      max: maxDate
    };
  }
};
</script>

<style scoped>
.container {
  margin-top: 20px;
  margin-bottom: 20px;
  min-height: 600px;
}
</style>
