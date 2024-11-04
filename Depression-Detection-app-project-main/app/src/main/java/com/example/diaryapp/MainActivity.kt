package com.example.diaryapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        var findID: TextView = findViewById(R.id.findID)
        var findPW: TextView = findViewById(R.id.findPW)
        var register: TextView = findViewById(R.id.register)
        var login: Button = findViewById(R.id.login)

        findID.setOnClickListener {
            val intent = Intent(this, FindIDPage::class.java)
            startActivity(intent)
        }

        findPW.setOnClickListener {
            val intent = Intent(this, FindPWPage::class.java)
            startActivity(intent)
        }

        register.setOnClickListener {
            val intent = Intent(this, RegisterPage::class.java)
            startActivity(intent)
        }

        login.setOnClickListener {
            val intent = Intent(this, BottomNavActivity::class.java)
            startActivity(intent)
        }
    }
}
