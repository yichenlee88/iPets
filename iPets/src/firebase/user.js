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

export { updateUserProfile };
