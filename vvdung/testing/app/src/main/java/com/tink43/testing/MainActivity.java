package com.tink43.testing;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    EditText m_edtUser,m_edtPass; //Biến điều khiển EditText
    Button m_btnLogin; //Biến điều khiển Button

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Khởi tạo các biến điều khiển tương ứng trong layout
        m_edtUser = (EditText)findViewById(R.id.edtUser);
        m_edtPass = (EditText)findViewById(R.id.edtPassword);
        m_btnLogin = (Button) findViewById(R.id.btnLogin);

        //Cài đặt sự kiện Click cho Button Login
        m_btnLogin.setOnClickListener(new CButtonLogin());

    }//protected void onCreate(Bundle savedInstanceState) {

    public class CButtonLogin  implements View.OnClickListener {
        @Override
        public void onClick(View v) {//Hàm sử lý sự kiện click button login
            String user = m_edtUser.getText().toString();
            String pass = m_edtPass.getText().toString();
            //Kiểm tra tính hợp lệ của tài khoản, mật khẩu
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
//            String txt = "Clicked Account: " + user + "/" + pass;
//            Toast.makeText(getApplicationContext(),txt, Toast.LENGTH_SHORT).show();
        }
    }
    //Hàm dịch vụ Login
    void apiLogin(String user, String pass) throws IOException {
        String json = "{\"username\":\"" + user + "\",\"password\":\"" + pass +"\"}";
        Toast.makeText(getApplicationContext(),json,Toast.LENGTH_SHORT).show();
        Log.d("K43",json);
    }
}