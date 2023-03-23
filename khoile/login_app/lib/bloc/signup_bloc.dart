import 'dart:async';
import 'dart:convert';
import 'package:flutter/foundation.dart';
import 'package:http/http.dart' as http;
import 'package:login_app/model/user.dart';

Map<String, dynamic> parseToken(String responseBody) {
    final Map<String, dynamic> parsed = jsonDecode(responseBody);

    return parsed;
  }

class UserAPI {
    Future<Map<String, dynamic>> register(String username, String password, String fullname, String id) async {

      var url ='http://192.168.1.8:4380/register';
      var body = jsonEncode({
        "username": username,
        "password": password,
        "fullname": fullname,
        "_id": id
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

class SignupBloc {
    final StreamController<String> _errorController = StreamController<String>();

    Stream<String> get error => _errorController.stream;

    final UserAPI userAPI = UserAPI();

    late User _user;
    User get user => _user;

    String _fullname = "";
    String _username = "";
    String _password = "";
    String _id = "";
    String _confirmPassword = "";

    set setusername(String value) {
      _username = value.trim();
    }
    set setpassword(String value) {
      _password = value.trim();
    }
    set setConfirmPassword(String value) {
      _confirmPassword = value.trim();
    }
    set setfullname(String value) {
      _fullname = value.trim();
    }

    Future<bool> validateSignup() async {
      if (_username.isEmpty) {
        _errorController.sink.add("Please enter username");
        return false;
      }
      if (_password.isEmpty) {
        _errorController.sink.add("Please enter password");
        return false;
      }
      if (_fullname.isEmpty) {
        _errorController.sink.add("Please enter your fullname");
        return false;
      }
      if (_confirmPassword.isEmpty) {
        _errorController.sink.add("Please confirm password");
        return false;
      }
      if (_password != _confirmPassword) {
        _errorController.sink.add("Your password is incorrect");
        return false;
      }
    
      final Map<String, dynamic> validate = await userAPI.register(_username, _password, _fullname, _id);
      int r = validate["r"];
      if (r == 0) {
          _user = User(_username, _password, _fullname, _id);
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