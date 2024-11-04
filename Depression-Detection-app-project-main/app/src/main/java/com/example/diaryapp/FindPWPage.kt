package com.example.diaryapp

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText

class FindPWPage: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.find_pw)

        var sendCertBtnFindPW: Button = findViewById(R.id.sendCertification_findPW)
        var checkCertBtnFindPW: Button = findViewById(R.id.checkCertification_findPW)
        var inputIDFindPW:TextInputEditText = findViewById(R.id.findPW_ID)
        var inputEmailFindPW:EditText = findViewById(R.id.findPW_email)
        var inputCertNumFindPW:TextInputEditText = findViewById(R.id.userInputNum_findPW)

        val regexEmail = Regex("""^([a-zA-Z0-9_\-\.]+)@([a-zA-Z0-9_\-\.]+)\.([a-zA-Z]{2,5})${'$'}""")
        val regexID = Regex("^[\\sa-zA-Z]$")

        sendCertBtnFindPW.setOnClickListener {
            // 이름과 이메일 입력 형식 확인
            if (inputIDFindPW.text.toString().matches(regexID) && inputEmailFindPW.text.toString().matches(regexEmail)) {
                // DB에 이름과 이메일 정보가 일치한 경우
                if (true) {
                    val builder = Dialog("등록된 이메일로 인증번호를 전송하였습니다.")
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

        checkCertBtnFindPW.setOnClickListener {
            // 발송한 인증번호와 입력한 인증번호가 맞으면 비밀번호 변경 화면으로 이동
            // 인증번호가 다르면 잘못됐다고 팝업 안내

            val num = 12345 // 임의 설정 인증번호
            if (num.toString() == inputCertNumFindPW.text.toString()) {  // 번호 일치 화면 이동
                val intent = Intent(this, FindIDSettingPage::class.java)
                startActivity(intent)
            } else {
                val builder = Dialog("인증번호가 일치하지 않습니다.\n인증번호를 다시 입력해주세요.")
                builder.show()
            }
        }
    }
    fun Dialog(message: String): AlertDialog.Builder {
        val builder = AlertDialog.Builder(this)
        builder
            .setTitle("인증번호 확인")
            .setMessage(message)
            .setCancelable(true)
            .setPositiveButton("확인", object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {
                }
            })
        return builder
    }
}