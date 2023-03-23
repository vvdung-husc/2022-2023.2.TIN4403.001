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
