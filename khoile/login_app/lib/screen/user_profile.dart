import 'package:flutter/material.dart';
import 'package:login_app/bloc/login_bloc.dart';
import 'package:login_app/provider/user_provider.dart';
import 'package:login_app/screen/login_screen.dart';
import 'package:provider/provider.dart';

class UserProfile extends StatelessWidget {
  const UserProfile({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: SafeArea(
        child: Consumer<UserProvider>(builder: (context, userp, _) =>  Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [

              const Icon(Icons.person, size: 250,),

              const Text(
                'User Profile',
                style: TextStyle(
                  fontSize: 70,
                  fontWeight: FontWeight.bold
                ),
              ),

              const SizedBox(
                height: 50,
              ),
              _buildId(userp),

              const SizedBox(
                height: 30,
              ),
              _buildUsername(userp),

              const SizedBox(
                height: 30,
              ),
              _buildFullname(userp),

              const SizedBox(
                height: 30,
              ),
              _buildPassword(userp),

              const SizedBox(
                height: 70,
              ),
              _buildLogoutButton(context)

            ],
          ),
      ),
    ));
  }

  Padding _buildId(UserProvider userp) {
    return Padding(
              padding: const EdgeInsets.only(left: 30.0),
              child: Row(
                children: [
                  const Text(
                    'UserID: ',
                    style: TextStyle(
                      color: Colors.deepPurple,
                      fontSize: 35,
                      fontWeight: FontWeight.bold
                    ),
                  ),
                  Text(
                    userp.user.id,
                    style: const TextStyle(
                      fontSize: 32,
                    ),
                  ),
                ],
              ),
            );
  }

  Padding _buildUsername(UserProvider userp) {
    return Padding(
              padding: const EdgeInsets.only(left: 30.0),
              child: Row(
                children: [
                  const Text(
                    'Username: ',
                    style: TextStyle(
                      color: Colors.deepPurple,
                      fontSize: 35,
                      fontWeight: FontWeight.bold
                    ),
                  ),
                  Text(
                    userp.user.username,
                    style: const TextStyle(
                      fontSize: 32,
                    ),
                  ),
                ],
              ),
            );
  }

  Padding _buildFullname(UserProvider userp) {
    return Padding(
              padding: const EdgeInsets.only(left: 30.0),
              child: Row(
                children: [
                  const Text(
                    'Fullname: ',
                    style: TextStyle(
                      color: Colors.deepPurple,
                      fontSize: 35,
                      fontWeight: FontWeight.bold
                    ),
                  ),
                  Text(
                    userp.user.fullname,
                    style: const TextStyle(
                      fontSize: 32,
                    ),
                  ),
                ],
              ),
            );
  }

  Padding _buildPassword(UserProvider userp) {
    return Padding(
              padding: const EdgeInsets.only(left: 30.0),
              child: Row(
                children: [
                  const Text(
                    'Password: ',
                    style: TextStyle(
                      color: Colors.deepPurple,
                      fontSize: 35,
                      fontWeight: FontWeight.bold
                    ),
                  ),
                  Text(
                    userp.user.password,
                    style: const TextStyle(
                      fontSize: 32,
                    ),
                  ),
                ],
              ),
            );
  }

  Padding _buildLogoutButton(BuildContext context) {
    return Padding(
              padding: const EdgeInsets.symmetric(horizontal: 200.0),
              child: Container(
                height: 60,
                decoration: BoxDecoration(
                  color: Colors.deepPurple.shade400,
                  borderRadius: BorderRadius.circular(13)
                ),
                child: ElevatedButton(
                  style: ElevatedButton.styleFrom(
                    backgroundColor: Colors.deepPurple.shade400,
                  ),
                  onPressed: () { 
                    Navigator.pushAndRemoveUntil<void>(
                            context,
                            MaterialPageRoute<void>(builder: (BuildContext context) => Provider(
                              create: (_) => LoginBloc(), 
                              child: const LoginScreen())),
                            (route) => false,
                    );
                  },
                  child: const Center(
                    child: Text(
                      'Log Out',
                      style: TextStyle(
                        fontWeight: FontWeight.bold,
                        color: Colors.white,
                        fontSize: 30
                      ),
                    ),
                  ),
                ),
              ),
            );
  }
}