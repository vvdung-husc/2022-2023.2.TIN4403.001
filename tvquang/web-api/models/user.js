var mongoose    = require('mongoose');
var async       = require('async');

var userSchema = mongoose.Schema({
  username      : String,
  password      : String,
  fullname      : String,
});

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
CUser.prototype.addUser = function (user, callback) {
  console.log(user);
  _UserModel.create(user, function(err, res) {
    if (err) callback(-100,"Lỗi bên trong database");
    else callback(null);
  });  
}

CUser.prototype.registerUser = function (user,callback) {
  var accountData;
  async.waterfall([
    function (next) { 
      if (!user) return next(-1,'Dữ liệu lỗi');
      if (!user.username) return next(-2,'Dữ liệu lỗi - username');
      if (!user.password) return next(-3,'Dữ liệu lỗi - password');
      if (user.username.length < 3) return next(-4,'Dữ liệu lỗi - username tối thiểu 3 ký tự');
      if (user.password.length < 6) return next(-5,'Dữ liệu lỗi - password - tối thiểu 6 ký tự');
      accountData = user;
      User.getByUsername(user.username,next);
    },
    function (oUser,next) {
      if (oUser) return next(-6,'Tài khoản đã tồn tại');
      User.addUser(accountData,next)
    }
  ],callback);
}
//userinfo
CUser.prototype.getUserInfo = function (oUser,callback) {
  async.waterfall([
    function (next) {
      User.getByUsername(oUser.username,next);
    }
  ],callback);
}
