var mongoose    = require('mongoose');
var async       = require('async');

var userSchema = mongoose.Schema({
  username      : String,
  password      : String,
  fullname      : String,
}, {versionKey: false});

userSchema.index({username: 1 });

var User = new CUser();
module.exports = User;

var _UserModel = null;

function CUser() {

}

CUser.prototype.setDatabase = function (_client) {  
  _UserModel = _client.model('users', userSchema, 'users');
}
CUser.prototype.getByUsername = function (username_,callback) {
  _UserModel.findOne({username:username_}).exec(callback);
}
CUser.prototype.getUsers = function (callback) {
  _UserModel.find({}).exec(callback);
}
CUser.prototype.insertUsers = async function (username_,password_,fullname_) {
  var user = new _UserModel({username: username_, password: password_, fullname: fullname_});
  user.save();
}

CUser.prototype.Authentication = function (username_,password_,callback) {
  async.waterfall([
    function (next) {      
      User.getByUsername(username_,next);      
    },
    function (oUser,next) {
      if (!oUser) return next(-1,'Tài khoản không tồn tại');
      if (!(oUser.password == password_) ) return next(-2,'Mật khẩu không chính xác');
      next(null, oUser);
    }
  ],callback);
}
CUser.prototype.checkUser = function (username_,password_,fullname_,callback) {
  async.waterfall([
    function (next) {      
      //Log
      console.log(username_, password_);
      User.getByUsername(username_,next);
      //User.insertUsers(username_,password_,fullname_,next);      
    },
    function (oUser,next) {
      if (oUser) return next(-1,'Tài khoản đã tồn tại');
      next(null, oUser);
    }
  ],callback);
}
