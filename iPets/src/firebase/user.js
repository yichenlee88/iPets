import { db } from "../db";

const fStore = db.firestore();

async function updateUserProfile(store, uid) {
  await fStore
    .collection("users")
    .doc(uid)
    .get()
    .then(doc => {
      store.commit("updateUserProfile", {
        uid: uid,
        name: doc.data().name,
        email: doc.data().email,
        photoURL: doc.data().photoURL
      });
    });
}

async function updateMonthCalendar(store, uid) {
  await fStore
    .collection("users")
    .doc(uid)
    .collection("calEvent")
    .get()
    .then(function(querySnapshot) {
      const events = [];
      querySnapshot.forEach(function(doc) {
        var name = doc.data().name;
        var start = doc.data().start;
        var end = doc.data().end;
        var dateSplit = start.split("-");
        var now = new Date();
        // 過濾出當月事件
        if (
          Number(dateSplit[0]) === now.getFullYear() &&
          Number(dateSplit[1]) === now.getMonth() + 1
        ) {
          if (
            // 非連續性活動
            new Date(start).setHours(0, 0, 0, 0) ===
            new Date(end).setHours(0, 0, 0, 0)
          ) {
            events.push({
              name: name,
              start: start,
              end: end
            });
          } else {
            // 連續性活動
            console.log(name);
          }
        }
        // Sort events by start
      });
      store.commit("updateMonthCalendar", {
        month_calendar: events
      });
    });
}

export { updateUserProfile, updateMonthCalendar };
