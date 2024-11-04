package com.example.diaryapp.Network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {



            private const val BASE_URL = "http://150.230.251.172:8080/" // 실제 백엔드 URL로 변경
    private const val Local_URL = "http://10.0.2.2:8080/"
        private val retrofit by lazy {
            Retrofit.Builder()
                .baseUrl(Local_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        fun <T> create(service: Class<T>): T {
            return retrofit.create(service)
        }
    }
