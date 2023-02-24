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
    public static final String US = "US";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username =(EditText) findViewById(R.id.username);
        password =(EditText) findViewById(R.id.password);

        loginbtn = (MaterialButton) findViewById(R.id.loginbtn);
        signupbtn = (MaterialButton) findViewById(R.id.signupbtn);
        TextView quenmatkhau = (TextView) findViewById(R.id.quenmatkhau);

        String us = username.getText().toString();

        quenmatkhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, quenmatkhauActivity.class);
                startActivity(intent);
            }
        });
        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, dangkyActivity.class);
                startActivity(intent);
            }
        });

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(username.getText().toString().equals("admin") && password.getText().toString().equals("123")){
                    Toast.makeText(MainActivity.this,"Đăng nhập thành công",Toast.LENGTH_SHORT).show();
                    String us = username.getText().toString();
                    byExtras(us);

                }else if (username.getText().toString().isEmpty() || password.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Vui lòng không để trống tên đăng nhập hoặc mật khẩu", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this, "Sai tài khoản hoặc mật khẩu", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    public void byExtras(String us){
        Intent intent = new Intent(MainActivity.this, userActivity.class);
        intent.putExtra(US,us);
        startActivity(intent);
    }


}