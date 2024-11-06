package com.example.diaryapp.Network

import com.example.diaryapp.Serivce.CoolSmsService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
class SmsService(private val smsApi: CoolSmsService) {

    fun verifyCode(phoneNumber: String, code: String, onResult: (Boolean, String?) -> Unit) {
        val body = mapOf("phoneNumber" to phoneNumber, "code" to code)

        smsApi.verifyCode(body).enqueue(object : Callback<Map<String, String>> {
            override fun onResponse(call: Call<Map<String, String>>, response: Response<Map<String, String>>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    val status = responseBody?.get("status")
                    val message = responseBody?.get("message")

                    if (status == "success") {
                        onResult(true, message)
                    } else {
                        onResult(false, message ?: "유효하지 않거나 만료된 인증번호입니다.")
                    }
                } else {
                    onResult(false, "서버 오류가 발생했습니다.")
                }
            }

            override fun onFailure(call: Call<Map<String, String>>, t: Throwable) {
                onResult(false, t.message)
            }
        })
    }

    fun sendSms(phoneNumber: String, onResult: (Boolean, String?) -> Unit) {
        // 요청에 필요한 파라미터를 Map으로 생성
        val body = mapOf("phoneNumber" to phoneNumber)

        // Retrofit으로 API 호출
        smsApi.sendSms(body).enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful && response.body() != null) {
                    // 성공 시 콜백으로 true와 성공 메시지를 전달
                    onResult(true, "인증 코드가 전송되었습니다.")
                } else {
                    // 실패 시 콜백으로 false와 실패 메시지를 전달
                    onResult(false, "인증 코드 전송에 실패했습니다.")
                }
            }



            override fun onFailure(call: Call<String>, t: Throwable) {
                // 네트워크 오류 등으로 인한 실패 시 콜백으로 false와 오류 메시지를 전달
                onResult(false, t.message)
            }
        })
    }
}