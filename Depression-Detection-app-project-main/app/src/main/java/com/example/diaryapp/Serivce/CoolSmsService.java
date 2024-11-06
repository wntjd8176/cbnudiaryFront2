package com.example.diaryapp.Serivce;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface CoolSmsService {


    @POST("/sms/send")
    Call<String> sendSms(@Body Map<String, String> body);

    @POST("/sms/verify")
    Call<Map<String,String>> verifyCode(@Body Map<String, String> body);

}
