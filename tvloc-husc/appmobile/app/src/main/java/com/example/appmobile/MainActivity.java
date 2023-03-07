package com.example.appmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {
    private  EditText username,password;
    private MaterialButton loginbtn,signupbtn;
    static String US;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username =(EditText) findViewById(R.id.username);
        password =(EditText) findViewById(R.id.password);

        loginbtn = (MaterialButton) findViewById(R.id.loginbtn);
        signupbtn = (MaterialButton) findViewById(R.id.signupbtn);


        String us = username.getText().toString();


        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), dangkyActivity.class);
                startActivity(intent);
            }
        });

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(username.getText().toString().equals("loctv_k43") && password.getText().toString().equals("021119")){
                    Toast.makeText(getApplicationContext(),"Đăng nhập thành công",Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(),"{\"username\":\"" + username.getText().toString() + "\",\"password\":\"" + password.getText().toString() +"\"}",Toast.LENGTH_SHORT).show();
                    US = username.getText().toString();
                    Intent intent = new Intent(getApplicationContext(),userActivity.class);
                    startActivity(intent);

                }else if (username.getText().toString().isEmpty() || password.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Vui lòng không để trống tên đăng nhập hoặc mật khẩu", Toast.LENGTH_SHORT).show();
                }else if (username.getText().toString().length() <=3 || password.getText().toString().length() < 6){
                    Toast.makeText(getApplicationContext(), "Tài khoản hoặc mật khẩu không hợp lệ", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(), "Sai tài khoản hoặc mật khẩu", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }




}