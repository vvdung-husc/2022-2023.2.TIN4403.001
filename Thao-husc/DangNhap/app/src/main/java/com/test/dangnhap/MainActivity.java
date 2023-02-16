package com.test.dangnhap;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    EditText  tentaikhoan, matkhau;
    TextView quenmatkhau, dangky;
    Button dangnhap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //m_edtUser = (EditText)findViewById(R.id.edtUser);
        quenmatkhau= (TextView) findViewById(R.id.tvquenmatkhau);
        dangky= (TextView) findViewById(R.id.tvdangky);
        tentaikhoan=(EditText) findViewById(R.id.edttentaikhoan);
       matkhau=(EditText) findViewById(R.id.edtmatkhau);
       dangnhap=(Button) findViewById(R.id.btdangnhap);
        dangnhap.setOnClickListener(new CButtonDangNhap());
        dangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Activity2.class);
                startActivity(intent);
            }
        });
    }
    public class CButtonDangNhap implements View.OnClickListener {
        @Override
        public void onClick(View v) {//Hàm sử lý sự kiện click button login
            String user = tentaikhoan.getText().toString();
            String pass = matkhau.getText().toString();
            Log.d("K43","CLICK BUTTON LOGIN ACCOUNT " + user + "/" + pass);
            if (user.length() <= 3 || pass.length() < 6){
                Toast.makeText(getApplicationContext(),"Tài khoản hoặc mật khẩu không hợp lệ!",Toast.LENGTH_SHORT).show();
                return;
            }
            try {
                //Gọi hàm dịch vụ Login
                apiLogin(user,pass);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //Hàm dịch vụ Login
    void apiLogin(String user, String pass) throws IOException {
        String json = "{\"username\":\"" + user + "\",\"password\":\"" + pass +"\"}";
        Toast.makeText(getApplicationContext(),json,Toast.LENGTH_SHORT).show();
        Log.d("K43",json);
    }
}