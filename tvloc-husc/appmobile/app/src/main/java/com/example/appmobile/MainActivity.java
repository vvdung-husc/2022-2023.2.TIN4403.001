package com.example.appmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    EditText username,password;
    MaterialButton loginbtn,signupbtn;
    static String US;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);

        loginbtn = (MaterialButton) findViewById(R.id.loginbtn);
        signupbtn = (MaterialButton) findViewById(R.id.signupbtn);


        String us = username.getText().toString();

        //Cài đặt sự kiện Click cho Button Login
        loginbtn.setOnClickListener(new CButtonLogin());
        //Cài đặt sự kiện Click cho Button Register
        signupbtn.setOnClickListener(new CButtonRegister());

    }

    public class CButtonLogin  implements View.OnClickListener {
        @Override
        public void onClick(View v) {//Hàm sử lý sự kiện click button login
            String user = username.getText().toString();
            String pass = password.getText().toString();
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

    public class CButtonRegister implements View.OnClickListener {

        @Override
        public void onClick(View v) {//Hàm sử lý sự kiện click button register
            //Toast.makeText(getApplicationContext(),"CButtonRegister::onClick...",Toast.LENGTH_SHORT).show();
            Intent i = new Intent(getApplicationContext(), dangkyActivity.class);
            startActivity(i);
        }
    }

    //Hàm mẫu sử dụng phương thức GET
    void doGet(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String myResponse = response.body().string();
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //txtString.setText(myResponse);
                        Log.d("K43",myResponse);
                    }
                });
            }
        });
    }

    //Hàm mẫu sử dụng phương thức POST
    void doPost(String url,String json) throws IOException {
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(JSON,json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("K43",response.body().string());
            }
        });
    }
    //Hàm dịch vụ Login
    void apiLogin(String user, String pass) throws IOException {
        //boolean bOk = (user.equals("tvloc") && pass.equals("1111111"));
        String json = "{\"username\":\"" + user + "\",\"password\":\"" + pass +"\"}";
        Toast.makeText(getApplicationContext(),json,Toast.LENGTH_SHORT).show();
        Log.d("K43",json);

        RequestBody body = RequestBody.create(JSON,json);
        Request request = new Request.Builder()
                .url("http://192.168.56.1:4380/login")
                .post(body)
                .build();
        OkHttpClient client = new OkHttpClient();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                String errStr = "Tài khoản hoặc mật khẩu không chính xác.\n" + e.getMessage();
                Log.d("K43","onFailure\n" + errStr);
                Toast.makeText(getApplicationContext(),errStr,Toast.LENGTH_SHORT).show();
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String errStr = "Tài khoản hoặc mật khẩu không chính xác.\n" + response.body().string();
                Log.d("K43",errStr);
                if (!response.isSuccessful()){
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),errStr,Toast.LENGTH_SHORT).show();
                        }
                    });
                    return;
                }

                Intent intent = new Intent(getApplicationContext(),userActivity.class);
                startActivity(intent);

            }
        });
    }


}