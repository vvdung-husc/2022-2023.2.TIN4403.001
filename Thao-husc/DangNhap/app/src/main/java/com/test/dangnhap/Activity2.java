package com.test.dangnhap;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;

public class Activity2 extends AppCompatActivity {
    EditText email, taikhoan, matkhau,matkhautai;
    Button dangky;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

        email=(EditText) findViewById(R.id.edtemail);
        taikhoan=(EditText) findViewById(R.id.edttentaikhoan2);
        matkhau=(EditText) findViewById(R.id.edtmatkhau2);
        matkhautai=(EditText) findViewById(R.id.edtmatkhaulai);
        dangky=(Button) findViewById(R.id.btdangky);
        dangky.setOnClickListener(new Activity2.CButtonDangKy());
    }
    public class CButtonDangKy implements View.OnClickListener {
        @Override
        public void onClick(View v) {//Hàm sử lý sự kiện click button login
            String myemail= email.getText().toString();
            String user = taikhoan.getText().toString();
            String pass = matkhau.getText().toString();
            String repass = matkhautai.getText().toString();
            Log.d("K43","CLICK BUTTON LOGIN ACCOUNT " + user + "/" + pass);
            if (user.length() <= 3 || pass.length() < 6){
                Toast.makeText(getApplicationContext(),"Tài khoản hoặc mật khẩu không hợp lệ!",Toast.LENGTH_SHORT).show();
                return;
            }
            else if(repass.compareTo(pass)!=0){
                Toast.makeText(getApplicationContext(),"Hai mat khau khong khop!",Toast.LENGTH_SHORT).show();
                return;
            }
            try {
                //Gọi hàm dịch vụ Login
                apiLogin(myemail,user,pass);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //Hàm dịch vụ Login
    void apiLogin(String myemail ,String user, String pass) throws IOException {
        String json = "{Email: " + myemail +" ,username: " + user + " ,password: " + pass +"}";
        Toast.makeText(getApplicationContext(),json,Toast.LENGTH_SHORT).show();
        Log.d("K43",json);
    }
}