import { db } from "../db";

const fStore = db.firestore();

async function updateSinglePetInfo(store, uid, petID) {
  await fStore
    .collection("users")
    .doc(uid)
    .collection("pets")
    .doc(petID)
    .get()
    .then(function(doc) {
      store.commit("updateSinglePetInfo", {
        pet: doc.data()
      });
      console.log("[Vuex] Finish GET Single Pet Information");
    });
}

export { updateSinglePetInfo };
