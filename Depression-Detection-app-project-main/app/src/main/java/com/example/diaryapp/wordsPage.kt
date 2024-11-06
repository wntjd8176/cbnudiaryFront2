package com.example.diaryapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat

import androidx.core.view.WindowInsetsCompat

class wordsPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_words_page)

        var userId:EditText = findViewById(R.id.user_id)
        var likeWord:EditText = findViewById(R.id.like_word)
        var unlikeWord:EditText = findViewById(R.id.unlike_word)
        var saveWordsBtn: Button = findViewById(R.id.save_words_btn)


        saveWordsBtn.setOnClickListener {


            // 페이지 이동
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}