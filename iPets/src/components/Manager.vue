<template>
  <div id="manager">
    <div id="content">
      <b-row class="justify-content-md-center" style="margin:20px 0">
        <b-col cols="8" style="margin:0 auto">
          <b-card bg-variant="light">
            <b-row class="my-1" v-for="(type,index) in types" :key="index">
              <b-col sm="3">
                <label :for="`title-${type.title}`">
                  {{ type.title }}:
                </label>
              </b-col>
              <b-col sm="9">
                <b-form-input :id="`type-${type.id}`" :type="type.type"></b-form-input>
              </b-col>
            </b-row>
          </b-card>
        </b-col>
      </b-row>
    </div>
  </div>
</template>

<script>
export default {
  name: "Manager",
  data() {
    return {
      types: [
        {title: "標題", type: "text", id: "title"},
        {title: "副標題", type: "text", id: "subtitle"},
        {title: "日期", type: "date", id: "date"}
      ]
    };
  }
};
</script>
