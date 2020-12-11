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
        password: doc.data().password,
        photoURL: doc.data().photoURL
      });
      console.log("[Vuex] Finish GET User Profile");
    });
}

async function getUserPetsList(store, uid) {
  const petsList = [];
  var status = await fStore
    .collection("users")
    .doc(uid)
    .collection("pets")
    .get()
    .then(function(snapshot) {
      snapshot.forEach(function(doc) {
        petsList.push({ id: doc.id, name: doc.data().petName });
      });
      store.commit("getUserPetsList", {
        pets_list: petsList
      });
      store.commit("UpdateCurrentPet", {
        current_pet_id: petsList[0].id,
        current_pet_name: petsList[0].name
      });
      console.log("[Vuex] Finish GET Pets List");
      return 1;
    })
    .catch(() => {
      console.log("[Vuex] No pet yet");
      return 0;
    });
  return status;
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
            if (now.getDate() <= new Date(start).getDate()) {
              events.push({
                name: name,
                start: start,
                end: end
              });
            }
          } else {
            // 連續性活動
            return null;
            // console.log(name);
          }
        }
        // Sort events by start
      });
      store.commit("updateMonthCalendar", {
        month_calendar: events
      });
      console.log("[Vuex] Finish Month Calendar");
    });
}

export { updateUserProfile, getUserPetsList, updateMonthCalendar };
