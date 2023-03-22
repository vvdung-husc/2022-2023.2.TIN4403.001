import 'package:login_app/bloc/signup_bloc.dart';
import 'package:login_app/provider/user_provider.dart';
import 'package:login_app/screen/user_profile.dart';
import 'package:provider/provider.dart';
import 'package:flutter/gestures.dart';
import 'package:flutter/material.dart';
import 'package:login_app/bloc/login_bloc.dart';
import 'package:login_app/screen/signup_screen.dart';

class LoginScreen extends StatefulWidget {
  const LoginScreen({super.key});

  @override
  State<LoginScreen> createState() => _LoginScreenState();
}

class _LoginScreenState extends State<LoginScreen> {
  bool passwordVisible = false;

  @override
  void initState() {
    super.initState();
    passwordVisible = true;
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        backgroundColor: Colors.grey.shade300,
        body: SafeArea(
          child: Consumer<LoginBloc>(
              builder: (context, loginb, _) => Center(
                    child: Column(
                        mainAxisAlignment: MainAxisAlignment.center,
                        children: [
                          //Lock icon
                          const Icon(
                            Icons.lock_open,
                            size: 200,
                          ),

                          //Hello
                          const SizedBox(
                            height: 80,
                          ),
                          _buildGreetingText(),
                          const SizedBox(
                            height: 10,
                          ),
                          _buildWelcomeText(),

                          //Username textfield
                          const SizedBox(
                            height: 40,
                          ),
                          _buildUsernameTextfield(loginb),

                          //Password textfield
                          const SizedBox(
                            height: 25,
                          ),
                          _buildPasswordTextfield(loginb),

                          ///Notification
                          const SizedBox(
                            height: 15,
                          ),
                          _buildErrorMessage(loginb),

                          //Login button
                          const SizedBox(
                            height: 25,
                          ),
                          _buildLoginButton(context, loginb),

                          //=>Register screen
                          const SizedBox(
                            height: 40,
                          ),
                          _buildSignUpText(context),
                        ]),
                  )),
        ));
  }

  StreamBuilder<String> _buildErrorMessage(LoginBloc loginb) {
    return StreamBuilder<String>(
                          stream: loginb.error,
                          builder: (context, snapshot) => Text(
                            snapshot.data ?? "",
                            style: const TextStyle(
                                fontSize: 18,
                                color: Colors.red,
                                fontStyle: FontStyle.italic),
                          ),
                        );
  }

  RichText _buildSignUpText(context) {
    return RichText(
      text: TextSpan(
          text: "Don't have an account?",
          style: const TextStyle(color: Colors.black, fontSize: 20),
          children: <TextSpan>[
            TextSpan(
              text: ' Sign up',
              style: const TextStyle(color: Colors.blueAccent, fontSize: 20),
              recognizer: TapGestureRecognizer()
                ..onTap = () => {
                      Navigator.push(
                          context,
                          MaterialPageRoute(
                              builder: (context) => Provider(
                                  create: (context) => SignupBloc(),
                                  child: const SignUpScreen())))
                    },
            )
          ]),
    );
  }

  Padding _buildLoginButton(BuildContext context, LoginBloc loginb) {
    return Padding(
      padding: const EdgeInsets.symmetric(horizontal: 25.0),
      child: Container(
        height: 60,
        decoration: BoxDecoration(
            color: Colors.deepPurple.shade400,
            borderRadius: BorderRadius.circular(13)),
        child: ElevatedButton(
          style: ElevatedButton.styleFrom(
            backgroundColor: Colors.deepPurple.shade400,
          ),
          onPressed: () {
            _validateLogin(context, loginb);
          },
          child: const Center(
            child: Text(
              'LOG IN',
              style: TextStyle(
                  fontWeight: FontWeight.bold,
                  color: Colors.white,
                  fontSize: 30),
            ),
          ),
        ),
      ),
    );
  }

  Padding _buildPasswordTextfield(LoginBloc loginb) {
    return Padding(
      padding: const EdgeInsets.symmetric(horizontal: 25.0),
      child: Container(
        decoration: BoxDecoration(
            color: Colors.grey.shade200,
            border: Border.all(color: Colors.white),
            borderRadius: BorderRadius.circular(13)),
        child: Padding(
          padding: const EdgeInsets.only(left: 20.0),
          child: TextField(
            onChanged: (value) {
              loginb.setpassword = value;
            },
            textInputAction: TextInputAction.next,
            style: const TextStyle(fontSize: 25),
            obscureText: passwordVisible,
            decoration: InputDecoration(
              border: InputBorder.none,
              hintText: 'Enter password:',
              suffixIcon: IconButton(
                icon: Icon(
                    passwordVisible ? Icons.visibility : Icons.visibility_off),
                onPressed: () {
                  setState(
                    () {
                      passwordVisible = !passwordVisible;
                    },
                  );
                },
              ),
            ),
          ),
        ),
      ),
    );
  }

  Padding _buildUsernameTextfield(LoginBloc loginb) {
    return Padding(
      padding: const EdgeInsets.symmetric(horizontal: 25.0),
      child: Container(
        decoration: BoxDecoration(
            color: Colors.grey.shade200,
            border: Border.all(color: Colors.white),
            borderRadius: BorderRadius.circular(13)),
        child: Padding(
          padding: const EdgeInsets.only(left: 20.0),
          child: TextField(
            onChanged: (value) {
              loginb.setusername = value;
            },
            style: const TextStyle(fontSize: 25),
            decoration: const InputDecoration(
              border: InputBorder.none,
              hintText: 'Enter username:',
            ),
          ),
        ),
      ),
    );
  }

  Text _buildWelcomeText() {
    return const Text(
      "Welcome back!!!",
      style: TextStyle(fontSize: 25),
    );
  }

  Text _buildGreetingText() {
    return const Text(
      "Hey yo, what's up bro?",
      style: TextStyle(fontWeight: FontWeight.bold, fontSize: 40),
    );
  }
}

  ///Loading
  void _validateLogin(BuildContext context, LoginBloc loginb) async {
    showDialog(
      barrierDismissible: false,
      context: context,
      builder: (_) {
        return Dialog(
          child: Padding(
            padding: const EdgeInsets.symmetric(vertical: 20),
            child: Column(
              mainAxisSize: MainAxisSize.min,
              children: const [
                CircularProgressIndicator(),
                SizedBox(height: 15),
                Text('Loading...')
              ],
            ),
          ),
        );
      },
    );

    if (await loginb.validateLogin()) {
      //print('dang nhap thanh cong');
      if (context.mounted) {
        Provider.of<UserProvider>(context, listen: false).user = loginb.user;
        Navigator.of(context).pop();
        Navigator.push(context,
            MaterialPageRoute(builder: (context) => const UserProfile()));
      }
    } else {
      // ignore: use_build_context_synchronously
      Navigator.of(context).pop();
    }
  }
