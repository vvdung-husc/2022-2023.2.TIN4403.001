package com.test.dangnhap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Dangky extends AppCompatActivity {
    TextView m_txtBack;
    EditText m_edtUser,m_edtName,m_edtPass1,m_edtPass2; //Biến điều khiển EditText
    Button m_btnCreate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dangky);
        //Gán các biến điều khiển cho các Controls
        m_edtName = (EditText)findViewById(R.id.edthoten);
        m_edtUser = (EditText)findViewById(R.id.edttentaikhoan2);
        m_edtPass1 = (EditText)findViewById(R.id.edtmatkhau2);
        m_edtPass2 = (EditText)findViewById(R.id.edtmatkhaulai);
        m_btnCreate = (Button)findViewById(R.id.btdangky);

        m_btnCreate.setOnClickListener(new View.OnClickListener(){
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