<template>
  <!-- <div id="app"> -->
  <b-navbar toggleable="lg" variant="faded" type="light">
    <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>
    <b-navbar-brand href="#">iPets</b-navbar-brand>

    <!-- <b-collapse id="nav-collapse" is-nav>
      <b-navbar-nav>
        <b-nav-item href="#/about" v-if="!isLogIn">關於我們</b-nav-item>
        <b-nav-item href="#/dogScience" v-if="!isLogIn">狗狗科普</b-nav-item>
        <b-nav-item href="#/dogInfo" v-if="!isLogIn">人與狗的關係</b-nav-item>
        <b-nav-item href="#/contact" v-if="!isLogIn">聯繫我們</b-nav-item>
      </b-navbar-nav>
    </b-collapse> -->

    <!-- Right aligned nav items -->
    <b-navbar-nav class="ml-auto">
      <b-nav-item href="#/about" v-if="!isLogIn">關於我們</b-nav-item>
      <b-nav-item href="#/dogScience" v-if="!isLogIn">狗狗科普</b-nav-item>
      <b-nav-item href="#/dogInfo" v-if="!isLogIn">人與狗的關係</b-nav-item>
      <b-nav-item href="#/contact" v-if="!isLogIn">聯繫我們</b-nav-item>
      <b-nav-form>
        <b-nav-item href="#/login" v-if="!isLogIn"
          ><i class="fas fa-sign-in-alt" style="size:12px"></i>登入</b-nav-item
        >
        <b-nav-item href="#/register" v-if="!isLogIn"
          ><i class="far fa-user" style="size:12px"></i>註冊</b-nav-item
        >
        <b-form-input
          size="sm"
          class="mr-sm-2"
          placeholder="Search"
        ></b-form-input>
        <b-button size="sm" class="my-2 my-sm-0" type="submit">Search</b-button>
      </b-nav-form>

      <!-- <b-nav-item-dropdown text="Lang" right>
        <b-dropdown-item href="#">EN</b-dropdown-item>
        <b-dropdown-item href="#">ES</b-dropdown-item>
        <b-dropdown-item href="#">RU</b-dropdown-item>
        <b-dropdown-item href="#">FA</b-dropdown-item>
      </b-nav-item-dropdown> -->
    </b-navbar-nav>
  </b-navbar>
  <!--
    <div id="content">
    <nav class="navbar navbar-expand-lg navbar-light" style="background-color: #e3f2fd;">
      <a class="navbar-brand" href="#/">iPets</a>
      <button
        class="navbar-toggler"
        type="button"
        data-toggle="collapse"
        data-target="#navbarSupportedContent"
        aria-controls="navbarSupportedContent"
        aria-expanded="false"
        aria-label="Toggle navigation"
      >
        <span class="navbar-toggler-icon"></span>
      </button>

      <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
          <li class="nav-item">
            <a class="nav-link" href="#/about" v-if="!isLogIn">關於我們</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="#/dogScience" v-if="!isLogIn">狗狗科普</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="#/dogInfo" v-if="!isLogIn">人與狗的關係</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="#/contact" v-if="!isLogIn">聯繫我們</a>
          </li>
        </ul>

        <div class="collapse navbar-collapse" id="navbarSupportedContent">
          <ul class="navbar-nav mr-auto">
            <li class="nav-item">
              <a class="nav-link" href="#/homeLogin" v-if="isLogIn">行事曆</a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="#/" v-if="isLogIn">相簿</a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="#/" v-if="isLogIn">會員專區</a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="#/" v-if="isLogIn">設定</a>
            </li>
          </ul>
        </div>
        <BUtton class="btn my-2 my-sm-0" v-if="isLogIn" @click="logout">
          <i class="fas fa-sign-out-alt"></i>
          登出
        </BUtton>
        <ul class="navbar-nav mr-5 mt-2 mt-lg-0" v-if="isLogIn">
          <li class="nav-item">
            <a class="nav-link" @click="logout">
              <i class="fas fa-sign-in-alt" style="size:12px"></i>登出
            </a>
          </li>
        </ul>

        <form class="form-inline my-2 my-lg-0">
          <ul class="navbar-nav my-2 my-sm-0" v-if="!isLogIn">
            <li class="nav-item">
              <a class="nav-link" href="#/login">
                <i class="fas fa-sign-in-alt" style="size:12px"></i>登入
              </a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="#/register">
                <i class="far fa-user" style="size:12px"></i>註冊
              </a>
            </li>
          </ul>
          <input
            class="form-control mr-sm-2"
            type="search"
            placeholder="Search"
            aria-label="Search"
          >
          <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
        </form>
      </div>
    </nav>
    <div id="v-content" v-bind:style="{minHeight: Height+'px'}"><router-view/></div>
    <div id="footer">
      <div class="row">
        <div class="col-12 text-center h-50">
          <p>© 台北商業大學 資訊管理系 版權所有</p>
        </div>
      </div>
    </div>
  </div>
  </div> -->
</template>

<script>
export default {
  name: "Navbar",
  data() {
    return {};
  }
};
</script>
