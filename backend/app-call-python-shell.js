const express = require("express");
const app = express();
const cors = require("cors");
const multer = require("multer");
let { PythonShell } = require("python-shell");

app.use(function (req, res, next) {
  // Website you wish to allow to connect
  res.setHeader("Access-Control-Allow-Origin", "http://localhost:8080");

  // Request methods you wish to allow
  res.setHeader(
    "Access-Control-Allow-Methods",
    "GET, POST, OPTIONS, PUT, PATCH, DELETE"
  );

  // Request headers you wish to allow
  res.setHeader(
    "Access-Control-Allow-Headers",
    "X-Requested-With,content-type"
  );

  // Set to true if you need the website to include cookies in the requests sent
  // to the API (e.g. in case you use sessions)
  res.setHeader("Access-Control-Allow-Credentials", true);

  // Pass to next layer of middleware
  next();
});

var upload = multer({
  limit: {
    // 限制上傳檔案的大小為 1MB
    fileSize: 1000000,
  },
});

app.get("/", function (req, res) {
  res.send("Hello World!");
});

app.get("/api", function (req, res) {
  res.send("api");
});

app.post("/upload", upload.single("photo"), async (req, res) => {
  let options = {
    mode: "text",
    pythonPath:
      "C:\\Users\\YiChen\\AppData\\Local\\Continuum\\anaconda3\\envs\\tf\\python.exe",
    scriptPath:
      "C:\\Users\\YiChen\\Desktop\\iPets\\backend\\dog_breed_classification",
  };
  let pyshell = new PythonShell("process.py", options);
  pyshell.send(req.file.buffer.toString("hex"), { mode: "text" });
  pyshell.on("message", function (message) {
    message = message.replace(new RegExp('"', "g"), "");
    message = message.replace(new RegExp("\\^", "g"), '"');
    json = JSON.parse(message);
    res.json(json);
  });
  pyshell.end(function (err) {
    if (err) {
      throw err;
    }
  });
});

app.listen(3000, () => {
  console.log("server running on port 3000");
});
