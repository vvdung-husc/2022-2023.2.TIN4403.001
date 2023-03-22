var express = require('express');
var bodyParser = require('body-parser');

var app = express();

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));


//console.log("HELLO NODEJS")
app.get("/", function (req, res) {
    res.status(200).send("Welcome to RESTFUL API - NODEJS - TINK43");
  });

  app.get("/login", function (req, res) {
    res.status(200).send("Welcome to RESTFUL API - NODEJS - TINK43");
  });

  app.post("/login", function (req, res) {
    var user = req.body.username;
    var pass = req.body.password;	
    console.log("ACCOUNT:",user, "/",pass );
  
   // if (user == "vvdung" && pass == "13572468" )
    //  res.status(200).send("API LOGIN - THANH CONG");
    //else
    // res.status(503).send("API LOGIN - LOI TAI KHOAN");
  });
  
  //hàm đăng ký tài khoản
  app.post("/register", function (req, res) {
    res.status(200).send("API REGISTER - POST");
  });
  
  //hàm nhận thông tin tài khoản sau khi đã đăng nhập thành công
  app.post("/userinfo", function (req, res) {
    res.status(200).send("API USERINFO - POST");
  });

var server = app.listen(4380, function () {
    console.log("app running on port.", server.address().port);
  });  