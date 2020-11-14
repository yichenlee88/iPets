import { db } from "../db";

const fStore = db.firestore();

async function updatePetInfo(store, uid) {
  var petsRef = fStore
    .collection("users")
    .doc(uid)
    .collection("pets");
  await petsRef.get().then(function(querySnapshot) {
    querySnapshot.forEach(function(doc) {
      store.commit("updatePetInfo", {
        pet: doc.data(),
        pet_doc_id: doc.id
      });
    });
  });
}

export { updatePetInfo };
