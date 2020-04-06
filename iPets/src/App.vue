<template>
  <div id="app">
    <nav class="navbar navbar-expand-lg navbar-light" style="background-color: #e3f2fd;">
      <a class="navbar-brand" href="#/">iPets</a>
      <div class="collapse navbar-collapse" id="navbarTogglerDemo03">
        <ul class="navbar-nav mr-auto mt-2 mt-lg-0">
          <li class="nav-item">
            <a class="nav-link" href="#/about">關於我們</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="#/">狗狗科普</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="#/dogInfo">人與狗的關係</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="#/contact">聯繫我們</a>
          </li>
        </ul>
        <ul class="navbar-nav mr-5 mt-2 mt-lg-0">
          <li class="nav-item">
            <a class="nav-link" href="#/login">
              <i class="fas fa-sign-in-alt" style="size:12px"></i>登入
            </a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="#">
              <i class="far fa-user" style="size:12px"></i>註冊
            </a>
          </li>
        </ul>
        <form class="form-inline my-2 my-lg-0">
          <input
            class="form-control mr-sm-2"
            type="search"
            placeholder="Search"
            aria-label="Search"
          />
          <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
        </form>
      </div>
    </nav>
    <router-view />
    <div class="row" style="background-color: #e3f2fd;">
      <div class="col-12 text-center h-50">
        <p>© 台北商業大學 資訊管理系 版權所有</p>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'App'
}
</script>

<style>
#app {
  font-family: "Avenir", Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  width: 100%;
  color: #2c3e50;
}
</style>
