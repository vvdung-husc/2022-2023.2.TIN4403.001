var express = require('express');
var bodyParser = require('body-parser');

var async   = require('async');
var LTDB    = require('./_database/db_ltdd');
var USER    = require('./models/user');

var app = express();

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));

//console.log("HELLO NODEJS")
//sử dụng để kiểm tra API có đang hoạt động
app.get("/", function (req, res) {
  res.status(200).send("Welcome to RESTFUL API - NODEJS - TINK43");
});

//hàm đăng nhập - nhận thông tin tài khoản từ Android App
app.post("/login", function (req, res) {
  var user = req.body.username;
  var pass = req.body.password;	
  console.log("ACCOUNT:",user, "/",pass );
  login(user,pass,res);

  // if (user == "vvdung" && pass == "13572468" )
  //   res.status(200).send("API LOGIN - THANH CONG");
  // else
  //   res.status(503).send("API LOGIN - LOI TAI KHOAN");
});

//hàm đăng ký tài khoản
app.post("/register", function (req, res) {
  var user = req.body.username;
  var pass = req.body.password;	
  var name = req.body.fullname;
  var oUser = {
    username: user,
    password: pass,
    fullname: name
  }
  console.log("ACCOUNT:",user, "/",pass );
  register(oUser,res);
  //res.status(200).send("API REGISTER - POST");
});

//hàm nhận thông tin tài khoản sau khi đã đăng nhập thành công
app.post("/userinfo", function (req, res) {
  res.status(200).send("API USERINFO - POST");
});


initDatabase(function (err,result){
  if (err) process.exit(1);
  //dịch vụ WebService chạy tại cổng số n
  var server = app.listen(4380, function () {
      console.log("app running on port.", server.address().port);
  });    
});

function initDatabase(callback) {
  async.waterfall([
    function (next) {
      LTDB.Init(next);
    }
  ], callback);
}
//hàm đăng nhập - kiểm tra trong database
function login(user,pass,res){
  var oDataRet = [];
  async.waterfall([
    function (next) {
      USER.Authentication(user,pass,next);
    },    
    function (oUser,next) {
      oDataRet = oUser;
      next(null,oDataRet);
    }
  ],function (err, result) {
    if (err) {
      var oResult = {};
      oResult.r = -1;
      oResult.m = result;
      return res.status(302).json(oResult);      
    }
    else {
      var oResult = {};
      oResult.r = 0;
      oResult.m = oDataRet;
      return res.status(200).json(oResult);
    }
  });   
}

function register(oUser,res){
  async.waterfall([
    function (next) {
      USER.registerUser(oUser,next);
    }
  ],function (err, result) {
    if (err) {
      var oResult = {};
      oResult.r = -1;
      oResult.m = result;
      return res.status(302).json(oResult);      
    }
    else {
      var oResult = {};
      oResult.r = 0;
      oResult.m = "Tạo tài khoản thành công!";
      return res.status(200).json(oResult);
    }
  });  
}