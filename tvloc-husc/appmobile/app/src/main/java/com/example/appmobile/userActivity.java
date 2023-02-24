package com.example.appmobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class userActivity extends AppCompatActivity {

    private TextView user;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        MaterialButton logoutbtn = (MaterialButton) findViewById(R.id.logoutbtn);
        user = (TextView) findViewById(R.id.user);

        setDataByExtras();

        logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(userActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public void setDataByExtras(){
        Intent intent = getIntent();
        String us = intent.getStringExtra(MainActivity.US);

        user.setText("Chào mừng người dùng: "+ us);
    }
}
