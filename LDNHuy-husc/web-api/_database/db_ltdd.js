var async = require('async');

var DBLTDD = new CDBLTDD();

module.exports = DBLTDD;

var USERS;

function CDBLTDD() {
    this.client = null;
}
CDBLTDD.prototype.Init = function (callback) {
    console.log('Connecting LTDD Database...');
    async.waterfall([
      function (next) {
        // fnConnectDatabase('mongodb://testuser:ltdd%40123@222.255.148.165:9017/LTDD',next);
        fnConnectDatabase('mongodb://127.0.0.1:27017/LTDD',next);        
      },
      function (next) {
        USERS      = require('../models/user');                
        USERS.setDatabase(DBLTDD.client);
        next();
      }
    ], callback);
  }
function fnConnectDatabase(connString,callback) {
    var mongoose = require('mongoose');
    mongoose.Promise = global.Promise;
    var MongoOptions = {
      poolSize: 10,
      useNewUrlParser: true,
      useUnifiedTopology: true,
      useCreateIndex: true,
      useFindAndModify: false
    };
    var conn = mongoose.createConnection(connString,MongoOptions);
    //console.log(conn);
    conn.on('error', function (err) {
      console.log('...MONGO EVENT ERROR');      
      callback(err);
    });
    conn.on('connected', function() {
      if (DBLTDD.client) console.log('...MONGO [' + DBLTDD.client.name + '] Connected!');
    });
    conn.on('disconnected', function() {
      if (DBLTDD.client) console.log('...MONGO [' + DBLTDD.client.name + '] Disconnected!');
    });
    conn.then(function(_db) {
      console.log('...MONGO Actived : [' + _db.name + ']');
      //console.log(conn);
      DBLTDD.client = _db;
      callback(null);  
    });
}