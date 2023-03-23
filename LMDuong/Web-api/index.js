var express = require('express');//web sever
var bodyParser = require('body-parser');//thư viện

var app = express();//đặt biến bằng express
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));

//sử dụng để kiểm tra API có đang hoạt động
app.get("/", function (req, res) {
    res.status(200).send("Welcome to RESTFUL API - NODEJS - TINK43");
});

app.post("/login", function (req, res) {
    var user = req.body.username;
    var pass = req.body.password;	
    console.log("Account:",user, "/",pass );
    res.status(200).send("API Login - Get");
});

app.post("/register", function (req, res) {
    res.status(200).send("API Register - Get");
});

app.post("/useinfo", function (req, res) {
    res.status(200).send("API Useinfo - Get");
});

var server = app.listen(1405, function () {
    console.log("app running on port.", server.address().port);
});