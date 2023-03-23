package com.tink43.hello_husc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.IOException;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity {
    TextView m_txtBack;
    EditText m_edtUser,m_edtName,m_edtPass1,m_edtPass2; //Biến điều khiển EditText
    Button m_btnCreate;

    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    

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

        m_btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xử lý sự kiện click vào nút "Tạo tài khoản" ở đây
                // Lấy thông tin người dùng từ các controls EditText
                String username = m_edtUser.getText().toString();
                String name = m_edtName.getText().toString();
                String password1 = m_edtPass1.getText().toString();
                String password2 = m_edtPass2.getText().toString();

                // Kiểm tra các trường thông tin nhập vào
                if (username.isEmpty() || name.isEmpty() || password1.isEmpty() || password2.isEmpty()) {
                    // Nếu có trường thông tin nào bị bỏ trống, hiển thị thông báo lỗi
                    Toast.makeText(getApplicationContext(), "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else if (!password1.equals(password2)) {
                    // Nếu mật khẩu nhập lại không khớp với mật khẩu đã nhập, hiển thị thông báo lỗi
                    Toast.makeText(getApplicationContext(), "Mật khẩu không khớp", Toast.LENGTH_SHORT).show();
                } else {
                    apiCreateUser(username, password1, name);
                }
            }
        });

    }
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
                RegisterActivity.this.runOnUiThread(new Runnable() {
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


    public void apiCreateUser(String username, String password, String name) {
        RequestBody body = RequestBody.create(JSON, "{\"username\": \"" + username + "\", \"password\": \"" + password + "\", \"fullname\": \"" + name + "\"}");
        
        Request request = new Request.Builder()
            .url("http://192.168.1.5:4380/register")
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
                Log.d("K43",response.body().string());
            
                if (response.isSuccessful()) {
                    String myResponse = response.body().string();
                    Log.d("K43",myResponse);
                    try {
                        JSONObject jsonObject = new JSONObject(myResponse);
                        String status = jsonObject.getString("status");
                        if (status.equals("success")) {
                            // Nếu đăng ký thành công, chuyển về màn hình đăng nhập

                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            // Nếu đăng ký thất bại, hiển thị thông báo lỗi
                            String message = jsonObject.getString("message");
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        
    }

    
}

