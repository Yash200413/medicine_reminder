package com.example.medicine_reminder

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.medicine_reminder.model.LoginRequest
import com.example.medicine_reminder.model.LoginResponse
import com.example.medicine_reminder.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val signUp = findViewById<TextView>(R.id.signupLink)
        val usernameInput = findViewById<EditText>(R.id.usernameInput)
        val passwordInput = findViewById<EditText>(R.id.passwordInput)
        val loginButton = findViewById<Button>(R.id.loginButton)
        signUp.setOnClickListener {
            Intent(this@MainActivity, SignUpActivity::class.java).also {
                startActivity(it)
            }

        }
        loginButton.setOnClickListener {
            val email = usernameInput.text.toString()
            val password = passwordInput.text.toString()

            if (email.isBlank() || password.isBlank()) {
                Toast.makeText(this, "Please enter both email and password", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }

            val request = LoginRequest(email, password)

            RetrofitClient.api.login(request).enqueue(object : Callback<LoginResponse> {
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    if (response.isSuccessful && response.body() != null) {
//                        val message = response.body()?.message ?: "Login success"
//                        Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT).show()
                        Intent(this@MainActivity, UserDetails::class.java).also {
                            startActivity(it)
                        }
                        finish()
                    } else {
                        Toast.makeText(this@MainActivity, "Invalid credentials", Toast.LENGTH_SHORT)
                            .show()
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Log.e("LoginError", "Network error", t)
                    Toast.makeText(
                        this@MainActivity,
                        "Network error: ${t.message}",
                        Toast.LENGTH_SHORT
                    ).show()

                }
            })
        }
    }
}
