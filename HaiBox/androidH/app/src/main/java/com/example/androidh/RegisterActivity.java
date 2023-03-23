package com.example.androidh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity {
    TextView m_txtBack;
    EditText m_edtUser,m_edtName,m_edtPass1,m_edtPass2; //Biến điều khiển EditText
    Button m_btnCreate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //Gán các biến điều khiển cho các Controls
        m_txtBack = (TextView)findViewById(R.id.txtBack);
        m_edtUser = (EditText)findViewById(R.id.edtUser);
        m_edtName = (EditText)findViewById(R.id.edtName);
        m_edtPass1 = (EditText)findViewById(R.id.edtPass1);
        m_edtPass2 = (EditText)findViewById(R.id.edtPass2);
        m_btnCreate = (Button)findViewById(R.id.btnCreateUser);

        m_txtBack.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}