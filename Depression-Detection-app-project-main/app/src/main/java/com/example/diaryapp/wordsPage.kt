package com.example.diaryapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.diaryapp.Serivce.UserChartApiService.UserChartDTO
import com.example.diaryapp.Network.RetrofitInstance
import com.example.diaryapp.Serivce.UserChartApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import android.widget.Toast
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
            val userChartDTO = UserChartDTO(
                userID = userId.text.toString(),
                favoriteWord = likeWord.text.toString(),
                unfavoriteWord = unlikeWord.text.toString()
            )

            CoroutineScope(Dispatchers.IO).launch {
                try {
                    // Service 인스턴스 생성
                    val userApiService = RetrofitInstance.create(UserChartApiService::class.java)
                    val response = userApiService.registChart(userChartDTO)

                    withContext(Dispatchers.Main) {
                        if (response.isSuccessful) {
                            Toast.makeText(this@wordsPage, "저장 성공!", Toast.LENGTH_SHORT).show()

                            // 페이지 이동
                            val intent = Intent(this@wordsPage, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(this@wordsPage, "저장 실패: ${response.message()}", Toast.LENGTH_SHORT).show()
                        }
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@wordsPage, "API 호출 중 오류 발생: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }




        }
    }
}