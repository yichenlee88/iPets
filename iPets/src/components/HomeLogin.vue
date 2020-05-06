<template>
  <div class="container">
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
            <div class="dropdown-menu dropdown-info">
              <a class="dropdown-item" href="#">新增</a>
              <a class="dropdown-item" href="#">刪除</a>
              <a class="dropdown-item" href="#">修改</a>
            </div>
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
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "HomeLogin"
};
</script>

<style scoped>
.container {
  margin-top: 20px;
  margin-bottom: 20px;
}
</style>
