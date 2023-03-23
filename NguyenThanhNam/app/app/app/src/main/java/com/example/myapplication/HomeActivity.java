package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HomeActivity extends AppCompatActivity {
    TextView textViewLogout, textInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        textViewLogout = findViewById(R.id.logoutNow);
        textInfo = findViewById(R.id.info);

        try {
            getInfo();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        textViewLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    void getInfo() throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://10.10.6.185:4380")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String myResponse = response.body().string();
                HomeActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textInfo.setText(myResponse);
                        Log.d("K43",myResponse);
                    }
                });
            }
        });
    }
}