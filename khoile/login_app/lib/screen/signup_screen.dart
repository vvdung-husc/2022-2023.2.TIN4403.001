import 'package:flutter/gestures.dart';
import 'package:flutter/material.dart';
import 'package:login_app/bloc/login_bloc.dart';
import 'package:login_app/bloc/signup_bloc.dart';
import 'package:login_app/screen/login_screen.dart';
import 'package:provider/provider.dart';

class SignUpScreen extends StatefulWidget {
  const SignUpScreen({super.key});

  @override
  State<SignUpScreen> createState() => _SignupScreenState();
}

class _SignupScreenState extends State<SignUpScreen> {
  bool passwordVisible1=false;
  bool passwordVisible2=false;

  @override
    void initState(){
      super.initState();
      passwordVisible1=true;
      passwordVisible2=true;
    }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Colors.grey.shade300,
      body: SafeArea(
        child: Consumer<SignupBloc>(builder: (context, signupb, _) => Center(
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              ///Lock icon
              const Icon(Icons.lock_open, size: 200,),
              
              ///Hello
              const SizedBox(
                height: 80,
              ),
              _buildText(),

              ///Username textfield
              const SizedBox(
                height: 40,
              ),
              _buildUsernameTextfield(signupb),

              ///Fullname textfield
              const SizedBox(
                height: 25,
              ),
              _buildFullnameTextfield(signupb),

              ///Password textfield
              const SizedBox(
                height: 25,
              ),
              _buildPasswordTextfield(signupb),

              ///Password textfield
              const SizedBox(
                height: 25,
              ),
              _buildConfirmPasswordTextfield(signupb),

              ///notification
              const SizedBox(
                height: 15,
              ),
              _buildErrorMessage(signupb),

              ///Sign Up button
              const SizedBox(
                height: 25,
              ),
              _buildSignupButton(context, signupb),

              ///=>Login screen
              const SizedBox(
                height: 40,
              ),
              _buildLoginText(context), 
            ]
          ),
        ),
      ),
    ));
  }

  StreamBuilder<String> _buildErrorMessage(SignupBloc signupb) {
    return StreamBuilder<String>(
              stream: signupb.error,
              builder: (context, snapshot) => Text(
                snapshot.data ?? "",
                style: const TextStyle(
                  fontSize: 18,
                  color: Colors.red, fontStyle: FontStyle.italic),
              ),
            );
  }

  RichText _buildLoginText(context) {
    return RichText(  
              text: TextSpan(  
                  text: "Already have an account?",  
                  style: const TextStyle(color: Colors.black, fontSize: 20),  
                  children: <TextSpan>[  
                      TextSpan(text: ' Log in',  
                          style: const TextStyle(color: Colors.blueAccent, fontSize: 20),
                          recognizer: TapGestureRecognizer()..onTap = () => {
                            //Navigator.push(context, MaterialPageRoute(builder: (context) => const LoginScreen()))
                            Navigator.pushAndRemoveUntil<void>(
                              context,
                              MaterialPageRoute<void>(builder: (BuildContext context) => Provider(
                                create: (_) => LoginBloc(), 
                                child: const LoginScreen())
                              ),
                              (route) => false,
                            )
                          },
                      )  
                  ]  
              ),  
            );
  }

  Padding _buildSignupButton(BuildContext context, SignupBloc signupb) {
    return Padding(
              padding: const EdgeInsets.symmetric(horizontal: 25.0),
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
                  onPressed: () async { 
                    _validateRegister(context, signupb);
                  },
                  child: const Center(
                    child: Text(
                      'SIGN UP',
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

  Padding _buildPasswordTextfield(SignupBloc signupb) {
    return Padding(
              padding: const EdgeInsets.symmetric(horizontal: 25.0),
              child: Container(
                decoration: BoxDecoration(
                  color: Colors.grey.shade200,
                  border: Border.all(color: Colors.white), 
                  borderRadius: BorderRadius.circular(13)
                ),
                child: Padding(
                  padding: const EdgeInsets.only(left: 20.0),
                  child: TextField(
                    onChanged: (value){signupb.setpassword = value;},
                    style: const TextStyle(
                      fontSize: 25
                    ),
                    obscureText: passwordVisible1,
                    decoration: InputDecoration(
                      border: InputBorder.none,
                      hintText: 'Enter password:',
                      suffixIcon: IconButton(
                        icon: Icon(passwordVisible1 ? Icons.visibility : Icons.visibility_off),
                        onPressed: () {
                          setState(
                            () {
                              passwordVisible1 = !passwordVisible1;
                            },
                          );
                        },
                      )
                    ),
                  ),
                ),
              ),
            );
  }

  Padding _buildConfirmPasswordTextfield(SignupBloc signupb) {
    return Padding(
              padding: const EdgeInsets.symmetric(horizontal: 25.0),
              child: Container(
                decoration: BoxDecoration(
                  color: Colors.grey.shade200,
                  border: Border.all(color: Colors.white), 
                  borderRadius: BorderRadius.circular(13)
                ),
                child: Padding(
                  padding: const EdgeInsets.only(left: 20.0),
                  child: TextField(
                    onChanged: (value){signupb.setConfirmPassword = value;},
                    style: const TextStyle(
                      fontSize: 25
                    ),
                    obscureText: passwordVisible2,
                    decoration: InputDecoration(
                      border: InputBorder.none,
                      hintText: 'Enter password again:',
                      suffixIcon: IconButton(
                        icon: Icon(passwordVisible2 ? Icons.visibility : Icons.visibility_off),
                        onPressed: () {
                          setState(
                            () {
                              passwordVisible2 = !passwordVisible2;
                            },
                          );
                        },
                      )
                    ),
                  ),
                ),
              ),
            );
  }

  Padding _buildUsernameTextfield(SignupBloc signupb) {
    return Padding(
              padding: const EdgeInsets.symmetric(horizontal: 25.0),
              child: Container(
                decoration: BoxDecoration(
                  color: Colors.grey.shade200,
                  border: Border.all(color: Colors.white), 
                  borderRadius: BorderRadius.circular(13)
                ),
                child: Padding(
                  padding: const EdgeInsets.only(left: 20.0),
                  child: TextField(
                    onChanged: (value){signupb.setusername = value;},
                    style: const TextStyle(
                      fontSize: 25
                    ),
                    decoration: const InputDecoration(
                      border: InputBorder.none,
                      hintText: 'Enter username:',
                    ),
                  ),
                ),
              ),
            );
  }

  Padding _buildFullnameTextfield(SignupBloc signupb) {
    return Padding(
              padding: const EdgeInsets.symmetric(horizontal: 25.0),
              child: Container(
                decoration: BoxDecoration(
                  color: Colors.grey.shade200,
                  border: Border.all(color: Colors.white), 
                  borderRadius: BorderRadius.circular(13)
                ),
                child: Padding(
                  padding: const EdgeInsets.only(left: 20.0),
                  child: TextField(
                    onChanged: (value){signupb.setfullname = value;},
                    style: const TextStyle(
                      fontSize: 25
                    ),
                    decoration: const InputDecoration(
                      border: InputBorder.none,
                      hintText: 'Enter your fullname:',
                    ),
                  ),
                ),
              ),
            );
  }

  Text _buildText() {
    return const Text(
              "Create new account!",
              style: TextStyle(
                fontWeight: FontWeight.bold,
                fontSize: 40
              ),
            );
  }
}

  ///Loading dialog and notification dialog
  void _validateRegister(BuildContext context, SignupBloc signupb) async {
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
    if (await signupb.validateSignup()) {
      // ignore: use_build_context_synchronously
      showDialog(
        context: context,
        builder: (context) {
          return AlertDialog(
            backgroundColor: Colors.deepPurple.shade100,
            title: const Text(
              'Notification',
              style: TextStyle(
                fontSize: 30,
                fontWeight: FontWeight.bold
              ),
            ),
            content: const Text(
              'Sign Up Success, go to the Login Screen',
              style: TextStyle(
                fontSize: 24,
              ),
            ),
            actions: [
              ElevatedButton(
                    style: ElevatedButton.styleFrom(
                      backgroundColor: Colors.deepPurple.shade400,
                    ),
                    onPressed: (){
                      Navigator.push(context, MaterialPageRoute(builder: (context) => Provider(
                        create: (_) => LoginBloc(), child: const LoginScreen())));},
                    child: const Text(
                      "OK",
                      style: TextStyle(
                          fontWeight: FontWeight.bold,
                          color: Colors.white,
                          fontSize: 29
                        ),))
            ], 
          );
        }
      );
      
    } else {
      // ignore: use_build_context_synchronously
      Navigator.of(context).pop();
    }
  }