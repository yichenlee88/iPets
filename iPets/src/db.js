import firebase from "firebase/app";
import "firebase/auth";

const firebaseConfig = {
  apiKey: "AIzaSyBCUNQk85j820SKwa8t_J58YlKybrJcfqY",
  authDomain: "ipets-5fd4f.firebaseapp.com",
  databaseURL: "https://ipets-5fd4f.firebaseio.com",
  projectId: "ipets-5fd4f",
  storageBucket: "ipets-5fd4f.appspot.com",
  messagingSenderId: "692117739680",
  appId: "1:692117739680:web:94b3738cb57db7ff5dd1c5",
  measurementId: "G-P2T7VD2XK3"
};

export const db = firebase.initializeApp(firebaseConfig);
