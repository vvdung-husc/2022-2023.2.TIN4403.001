package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    static String   _userNameLogined;
    EditText m_edtUser,m_edtPass; //Biến điều khiển EditText
    Button m_btnLogin, m_btnRegister; //Biến điều khiển Button
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Khởi tạo các biến điều khiển tương ứng trong layout
        m_edtUser = (EditText)findViewById(R.id.edtUser);
        m_edtPass = (EditText)findViewById(R.id.edtPassword);
        m_btnLogin = (Button) findViewById(R.id.btnLogin);
        m_btnRegister = (Button)findViewById(R.id.btnRegister);

        //Cài đặt sự kiện Click cho Button Login
        m_btnLogin.setOnClickListener(new CButtonLogin());
        //Cài đặt sự kiện Click cho Button Register
        m_btnRegister.setOnClickListener(new CButtonRegister());

    }

    public class CButtonLogin  implements View.OnClickListener {
        @Override
        public void onClick(View v) {//Hàm sử lý sự kiện click button login
            String user = m_edtUser.getText().toString();
            String pass = m_edtPass.getText().toString();
            Log.d("K43","CLICK BUTTON LOGIN ACCOUNT " + user + "/" + pass);
            if (user.length() < 3 || pass.length() < 6){
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
        boolean bOk = (user.equals("lnphi") && pass.equals("123456"));
        String json = "{\"username\":\"" + user + "\",\"password\":\"" + pass +"\"}" + bOk;
        Toast.makeText(getApplicationContext(),json,Toast.LENGTH_SHORT).show();
        Log.d("K43",json);

        if (bOk){
            Log.d("K43","TRUE OK");
            _userNameLogined = " Lụa Hà ";
            Intent intent = new Intent(getApplicationContext(),UserActivity.class);
            startActivity(intent);
        }
        else{
            Log.d("K43","FALSE OK");
            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(),"Tài khoản hoặc mật khẩu không chính xác.",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public class CButtonRegister implements View.OnClickListener {

        @Override
        public void onClick(View v) {//Hàm sử lý sự kiện click button register
            Toast.makeText(getApplicationContext(),"CButtonRegister::onClick...",Toast.LENGTH_SHORT).show();
            Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
            startActivity(i);
        }
    }
}
