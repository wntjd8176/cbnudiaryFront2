package com.example.diaryapp

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class RegisterPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register)

        var inputName:EditText = findViewById(R.id.inputName_register)
        var inputPhone1: TextView = findViewById(R.id.inputPhone1_register)
        var inputPhone2: TextView = findViewById(R.id.inputPhone2_register)
        var inputPhone3: TextView = findViewById(R.id.inputPhone3_register)
        var inputPW1: EditText = findViewById(R.id.inputPW_register)
        var inputPW2: EditText = findViewById(R.id.inputPW2_register)
        var inputID: EditText = findViewById(R.id.inputID_register)
        var inputEmail: EditText = findViewById(R.id.inputEmail_register)
        var inputBabyName: EditText = findViewById(R.id.inputBabyName_register)
        var inputYear: TextView = findViewById(R.id.year_register)
        var inputMonth: TextView = findViewById(R.id.month_register)
        var inputDay: TextView = findViewById(R.id.day_register)

        var validateBtn: Button = findViewById(R.id.validate_button)
        var duplicateIDBtn: Button = findViewById(R.id.duplicateID_register)
        var signUp: Button = findViewById(R.id.signUp)

        val regexEmail = Regex("""^([a-zA-Z0-9_\-\.]+)@([a-zA-Z0-9_\-\.]+)\.([a-zA-Z]{2,5})${'$'}""")
        val regexID = Regex("^[A-Za-z0-9]+\$")
        val regexName = Regex("^[\\s가-힣a-zA-Z]{2,20}$")
        val regexPhone = Regex("^[\\s0-9]{4}$")
        val regexYear = Regex("^[\\s0-9]{4}$")
        val regexDay = Regex("^[\\s0-9]{2}$")

        validateBtn.setOnClickListener {
            // 표현식 확인
            if (inputName.text.toString().matches(regexName)
                && inputPhone2.text.toString().matches(regexPhone)
                && inputPhone3.text.toString().matches(regexPhone)) {
                // DB에 이름, 전화번호가 등록되지 않은 경우
                val t = true
                if (t){
                    println("인증요청중")
                    // 인증하는 코드 작성 필요
                } else {    // DB에 이름, 전화번호가 등록된 경우
                    val builder = Dialog("회원가입","가입된 정보가 있습니다.")
                    builder.show()
                }

            } else {    // 입력 오류인 경우
                val builder = Dialog("회원가입", "이름 또는 전화번호의 입력이 잘못되었습니다.")
                builder.show()
            }

        }


        duplicateIDBtn.setOnClickListener {
            // DB에서 중복 아이디가 있는지 확인하는 코드 작성 필요
            println(inputID.text.toString())
            if (inputID.text.toString().matches(regexID)) {
                val message = false
                if (message) {
                    val builder = Dialog("아이디 중복 확인","사용 가능한 아이디 입니다.")
                    builder.show()
                } else {
                    val builder = Dialog("아이디 중복 확인","이미 사용중인 아이디 입니다.\n아이디를 다시 입력해주세요.")
                    builder.show()
                }
            }
            else {
                val builder = Dialog("아이디 중복 확인","아이디 입력이 잘못되었습니다.")
                builder.show()
            }
        }

        signUp.setOnClickListener {
            // 입력되지 않은 곳이 있는지
            // 유효하지 않은 입력값이 있는지 확인 필요
            // 비밀번호 일치 여부 확인

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    fun Dialog(message1: String, message2: String): AlertDialog.Builder {
        val builder = AlertDialog.Builder(this)
        builder
            .setTitle(message1)
            // 전달된 메시지 사용
            .setMessage(message2)
            .setCancelable(true)
            .setPositiveButton("확인", object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {
//                    Toast.makeText(baseContext, "아이디 중복 확인 완료", Toast.LENGTH_SHORT).show()
                }
            })
        return builder
    }
}
