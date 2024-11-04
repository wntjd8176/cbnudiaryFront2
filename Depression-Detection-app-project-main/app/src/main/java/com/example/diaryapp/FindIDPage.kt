package com.example.diaryapp

import android.content.DialogInterface
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class FindIDPage : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.find_id)

        val regexEmail = Regex("""^([a-zA-Z0-9_\-\.]+)@([a-zA-Z0-9_\-\.]+)\.([a-zA-Z]{2,5})${'$'}""")
        val regexName = Regex("^[\\s가-힣a-zA-Z]{2,20}$")

        var inputName: EditText = findViewById(R.id.findID_name)
        var inputEmail: EditText = findViewById(R.id.findID_email)
        var findIDBtn: Button = findViewById(R.id.findId_button)

        findIDBtn.setOnClickListener {
            if (inputName.text.toString().matches(regexName) && inputEmail.text.toString().matches(regexEmail)) {
                if (true) {
                    // 이름과 이메일이 일치한 경우
                    // db 알고리즘 ->
                    val builder = Dialog("등록된 이메일로 아이디 정보를 전송하였습니다.")
                    builder.show()
                } else {
                    // DB에 이메일이나 아이디 정보가 없는 경우
                    val builder = Dialog("가입된 정보를 찾을 수 없습니다.")
                    builder.show()
                }
            }
            else {
                val builder = Dialog("이름 또는 이메일 입력이 잘못되었습니다.")
                builder.show()
            }
        }


    }

    fun Dialog(message: String): AlertDialog.Builder {
        val builder = AlertDialog.Builder(this)
        builder
            .setTitle("아이디 찾기")
            // 전달된 메시지 사용
            .setMessage(message)
            .setCancelable(true)
            .setPositiveButton("확인", object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {
                }
            })
        return builder
    }
}