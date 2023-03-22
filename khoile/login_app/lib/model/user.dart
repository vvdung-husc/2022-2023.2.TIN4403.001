
class User {
  final String username;
  final String password;
  final String fullname;
  final String _id;

  String get id => _id;

  User(this.username, this.password, this.fullname, this._id);

  User.fromJson(Map<String, dynamic> json)
      : username = json['username'],
        password = json['password'],
        fullname = json['fullname'],
        _id = json['_id'];

  Map<String, dynamic> toJson(json) => {
        'username': username,
        'password': password,
        'fullname': fullname,
        '_id': _id,
      };
}