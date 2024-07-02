package com.madcamp.tabapp

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.madcamp.tabapp.data.User
import com.madcamp.tabapp.data.UserDao
import com.madcamp.tabapp.data.database.InitDb
import com.madcamp.tabapp.databinding.ActivityLoginBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity() {

    private lateinit var btnLogin: Button
    private lateinit var binding: ActivityLoginBinding

    private lateinit var userDao: UserDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize the UserDao
        userDao = InitDb.appDatabase.userDao()

        // Initialize UI components
        btnLogin = binding.btnLogin

        // Set onClickListener for the login button
        btnLogin.setOnClickListener {
            val loginId = binding.etLoginId.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            login(loginId, password)
        }
    }

    private fun login(loginId: String, password: String) {

        if (TextUtils.isEmpty(loginId) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "로그인 정보를 입력하세요", Toast.LENGTH_SHORT).show()
            return
        }

        CoroutineScope(Dispatchers.IO).launch {
            val user = userDao.getUserByLoginId(loginId)

            if (user == null) {
                // Check if the user_table has any data
                val userCount = userDao.getUserCount()
                if (userCount == 0) {
                    insertTestUser(loginId, password)
                    withContext(Dispatchers.Main) {
                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        startActivity(intent)
                        overridePendingTransition(0, R.anim.fade_out)
                        finish()
                    }
                    return@launch // Early return to prevent executing the rest of the coroutine
                }
            } else {
                // Handle the result on the main thread
                withContext(Dispatchers.Main) {
                    if (password == user.password) {
                        // Successful login, navigate to the main activity
                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        startActivity(intent)
                        overridePendingTransition(0, R.anim.fade_out)
                        finish()
                    } else {
                        // Invalid login credentials
                        Toast.makeText(this@LoginActivity, "로그인 정보가 잘못되었습니다", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

    }

    private fun insertTestUser(loginId: String, password: String) {
        CoroutineScope(Dispatchers.IO).launch {
            // Insert the test user
            val testUser = User(
                loginId = loginId,
                password = password,  // Note: In a real application, passwords should be hashed
                nickname = "대빵이",
                contact = "admin@example.com"
            )
            userDao.insert(testUser)
        }
    }
}
