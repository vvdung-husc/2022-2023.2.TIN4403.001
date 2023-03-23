package com.tink43.hello_husc;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class  UserActivity extends AppCompatActivity {
//    TextView back;
    Button back;
    TextView userName;
    TextView password;
    TextView fullName;

    TextView token;

    String tokenId;
    String data;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        initBinding();
        initView();
        initBack();
    }

    private void initView() {
        Intent intent = getIntent();
        data = intent.getStringExtra("key");
        tokenId = intent.getStringExtra("token");
        bindingTextView(data);
    }

    private void bindingTextView(String data) {
        try {
            JSONObject json = new JSONObject(data);
            JSONObject userName = json.getJSONObject("m");
            String name = userName.getString("username");
            String password = userName.getString("password");
            String fullname = userName.getString("fullname");
            this.userName.setText(name);
            this.password.setText(password);
            this.fullName.setText(fullname);
            this.token.setText(tokenId);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    private void initBack() {
        back.setOnClickListener(view -> {
            finish();
        }
        );
    }

    private void initBinding() {
        back = (Button) findViewById(R.id.back_button);
        userName = (TextView) findViewById(R.id.user_name);
        password = (TextView) findViewById(R.id.password);
        fullName = (TextView) findViewById(R.id.full_name);
        token = (TextView) findViewById(R.id.token);
    }


}