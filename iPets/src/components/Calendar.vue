<template>
  <v-app>
    <v-main>
      <v-row class="fill-height">
        <v-col>
          <v-sheet height="64">
            <v-toolbar flat color="white">
              <v-btn color="primary" dark @click="dialog = true">New Event</v-btn>
              <v-btn outlined class="mr-4" @click="setToday">Today</v-btn>
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
                    <v-list-item-title>Day</v-list-item-title>
                  </v-list-item>
                  <v-list-item @click="type = 'week'">
                    <v-list-item-title>Week</v-list-item-title>
                  </v-list-item>
                  <v-list-item @click="type = 'month'">
                    <v-list-item-title>Month</v-list-item-title>
                  </v-list-item>
                  <v-list-item @click="type = '4day'">
                    <v-list-item-title>4 days</v-list-item-title>
                  </v-list-item>
                </v-list>
              </v-menu>
            </v-toolbar>
          </v-sheet>

          <v-dialog v-model="dialog" max-width="500">
            <v-card>
              <v-container>
                <v-form @submit.prevent="addEvent">
                  <v-text-field v-model="name" type="text" label="event name (required)"></v-text-field>
                  <v-text-field v-model="details" type="text" label="detail"></v-text-field>
                  <v-text-field v-model="start" type="date" label="start (required)"></v-text-field>
                  <v-text-field v-model="end" type="date" label="end (required)"></v-text-field>choose event's color
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
                    @click.stop="dialog = false"
                  >create event</v-btn>
                </v-form>
              </v-container>
            </v-card>
          </v-dialog>

          <v-dialog v-model="dialogDate" max-width="500">
            <v-card>
              <v-container>
                <v-form @submit.prevent="addEvent">
                  <h4 style="text-align: center">新增事件</h4>
                  <v-text-field v-model="name" type="text" label="event name (required)"></v-text-field>
                  <v-text-field v-model="details" type="text" label="detail"></v-text-field>
                  <v-text-field v-model="start" type="date" label="start (required)"></v-text-field>
                  <v-text-field v-model="end" type="date" label="end (required)"></v-text-field>choose event's color
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
                    @click.stop="dialog = false"
                  >create event</v-btn>
                </v-form>
              </v-container>
            </v-card>
          </v-dialog>

          <v-sheet height="600">
            <v-calendar
              ref="calendar"
              v-model="focus"
              color="primary"
              :events="events"
              :event-color="getEventColor"
              :event-margin-bottom="3"
              :now="today"
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
                      placeholder="title"
                    ></textarea-autosize>
                    <textarea-autosize
                      v-model="selectedEvent.details"
                      type="text"
                      style="width: 100%"
                      :min-height="50"
                      placeholder="add note"
                    ></textarea-autosize>
                    <v-select v-model="color" :v-for="color in colors" :items="colors"></v-select>
                    <v-radio-group v-model="frequency">
                      <v-radio label="不重複"></v-radio>
                      <v-radio label="每日"></v-radio>
                      <v-radio label="每週"></v-radio>
                      <v-radio label="每月"></v-radio>
                      <v-radio label="每年"></v-radio>
                    </v-radio-group>
                  </form>
                </v-card-text>

                <v-card-actions>
                  <v-btn text color="secondary" @click="selectedOpen = false">close</v-btn>
                  <v-btn
                    v-if="currentlyEditing !== selectedEvent.id"
                    text
                    @click.prevent="editEvent(selectedEvent)"
                  >edit</v-btn>
                  <v-btn
                    text
                    v-else
                    type="submit"
                    @click.prevent="updateEvent(selectedEvent.id)"
                  >Save</v-btn>
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

const fStore = db.firestore();

export default {
  data() {
    return {
      today: new Date().toISOString().substr(0, 10),
      focus: new Date().toISOString().substr(0, 10),
      type: "month",
      typeToLabel: {
        month: "Month",
        week: "Week",
        day: "Day",
        "4day": "4 Days"
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
      start: null,
      end: null,
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
      const startMonth = this.monthFormatter(start);
      const endMonth = this.monthFormatter(end);
      const suffixMonth = startMonth === endMonth ? "" : endMonth;
      const startYear = start.year;
      const endYear = end.year;
      const suffixYear = startYear === endYear ? "" : endYear;
      const startDay = start.day + this.nth(start.day);
      const endDay = end.day + this.nth(end.day);
      switch (this.type) {
        case "month":
          return `${startMonth} ${startYear}`;
        case "week":
        case "4day":
          return `${startMonth} ${startDay} ${startYear} - ${suffixMonth} ${endDay} ${suffixYear}`;
        case "day":
          return `${startMonth} ${startDay} ${startYear}`;
      }
      return "";
    },
    monthFormatter() {
      return this.$refs.calendar.getFormatter({
        timeZone: "UTC",
        month: "long"
      });
    }
  },
  methods: {
    async getEvents() {
      let snapshot = await fStore
        .collection("pets")
        .doc("3heOY1mUC6wCbo2jdE9M")
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
      this.start = date;
      this.end = date;
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
      if (this.name && this.start && this.end) {
        if (this.frequency === 1) {
          for (let i = 1; i < 5; i++) {
            //   if (
            //     parseInt(this.start.substr(5, 2)) ===
            //       (1 || 3 || 5 || 7 || 8 || 10 || 12) &&
            //     parseInt(this.start.substr(8, 2)) + i > 31
            //   ) {
            //     this.st =
            //       this.start.substr(0, 5) +
            //       parseInt(this.start.substr(5, 2) + 1).toString() +
            //       "-" +
            //       (parseInt(this.start.substr(8, 2)) + i - 31).toString();
            //     this.en =
            //       this.end.substr(0, 5) +
            //       parseInt(this.end.substr(5, 2) + 1).toString() +
            //       "-" +
            //       (parseInt(this.end.substr(8, 2)) + i - 31).toString();
            //   } else if (
            //     parseInt(this.start.substr(5, 2)) === (2 || 4 || 6 || 9 || 11) &&
            //     parseInt(this.start.substr(8, 2)) + i > 30
            //   ) {
            //     this.st =
            //       this.start.substr(0, 5) +
            //       parseInt(this.start.substr(5, 2) + 1).toString() +
            //       "-" +
            //       (parseInt(this.start.substr(8, 2)) + i - 30).toString();
            //     this.en =
            //       this.end.substr(0, 5) +
            //       parseInt(this.end.substr(5, 2) + 1).toString() +
            //       "-" +
            //       (parseInt(this.end.substr(8, 2)) + i - 30).toString();
            //   } else {
            this.st =
              this.start.substr(0, 8) +
              (parseInt(this.start.substr(8, 2)) + i).toString();
            this.en =
              this.end.substr(0, 8) +
              (parseInt(this.end.substr(8, 2)) + i).toString();
            // }
            await fStore
              .collection("pets")
              .doc("3heOY1mUC6wCbo2jdE9M")
              .collection("calEvent")
              .add({
                name: this.name,
                details: this.details,
                frequency: this.frequency,
                start: this.st,
                end: this.en,
                color: this.color
              });
            this.getEvents();
          }
        } else if (this.frequency === 2) {
          for (let i = 1; i < 4; i++) {
            this.st =
              this.start.substr(0, 8) +
              (parseInt(this.start.substr(8, 2)) + i * 7).toString();
            this.en =
              this.end.substr(0, 8) +
              (parseInt(this.end.substr(8, 2)) + i * 7).toString();
            await fStore
              .collection("pets")
              .doc("3heOY1mUC6wCbo2jdE9M")
              .collection("calEvent")
              .add({
                name: this.name,
                details: this.details,
                frequency: this.frequency,
                start: this.st,
                end: this.en,
                color: this.color
              });
            this.getEvents();
          }
        } else if (this.frequency === 3) {
          for (let i = 1; i < 4; i++) {
            this.st =
              this.start.substr(0, 5) +
              (parseInt(this.start.substr(5, 2)) + i).toString() +
              this.start.substr(7, 3);
            this.en =
              this.end.substr(0, 5) +
              (parseInt(this.end.substr(5, 2)) + i).toString() +
              this.end.substr(7, 3);
            await fStore
              .collection("pets")
              .doc("3heOY1mUC6wCbo2jdE9M")
              .collection("calEvent")
              .add({
                name: this.name,
                details: this.details,
                frequency: this.frequency,
                start: this.st,
                end: this.en,
                color: this.color
              });
            this.getEvents();
          }
        } else if (this.frequency === 4) {
          for (let i = 1; i < 4; i++) {
            this.st =
              (parseInt(this.start.substr(0, 4)) + i).toString() +
              this.start.substr(4, 6);
            this.en =
              (parseInt(this.end.substr(0, 4)) + i).toString() +
              this.end.substr(4, 6);
            await fStore
              .collection("pets")
              .doc("3heOY1mUC6wCbo2jdE9M")
              .collection("calEvent")
              .add({
                name: this.name,
                details: this.details,
                frequency: this.frequency,
                start: this.st,
                end: this.en,
                color: this.color
              });
            this.getEvents();
          }
        }
        await fStore
          .collection("pets")
          .doc("3heOY1mUC6wCbo2jdE9M")
          .collection("calEvent")
          .add({
            name: this.name,
            details: this.details,
            frequency: this.frequency,
            start: this.start,
            end: this.end,
            color: this.color
          });
        this.getEvents();
        this.name = "";
        this.details = "";
        this.frequency = "";
        this.start = "";
        this.end = "";
        this.color = "";
      } else {
        alert("You must enter the event name, start, and end time");
      }
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
      await fStore
        .collection("pets")
        .doc("3heOY1mUC6wCbo2jdE9M")
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
      if (confirm(`確定是否刪除 ${selectedEvent} ?`)) {
        await fStore
          .collection("pets")
          .doc("3heOY1mUC6wCbo2jdE9M")
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
    },
    nth(d) {
      return d > 3 && d < 21
        ? "th"
        : ["th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th"][d % 10];
    }
  }
};
</script>
