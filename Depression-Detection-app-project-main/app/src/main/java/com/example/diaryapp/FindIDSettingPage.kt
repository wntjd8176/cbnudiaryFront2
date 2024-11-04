package com.example.diaryapp

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText

class FindIDSettingPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.find_pw_settings)

        var inputNewPWFindPWSet: TextInputEditText = findViewById(R.id.userInputNewPW_findPWset)
        var inputNewPW2FindPWSet: TextInputEditText = findViewById(R.id.userInputNewPW2_findPWset)
        var findPWSetBtn :Button = findViewById(R.id.findPWSet_button)



        findPWSetBtn.setOnClickListener {
            if (inputNewPWFindPWSet.text.toString() == inputNewPW2FindPWSet.text.toString()) {
                Log.i("inputNewPWFindPWSet", inputNewPWFindPWSet.text.toString())
                Log.i("inputNewPWFindPWSet", inputNewPW2FindPWSet.text.toString())

                // 유저 db에 변경된 비밀번호로 저장하기
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            } else {
                Log.i("inputNewPWFindPWSet", inputNewPWFindPWSet.text.toString())
                Log.i("inputNewPWFindPWSet", inputNewPW2FindPWSet.text.toString())

                val builder = showDialog("입력한 비밀번호가 일치하지 않습니다.\n비밀번호를 다시 입력해주세요.")
                builder.show()
            }
        }

    }
    fun showDialog(message: String): AlertDialog.Builder {
        val builder = AlertDialog.Builder(this)
        builder
            .setTitle("비밀번호 설정")
            .setMessage(message)
            .setCancelable(true)
            .setPositiveButton("확인", object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {
//                    Toast.makeText(baseContext, "아이디 중복 확인 완료", Toast.LENGTH_SHORT).show()
                }
            })
        return builder
    }
}