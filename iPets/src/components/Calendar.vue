<template>
  <v-app>
    <v-main>
      <v-row class="fill-height">
        <v-col>
          <v-sheet height="64">
            <v-toolbar flat color="white">
              <v-btn color="primary" dark @click="dialog = true">新增事件</v-btn>
              <v-btn outlined class="mr-4" @click="setToday">今天</v-btn>
              <v-btn fab text small @click="prev">
                <v-icon small>mdi-chevron-left</v-icon>
              </v-btn>
              <v-btn fab text small @click="next">
                <v-icon small>mdi-chevron-right</v-icon>
              </v-btn>
              <v-toolbar-title>{{ title }}</v-toolbar-title>
              <div class="flex-grow-1"></div>
              <v-menu bottom right>
                <template v-slot:activator="{ on }">
                  <v-btn outlined v-on="on">
                    <span>{{ typeToLabel[type] }}</span>
                    <v-icon right>mdi-menu-down</v-icon>
                  </v-btn>
                </template>
                <v-list>
                  <v-list-item @click="type = 'day'">
                    <v-list-item-title>日</v-list-item-title>
                  </v-list-item>
                  <v-list-item @click="type = 'week'">
                    <v-list-item-title>週</v-list-item-title>
                  </v-list-item>
                  <v-list-item @click="type = 'month'">
                    <v-list-item-title>月</v-list-item-title>
                  </v-list-item>
                  <v-list-item @click="type = '4day'">
                    <v-list-item-title>四天</v-list-item-title>
                  </v-list-item>
                </v-list>
              </v-menu>
            </v-toolbar>
          </v-sheet>

          <v-dialog v-model="dialog" max-width="500">
            <v-card>
              <v-container>
                <v-form @submit.prevent="addEvent">
                  <v-text-field v-model="name" type="text" label="事件名稱"></v-text-field>
                  <v-text-field v-model="details" type="text" label="描述"></v-text-field>
                  <v-text-field v-model="start" type="date" label="開始日期 (必須)"></v-text-field>
                  <v-text-field v-model="end" type="date" label="結束日期 (必須)"></v-text-field>選擇顏色
                  <v-select v-model="color" :v-for="color in colors" :items="colors"></v-select>
                  <v-radio-group v-model="frequency">
                    <v-radio label="不重複"></v-radio>
                    <v-radio label="每日"></v-radio>
                    <v-radio label="每週"></v-radio>
                    <v-radio label="每月"></v-radio>
                    <v-radio label="每年"></v-radio>
                  </v-radio-group>
                  <v-btn
                    type="submit"
                    color="primary"
                    class="mr-4"
                    @click.stop="
                      dialog = false;
                      closeDialog();
                    "
                  >建立事件</v-btn>
                </v-form>
              </v-container>
            </v-card>
          </v-dialog>

          <v-dialog v-model="dialogDate" max-width="500">
            <v-card>
              <v-container>
                <v-form @submit.prevent="addEvent">
                  <h4 style="text-align: center">新增事件</h4>
                  <v-text-field v-model="name" type="text" label="事件名稱"></v-text-field>
                  <v-text-field v-model="details" type="text" label="描述"></v-text-field>
                  <v-text-field v-model="startTitle" type="date" label="開始日期 (必須)"></v-text-field>
                  <v-text-field v-model="endTitle" type="date" label="結束日期 (必須)"></v-text-field>選擇顏色
                  <v-select v-model="color" :v-for="color in colors" :items="colors"></v-select>
                  <v-radio-group v-model="frequency">
                    <v-radio label="不重複"></v-radio>
                    <v-radio label="每日"></v-radio>
                    <v-radio label="每週"></v-radio>
                    <v-radio label="每月"></v-radio>
                    <v-radio label="每年"></v-radio>
                  </v-radio-group>
                  <v-btn
                    type="submit"
                    color="primary"
                    class="mr-4"
                    @click.stop="
                      dialog = false;
                      closeDialog();
                    "
                  >建立事件</v-btn>
                </v-form>
              </v-container>
            </v-card>
          </v-dialog>

          <v-sheet height="600">
            <v-calendar
              ref="calendar"
              v-model="focus"
              color="primary"
              locale="ch"
              :events="events"
              :event-color="getEventColor"
              :event-margin-bottom="3"
              :now="focus"
              :type="type"
              @click:event="showEvent"
              @click:more="viewDay"
              @click:date="setDialogDate"
              @change="updateRange"
            ></v-calendar>
            <v-menu
              v-model="selectedOpen"
              :close-on-content-click="false"
              :activator="selectedElement"
              min-width="400"
              offset-x
            >
              <v-card color="grey lighten-4" flat>
                <v-toolbar :color="selectedEvent.color" dark>
                  <v-btn @click="deleteEvent(selectedEvent.id)" icon>
                    <v-icon>mdi-delete</v-icon>
                  </v-btn>
                  <v-toolbar-title v-html="selectedEvent.name"></v-toolbar-title>
                  <div class="flex-grow-1"></div>
                </v-toolbar>

                <v-card-text>
                  <form v-if="currentlyEditing !== selectedEvent.id">{{ selectedEvent.details }}</form>
                  <form v-else>
                    <p>title</p>
                    <textarea-autosize
                      v-model="selectedEvent.name"
                      type="text"
                      style="width: 100%"
                      placeholder="標題"
                    ></textarea-autosize>
                    <textarea-autosize
                      v-model="selectedEvent.details"
                      type="text"
                      style="width: 100%"
                      :min-height="50"
                      placeholder="描述"
                    ></textarea-autosize>
                    <v-select v-model="color" :v-for="color in colors" :items="colors"></v-select>
                  </form>
                </v-card-text>

                <v-card-actions>
                  <v-btn text color="secondary" @click="selectedOpen = false">關閉</v-btn>
                  <v-btn
                    v-if="currentlyEditing !== selectedEvent.id"
                    text
                    @click.prevent="editEvent(selectedEvent)"
                  >編輯</v-btn>
                  <v-btn text v-else type="submit" @click.prevent="updateEvent(selectedEvent.id)">儲存</v-btn>
                </v-card-actions>
              </v-card>
            </v-menu>
          </v-sheet>
        </v-col>
      </v-row>
    </v-main>
  </v-app>
</template>

<script>
import { db } from "../db";
import firebase from "firebase";

const fStore = db.firestore();

export default {
  data() {
    return {
      today: new Date(),
      focus: new Date().toISOString().slice(0, 10),
      start: new Date(),
      // end: new Date().toISOString().slice(0, 10),
      end: new Date(),
      startTitle: null,
      endTitle: null,
      date: new Date(),
      type: "month",
      typeToLabel: {
        month: "月",
        week: "週",
        day: "日",
        "4day": "四天"
      },
      colors: [
        { text: "red" },
        { text: "green" },
        { text: "blue" },
        { text: "purple" },
        { text: "orange" },
        { text: "pink" },
        { text: "brown" }
      ],
      frequency: 0,
      name: null,
      details: null,
      color: "red", // default event color
      currentlyEditing: null,
      selectedEvent: {},
      selectedElement: null,
      selectedOpen: false,
      events: [],
      dialog: false,
      dialogDate: false
    };
  },
  mounted() {
    this.getEvents();
  },
  computed: {
    title() {
      const { start, end } = this;
      if (!start || !end) {
        return "";
      }
      const startMonth =
        typeof start === "string" ? start.slice(5, 7) : start.month;
      const endMonth = end.month;
      const suffixMonth = startMonth === endMonth ? startMonth : endMonth;
      const startYear =
        typeof start === "string" ? start.slice(0, 4) : start.year;
      const endYear = end.year;
      const suffixYear = startYear === endYear ? startYear : endYear;
      const startDay = start.day;
      const endDay = end.day;
      switch (this.type) {
        case "month":
          return `${startYear} 年 ${startMonth} 月`;
        case "week":
          return `${startYear} 年 ${startMonth} 月 ${startDay} 日 - ${suffixYear} 年 ${suffixMonth} 月 ${endDay} 日`;
        case "4day":
          return `${startYear} 年 ${startMonth} 月 ${startDay} 日 - ${suffixYear} 年 ${suffixMonth} 月 ${endDay -
            1} 日`;
        case "day":
          return `${startYear} 年 ${startMonth} 月 ${startDay} 日`;
      }
      return "";
    }
  },
  methods: {
    add: function(newdate, endDate) {
      let uid = firebase.auth().currentUser.uid;
      fStore
        .collection("users")
        .doc(uid)
        .collection("calEvent")
        .add({
          name: this.name,
          details: this.details,
          frequency: this.frequency,
          start: newdate,
          end: endDate,
          color: this.color
        });
      this.getEvents();
    },
    async getEvents() {
      let uid = firebase.auth().currentUser.uid;
      let snapshot = await fStore
        .collection("users")
        .doc(uid)
        .collection("calEvent")
        .get();
      const events = [];
      snapshot.forEach(doc => {
        let appData = doc.data();
        appData.id = doc.id;
        events.push(appData);
      });
      this.events = events;
    },
    setDialogDate({ date }) {
      this.dialogDate = true;
      this.focus = date;
      this.startTitle = date;
      this.endTitle = date;
    },
    closeDialog() {
      this.dialogDate = false;
    },
    viewDay({ date }) {
      this.focus = date;
      this.type = "day";
    },
    getEventColor(event) {
      return event.color;
    },
    setToday() {
      this.focus = this.today;
    },
    prev() {
      this.$refs.calendar.prev();
    },
    next() {
      this.$refs.calendar.next();
    },
    async addEvent() {
      if (this.name && this.startTitle && this.endTitle) {
        if (this.frequency === 1) {
          this.startTitle = new Date(this.startTitle);
          this.endTitle = new Date(this.endTitle);
          for (let i = 0; i < 5; i++) {
            let newdate = new Date(this.startTitle);
            let endDate = new Date(this.endTitle);
            newdate.setDate(this.startTitle.getDate() + i);
            endDate.setDate(this.endTitle.getDate() + i);
            newdate = newdate.toISOString().slice(0, 10);
            endDate = endDate.toISOString().slice(0, 10);
            this.add(newdate, endDate);
          }
        } else if (this.frequency === 2) {
          this.startTitle = new Date(this.startTitle);
          this.endTitle = new Date(this.endTitle);
          for (let i = 0; i < 5; i++) {
            let newdate = new Date(this.startTitle);
            let endDate = new Date(this.endTitle);
            newdate.setDate(this.startTitle.getDate() + i * 7);
            endDate.setDate(this.endTitle.getDate() + i * 7);
            newdate = newdate.toISOString().slice(0, 10);
            endDate = endDate.toISOString().slice(0, 10);
            this.add(newdate, endDate);
          }
        } else if (this.frequency === 3) {
          this.startTitle = new Date(this.startTitle);
          this.endTitle = new Date(this.endTitle);
          for (let i = 0; i < 5; i++) {
            let newdate = new Date(this.startTitle);
            let endDate = new Date(this.endTitle);
            newdate.setMonth(this.startTitle.getMonth() + i);
            endDate.setMonth(this.endTitle.getMonth() + i);
            newdate = newdate.toISOString().slice(0, 10);
            endDate = endDate.toISOString().slice(0, 10);
            this.add(newdate, endDate);
          }
        } else if (this.frequency === 4) {
          this.startTitle = new Date(this.startTitle);
          this.endTitle = new Date(this.endTitle);
          for (let i = 0; i < 2; i++) {
            let newdate = new Date(this.startTitle);
            let endDate = new Date(this.endTitle);
            newdate.setFullYear(this.startTitle.getFullYear() + i);
            endDate.setFullYear(this.endTitle.getFullYear() + i);
            newdate = newdate.toISOString().slice(0, 10);
            endDate = endDate.toISOString().slice(0, 10);
            this.add(newdate, endDate);
          }
        } else if (this.frequency === 0) {
          let newdate = new Date(this.startTitle);
          let endDate = new Date(this.endTitle);
          newdate = newdate.toISOString().slice(0, 10);
          endDate = endDate.toISOString().slice(0, 10);
          this.add(newdate, endDate);
        }
      }
      this.name = "";
      this.details = "";
      this.startTitle = "";
      this.endTitle = "";
    },
    editEvent(ev) {
      this.currentlyEditing = ev.id;
      this.name = ev.name;
      this.start = ev.start;
      this.frequency = ev.frequency;
      this.end = ev.end;
      this.color = ev.color;
    },
    async updateEvent(ev) {
      let selectedEvent = this.selectedEvent;
      let uid = firebase.auth().currentUser.uid;
      await fStore
        .collection("users")
        .doc(uid)
        .collection("calEvent")
        .doc(ev)
        .update({
          details: selectedEvent.details,
          name: selectedEvent.name,
          color: this.color,
          frequency: this.frequency,
          start: this.start,
          end: this.end
        })
        .then(res => {
          console.log("update completed");
        })
        .catch(err => {
          console.log(err);
        });
      this.selectedOpen = false;
      this.currentlyEditing = null;
      console.log(ev);
      location.reload();
    },
    async deleteEvent(ev) {
      let selectedEvent = this.selectedEvent.name;
      let uid = firebase.auth().currentUser.uid;
      if (confirm(`確定是否刪除 ${selectedEvent} ?`)) {
        await fStore
          .collection("users")
          .doc(uid)
          .collection("calEvent")
          .doc(ev)
          .delete()
          .then(res => {
            console.log(res);
          })
          .catch(err => {
            console.log(err);
          });
        this.selectedOpen = false;
        this.getEvents();
      }
    },
    showEvent({ nativeEvent, event }) {
      const open = () => {
        this.selectedEvent = event;
        this.selectedElement = nativeEvent.target;
        setTimeout(() => (this.selectedOpen = true), 10);
      };
      if (this.selectedOpen) {
        this.selectedOpen = false;
        setTimeout(open, 10);
      } else {
        open();
      }
      nativeEvent.stopPropagation();
    },
    updateRange({ start, end }) {
      this.start = start;
      this.end = end;
    }
  }
};
</script>
