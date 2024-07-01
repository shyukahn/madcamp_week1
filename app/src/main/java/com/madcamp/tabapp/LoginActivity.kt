package com.madcamp.tabapp

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.madcamp.tabapp.data.database.InitDb
import com.madcamp.tabapp.data.User
import com.madcamp.tabapp.data.UserDao
import com.madcamp.tabapp.databinding.ActivityLoginBinding

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity() {

    private lateinit var etLoginId: EditText
    private lateinit var etPassword: EditText
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
        etLoginId = binding.etLoginId
        etPassword = binding.etPassword
        btnLogin = binding.btnLogin

        // Set onClickListener for the login button
        btnLogin.setOnClickListener {
            login()
        }

        // Insert a test user for demonstration purposes
        insertTestUser()

        // print all users
        loadAllUsers()
    }

    private fun login() {

        val loginId = etLoginId.text.toString().trim()
        val password = etPassword.text.toString().trim()

        if (TextUtils.isEmpty(loginId) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "로그인 정보를 입력하세요", Toast.LENGTH_SHORT).show()
            return
        }

        // Execute the database query on a background thread using coroutines
        CoroutineScope(Dispatchers.IO).launch {
            val user = userDao.getUserByLoginId(loginId)

            // Handle the result on the main thread
            withContext(Dispatchers.Main) {
                if (user != null && password == user.password) {
                    // Successful login, navigate to the main activity
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    // Invalid login credentials
                    Toast.makeText(this@LoginActivity, "로그인 정보가 잘못되었습니다", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun insertTestUser() {
        CoroutineScope(Dispatchers.IO).launch {
            // Check if the test user 'admin' already exists in the db
            if (userDao.getUserByLoginId("admin") == null) {
                // Insert the test user
                val testUser = User(
                    loginId = "admin",
                    password = "admin",  // Note: In a real application, passwords should be hashed
                    fullName = "Admin User",
                    contact = "admin@example.com"
                )

                userDao.insert(testUser)
            }
        }
    }

    private fun loadAllUsers() {
        CoroutineScope(Dispatchers.IO).launch {
            val userDao = InitDb.appDatabase.userDao()
            val userList = userDao.getAllUsers()

            // 로그를 통해 사용자 데이터를 콘솔에 출력
            userList.forEach { user ->
                Log.d("MainActivity", "유저 정보 User: ${user.loginId}, ${user.fullName}, ${user.contact}")
            }
        }
    }
}
