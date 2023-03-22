package com.example.androidq

import android.R
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.androidq.RegisterActivity
import com.example.androidq.UserActivity
import java.io.IOException

class MainActivity : AppCompatActivity() {
    var m_edtUser: EditText? = null
    var m_edtPass //Biến điều khiển EditText
            : EditText? = null
    var m_btnLogin: Button? = null
    var m_btnRegister //Biến điều khiển Button
            : Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Khởi tạo các biến điều khiển tương ứng trong layout
        m_edtUser = findViewById<View>(R.id.edtUser) as EditText
        m_edtPass = findViewById<View>(R.id.edtPassword) as EditText
        m_btnLogin = findViewById<View>(R.id.btnLogin) as Button
        m_btnRegister = findViewById<View>(R.id.btnRegister) as Button

        //Cài đặt sự kiện Click cho Button Login
        m_btnLogin!!.setOnClickListener(CButtonLogin())
        //Cài đặt sự kiện Click cho Button Register
        m_btnRegister!!.setOnClickListener(CButtonRegister())
    }

    inner class CButtonLogin : View.OnClickListener {
        override fun onClick(v: View) { //Hàm sử lý sự kiện click button login
            val user = m_edtUser!!.text.toString()
            val pass = m_edtPass!!.text.toString()
            Log.d("K43", "CLICK BUTTON LOGIN ACCOUNT $user/$pass")
            if (user.length < 3 || pass.length < 6) {
                Toast.makeText(
                    applicationContext,
                    "Tài khoản hoặc mật khẩu không hợp lệ!",
                    Toast.LENGTH_SHORT
                ).show()
                return
            }
            try {
                //Gọi hàm dịch vụ Login
                apiLogin(user, pass)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    //Hàm dịch vụ Login
    @Throws(IOException::class)
    fun apiLogin(user: String, pass: String) {
        val bOk = user == "Quangml" && pass == "123456"
        val json = "{\"username\":\"$user\",\"password\":\"$pass\"}$bOk"
        Toast.makeText(applicationContext, json, Toast.LENGTH_SHORT).show()
        Log.d("K43", json)
        if (bOk) {
            Log.d("K43", "TRUE OK")
            _userNameLogined = " HỮU QUANG "
            val intent = Intent(applicationContext, UserActivity::class.java)
            startActivity(intent)
        } else {
            Log.d("K43", "FALSE OK")
            runOnUiThread {
                Toast.makeText(
                    applicationContext,
                    "Tài khoản hoặc mật khẩu không chính xác.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    inner class CButtonRegister : View.OnClickListener {
        override fun onClick(v: View) { //Hàm sử lý sự kiện click button register
            Toast.makeText(applicationContext, "CButtonRegister::onClick...", Toast.LENGTH_SHORT)
                .show()
            val i = Intent(applicationContext, RegisterActivity::class.java)
            startActivity(i)
        }
    }

    companion object {
        @kotlin.jvm.JvmField
        var _userNameLogined: String
        var _userNameLogined: String? = null
    }
}