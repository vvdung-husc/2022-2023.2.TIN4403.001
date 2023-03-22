import 'package:flutter/material.dart';
import 'package:login_app/provider/user_provider.dart';
import 'package:login_app/screen/login_screen.dart';
import 'package:provider/provider.dart';
import 'package:login_app/bloc/login_bloc.dart';

void main() {
  
  runApp(Provider(create: (_) => UserProvider(), child: const MyApp()));
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Provider(
                  create: (_) => LoginBloc(), child: const LoginScreen()));
  }
}


