import 'dart:async';
import 'dart:convert';

import 'package:flutter/foundation.dart';
import 'package:login_app/model/user.dart';
import 'package:http/http.dart' as http;

  //Lay token chuyen ve class
  Map<String, dynamic> parseToken(String responseBody) {
    final Map<String, dynamic> parsed = jsonDecode(responseBody);
    return parsed;
  }

  // User parseUser(Map<String, dynamic> parsed) {
  //   return User.fromJson(parsed);
  // }


  class UserAPI {
    Future<Map<String, dynamic>> fetchUserByUserName(String username, String password) async {

      var url ='http://192.168.1.8:4380/login';
      var body = jsonEncode({
        "username": username,
        "password": password
      });

      // print("Body: " + body);

      final response = await http.post(Uri.parse(url),
          headers: {"Content-Type": "application/json"},
          body: body
      );

      Map<String, dynamic> token = await compute(parseToken, response.body);
      return token;
    }
  }
  

  class LoginBloc {
    final StreamController<String> _errorController = StreamController<String>();

    Stream<String> get error => _errorController.stream;

    final UserAPI userAPI = UserAPI();

    late User _user;
    User get user => _user;

    String _username = "";
    String _password = "";

    set setusername(String value) {
      _username = value.trim();
    }
    set setpassword(String value) {
      _password = value.trim();
    }

    Future<bool> validateLogin() async {
      if (_username.isEmpty) {
        _errorController.sink.add("Please enter username");
        return false;
      }
      if (_password.isEmpty) {
        _errorController.sink.add("Please enter password");
        return false;
      }
      
      final Map<String, dynamic> validate = await userAPI.fetchUserByUserName(_username, _password);
      int r = validate["r"];
      if (r == 0) {
        ///lay dc user luc login
          _user = User.fromJson(validate["m"]);
          return true;
      }
      String m = validate["m"];
      _errorController.sink.add(m);
      return false;
    }

    void dispose() {
      _errorController.close();
    }
  }