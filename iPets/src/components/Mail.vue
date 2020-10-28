<template>
  <b-row>
    <b-col col="1" sm="1" md="1"></b-col>
    <b-col col="11" sm="11" md="11">
      <h3>提醒：</h3>
    </b-col>
    <b-col col="1" sm="1" md="1"></b-col>
    <b-col col="11" sm="11" md="11">
      <b-form-checkbox
        id="checkbox-1"
        v-model="status"
        name="checkbox-1"
        value="accepted"
        unchecked-value="not_accepted"
        checked
      >
        意見回饋電子郵件
      </b-form-checkbox>
    </b-col>
    <b-col col="1" sm="1" md="1"></b-col>
    <b-col col="11" sm="11" md="11">
      <b-form-checkbox
        id="checkbox-2"
        v-model="status"
        name="checkbox-2"
        value="accepted"
        unchecked-value="not_accepted"
        check
      >
        提醒電子郵件
      </b-form-checkbox>
    </b-col>
    <b-col col="1" sm="1" md="1"></b-col>
    <b-col col="11" sm="11" md="11">
      <b-form-checkbox
        id="checkbox-3"
        v-model="status"
        name="checkbox-3"
        value="accepted"
        unchecked-value="not_accepted"
        checked
      >
        新消息電子郵件
      </b-form-checkbox>
    </b-col>
  </b-row>
</template>

<script>
export default {
  name: "Mail",
  data() {
    return {
      status: "accepted"
    };
  }
};
</script>
