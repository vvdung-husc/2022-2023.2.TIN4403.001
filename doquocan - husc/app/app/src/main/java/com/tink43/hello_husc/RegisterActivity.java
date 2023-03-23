package com.tink43.hello_husc;

import static com.tink43.hello_husc.MainActivity.JSON;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity {
    TextView m_txtBack;
    EditText m_edtUser, m_edtName, m_edtPass1, m_edtPass2; //Biến điều khiển EditText
    Button m_btnCreate;

    String responseMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //Gán các biến điều khiển cho các Controls
        m_txtBack = (TextView) findViewById(R.id.txtBack);
        m_edtUser = (EditText) findViewById(R.id.edtUser);
        m_edtName = (EditText) findViewById(R.id.edtName);
        m_edtPass1 = (EditText) findViewById(R.id.edtPass1);
        m_edtPass2 = (EditText) findViewById(R.id.edtPass2);
        m_btnCreate = (Button) findViewById(R.id.btnCreateUser);

        m_btnCreate.setOnClickListener(view -> {
//            if (passwordValidate(m_edtPass1.getText().toString(), m_edtPass2.getText().toString())) {
//                fetchRegisterAPI(m_edtUser.getText().toString(), m_edtName.getText().toString(), m_edtPass1.getText().toString());
//            } else {
//                Toast.makeText(this, "Mật khẩu không đúng", Toast.LENGTH_SHORT).show();
//            }
            switch (passwordValidate(m_edtPass1.getText().toString(), m_edtPass2.getText().toString(), m_edtUser.getText().toString())) {
                case 1:
                    Toast.makeText(this, "Mật khẩu xác nhập không khớp!", Toast.LENGTH_SHORT).show();
                    return;
                case 2:
                    Toast.makeText(this, "Mật khẩu quá ngắn hoặc quá dài!", Toast.LENGTH_SHORT).show();
                    return;
                case 3:
                    Toast.makeText(this, "Tài khoản quá ngắn hoặc quá dài!", Toast.LENGTH_SHORT).show();
                    return;
                case 0:
                    fetchRegisterAPI(m_edtUser.getText().toString(), m_edtName.getText().toString(), m_edtPass1.getText().toString());
                    return;
            }
        });

        m_txtBack.setOnClickListener(v -> {
            // Finish the registration screen and return to the Login activity
            finish();
        });
    }

    String post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json); // new
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    private void fetchRegisterAPI(String userName, String fullName, String password) {
        String json = "{\"username\":\"" + userName + "\",\"password\":\"" + password + "\", \"fullname\":\"" + fullName + "\"}";
//        Toast.makeText(getApplicationContext(), json, Toast.LENGTH_SHORT).show();
//        Log.d("K43", json);

        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url("http://192.168.1.6:4380/register")
                .post(body)
                .build();
        OkHttpClient client = new OkHttpClient();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
               // String responseData = response.body();
                JSONObject json;
                try {
                    json = new JSONObject(response.body().string());
                    int responseCode = json.getInt("r");
                    responseMessage = json.getString("m");
                    responseValidate(responseCode);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private void responseValidate(int code) {
        switch (code) {
            case 0:
                runOnUiThread(() -> {
                    Toast.makeText(this, responseMessage, Toast.LENGTH_SHORT).show();
                    finish();
                });
                return;

            case -1:
                runOnUiThread(() -> {
                    Toast.makeText(this, responseMessage + "\n Vui lòng chọn tên khác", Toast.LENGTH_SHORT).show();
                });
                return;

        }
    }

    private int passwordValidate(String pass1, String pass2, String userName) {
        if (!Objects.equals(pass1, pass2)) {
            return 1;
        }
        if (pass1.length() > 50 || pass1.length() < 3) {
            return 2;
        }
        if (userName.length() > 50 || userName.length() < 3) {
            return 3;
        }
        return 0;
    }
}