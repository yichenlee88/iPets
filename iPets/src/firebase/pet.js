import { db } from "../db";

const fStore = db.firestore();

async function updatePetInfo(store, uid) {
  var petsRef = fStore
    .collection("users")
    .doc(uid)
    .collection("pets");
  petsRef.get().then(function(querySnapshot) {
    querySnapshot.forEach(function(doc) {
      store.commit("updatePetInfo", {
        pet: doc.data(),
        pet_doc_id: doc.id
      });
    });
  });
  updateInfo(store, petsRef);
}

function updateInfo(store, petsRef) {
  var infoRef = petsRef.doc(store.state.pet_doc_id).collection("info");
  var listInfo = [];
  infoRef.get().then(function(querySnapshot) {
    querySnapshot.forEach(function(doc) {
      listInfo.push({
        event_name: doc.id,
        done: doc.data().done,
        next_time: doc.data().next_time,
        period: doc.data().period,
        periodUnit: doc.data().periodUnit
      });
    });
    store.commit("updateInfo", {
      pet_info: listInfo
    });
  });
}

class Info {
  constructor(eventName, period, periodUnit) {
    this.eventName = eventName;
    this.period = parseInt(period, 10);
    this.periodUnit = periodUnit;
  }
  getNext() {
    var today = new Date(Date.now());
    var next = null;
    if (this.periodUnit === "day") {
      next = today.setDate(today.getDate() + this.period);
    } else if (this.periodUnit === "week") {
      next = today.setDate(today.getDate() + this.period * 7);
    } else if (this.periodUnit === "month") {
      next = today.setMonth(today.getMonth() + this.period);
    }
    return new Date(next);
  }
}

export { updatePetInfo, Info };
