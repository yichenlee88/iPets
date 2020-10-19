<template>
  <div class="center">
    <b-row>
      <b-col sm="4" md="6">
        <b-list-group>
          <b-list-group-item href="#">Cras justo odio</b-list-group-item>
          <b-list-group-item href="#"
            >Dapibus ac facilisis in</b-list-group-item
          >
          <b-list-group-item href="#">Vestibulum at eros</b-list-group-item>
        </b-list-group></b-col
      >
      <b-col sm="8" offset-sm="2" md="6" offset-md="0"
        >sm="5" offset-sm="2" md="6" offset-md="0"</b-col
      >
    </b-row>
  </div>
</template>

<script>
export default {
  name: "Setting"
};
</script>

<style scoped>
.center {
  display: block;
  margin-left: auto;
  margin-right: auto;
  margin-top: 30px;
  width: 70%;
}
</style>
