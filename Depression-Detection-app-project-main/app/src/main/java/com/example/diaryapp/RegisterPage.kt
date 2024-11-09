package com.example.diaryapp

import com.example.diaryapp.Network.SmsService
import com.example.diaryapp.Network.RetrofitInstance
import com.example.diaryapp.Serivce.UserApiService.UserDTO
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import com.example.diaryapp.Serivce.CoolSmsService

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

import com.example.diaryapp.Serivce.UserApiService
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

        var validateNumber: EditText = findViewById(R.id.validate_number)
        var validateChkBtn: Button = findViewById(R.id.validate_chk_button)

        val regexEmail = Regex("""^([a-zA-Z0-9_\-\.]+)@([a-zA-Z0-9_\-\.]+)\.([a-zA-Z]{2,5})${'$'}""")
        val regexID = Regex("^[A-Za-z0-9]+\$")
        val regexName = Regex("^[\\s가-힣a-zA-Z]{2,20}$")
        val regexPhone = Regex("^[\\s0-9]{4}$")
        val regexYear = Regex("^[\\s0-9]{4}$")
        val regexDay = Regex("^[\\s0-9]{2}$")


        val coolSmsService = RetrofitInstance.create(CoolSmsService::class.java)
        val smsService = SmsService(coolSmsService)
        var isIDAvailable = false

        validateBtn.setOnClickListener {
            // 표현식 확인
            if (inputName.text.toString().matches(regexName)
                && inputPhone2.text.toString().matches(regexPhone)
                && inputPhone3.text.toString().matches(regexPhone)) {
                // DB에 이름, 전화번호가 등록되지 않은 경우
                val fullPhoneNumber = "010" + inputPhone2.text.toString() + inputPhone3.text.toString()


                // 서버로 SMS 인증 요청을 보내는 함수 호출

                //val t = true
                smsService.sendSms(fullPhoneNumber) { success, message ->
                    if (success) {
                        // 성공 시: 인증 코드 전송 성공 메시지 표시
                        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                        println("인증 요청 중")
                        // 인증하는 코드 작성 필요
                    } else {    // DB에 이름, 전화번호가 등록된 경우
                        val builder = Dialog("회원가입", "가입된 정보가 있습니다.")
                        builder.show()
                    }
                }

            } else {    // 입력 오류인 경우
                val builder = Dialog("회원가입", "이름 또는 전화번호의 입력이 잘못되었습니다.")
                builder.show()
            }

        }


        duplicateIDBtn.setOnClickListener {
            val inputIdText = inputID.text.toString()

            if (inputIdText.matches(regexID)) {
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        // 서버에 중복 아이디 확인 요청 (예시: checkIDAvailability 메서드)
                        val userApiService = RetrofitInstance.create(UserApiService::class.java)
                        val response = userApiService.checkIDAvailability(inputIdText)

                        withContext(Dispatchers.Main) {
                            if (response.isSuccessful && response.body() == true) {
                                isIDAvailable = true // 사용 가능한 아이디
                                val builder = Dialog("아이디 중복 확인", "사용 가능한 아이디 입니다.")
                                builder.show()
                            } else {
                                isIDAvailable = false // 이미 사용 중인 아이디
                                val builder = Dialog("아이디 중복 확인", "이미 사용중인 아이디 입니다.\n아이디를 다시 입력해주세요.")
                                builder.show()
                            }
                        }
                    } catch (e: Exception) {
                        Log.e("API_CALL", "Error occurred during ID check: ${e.message}", e)
                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@RegisterPage, "아이디 중복 확인 중 오류 발생", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            } else {
                val builder = Dialog("아이디 중복 확인", "아이디 입력이 잘못되었습니다.")
                builder.show()
            }
        }

        signUp.setOnClickListener {
            if (!isIDAvailable) {
                Toast.makeText(this@RegisterPage, "아이디 중복 확인을 해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 입력되지 않은 곳이 있는지
            // 유효하지 않은 입력값이 있는지 확인 필요
            if (inputID.text.isNullOrBlank() || inputPW1.text.isNullOrBlank() ||
                inputName.text.isNullOrBlank() || inputBabyName.text.isNullOrBlank() ||
                inputEmail.text.isNullOrBlank()) {
                Toast.makeText(this@RegisterPage, "모든 입력값을 작성해 주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            // 비밀번호 일치 여부 확인
            if (inputPW1.text.toString() != inputPW2.text.toString()) {
                Toast.makeText(this@RegisterPage, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            try{
                val userDTO = UserDTO(
                    userID = inputID.text.toString(),
                    userPW = inputPW1.text.toString(),
                    name = inputName.text.toString(),
                    babyname=  inputBabyName.text.toString(),
                    preg = 0,
                    email= inputEmail.text.toString()
                )




            CoroutineScope(Dispatchers.IO).launch {

                try{
                    val userApiService = RetrofitInstance.create(UserApiService::class.java)
                    val response = userApiService.registUser(userDTO)
                    Log.d("API_RESPONSE", "Response Code: ${response.code()}, Message: ${response.message()}")

                    if (response.isSuccessful) {
                        // 성공 시 메인 스레드에서 Fragment 전환
                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@RegisterPage, "회원가입 성공!", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this@RegisterPage, wordsPage::class.java)
                            startActivity(intent)
                            finish()
                        }
                    } else {
                        // 실패 시 메인 스레드에서 오류 메시지 표시
                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@RegisterPage, "회원가입 실패: ${response.message()}", Toast.LENGTH_SHORT).show()
                        }
                    }
                } catch (e: Exception) {
                    // 예외 처리
                    Log.e("API_CALL", "Error occurred during API call: ${e.message}", e)
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@RegisterPage, "API 호출 중 오류 발생", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this@RegisterPage, "회원가입 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
        }







        }

        validateChkBtn.setOnClickListener {
            val fullPhoneNumber = "010" + inputPhone2.text.toString() + inputPhone3.text.toString() // 전체 전화번호
            val code = validateNumber.text.toString() // 사용자가 입력한 인증 코드

            if (code.isNotEmpty()) {
                smsService.verifyCode(fullPhoneNumber, code) { success, message ->
                    val builder = if (success) {
                        // 성공 시: 인증번호 확인 메시지 표시
                        Dialog("인증번호 확인", "인증번호가 확인되었습니다.")
                    } else {
                        // 실패 시: 오류 메시지 표시
                        Dialog("인증번호 확인", message ?: "유효하지 않거나 만료된 인증번호입니다.")
                    }
                    builder.show()
                }
            } else {
                // 입력 오류 시: 코드가 비어 있을 경우 알림
                val builder = Dialog("인증번호 확인", "인증번호를 입력해 주세요.")
                builder.show()
            }
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
