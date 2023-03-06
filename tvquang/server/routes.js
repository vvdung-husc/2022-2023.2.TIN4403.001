var async   = require('async');
var USER    = require('./models/user');

var appRouter = function (app) {

  app.get("/", function (req, res) {
    res.status(200).send("Welcome to RESTFUL API - NODEJS - TINK43");
  });

  app.get("/users", function (req, res) {
    getUsers(res)
    //res.status(200).send("Welcome to RESTFUL API - NODEJS - TINK43");
  });

  app.post("/userinfo", function (req, res) {
    res.status(200).send("USERINFO API");
  });
  
  app.post("/login", function (req, res) {
		var user = req.body.username;
    var pass = req.body.password;	
    login(user,pass,res);
    // if (user == "vvdung")
    // 	res.status(200).send("HÀM ĐĂNG NHẬP [" + user + "/" + pass +"]");
	  // else		
		// 	res.status(200).send("LOGIN API [" + user + "/" + pass +"]");
  });
  
  app.post("/register", function (req, res) {
    res.status(200).send("REGISTER API");
  });

}

module.exports = appRouter;

function getUsers(res){
  var oDataRet = [];
  async.waterfall([
    function (next) {
      USER.getUsers(next);
    },    
    function (oUsers,next) {
      oDataRet = oUsers;      
      next(null,oDataRet);
    }
  ],function (err, result) {
    if (err) {
      var oResult = {};
      oResult.r = -1;
      oResult.m = "Có lỗi";
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