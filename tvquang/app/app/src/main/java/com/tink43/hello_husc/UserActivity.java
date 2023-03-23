package com.tink43.hello_husc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class  UserActivity extends AppCompatActivity {
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    TextView m_txtWelcome;
    Button m_btnLogout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        //Khởi tạo các biến điều khiển tương ứng trong layout
        m_txtWelcome = (TextView)findViewById(R.id.txtWelcome);
        m_btnLogout = (Button) findViewById(R.id.btnLogout);

        //gan ten user vao textview

        String s = "Chào mừng tài khoản : " + MainActivity._userNameLogined;
        m_txtWelcome.setText(s);
        m_btnLogout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    
    /*public void getUserInfo(String username){
        String url = "http://192.168.1.4:4380/userinfo";
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(JSON, "{\"username\":\"" + username + "\"}");
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.d("K43","ERROR: " + e.getMessage());
                    }
        
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        //show ra thông tin user
                        Log.d("K43",response.body().string());
                        Toast.makeText(getApplicationContext(),response.body().string(),Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
            
    }*/
    

}