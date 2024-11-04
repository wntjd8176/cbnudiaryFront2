package com.example.diaryapp.Serivce

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface DiaryApiService {

    data class DiaryDTO(
        val diaryUserID: String, // 엔티티의 diaryUserID에 매핑
        val dtitle: String,      // 엔티티의 dtitle에 매핑
        val diaryContent: String, // 엔티티의 diaryContent에 매핑
        val createdDate: String,  // 엔티티의 createdDate에 매핑
        val emotions: Int,        // 엔티티의 emotions에 매핑
        val resultEmotion: Int
    )

    @POST("diary/write2")
    suspend fun saveDiary(@Body diaryDTO: DiaryDTO): Response<Void>

    // 일기 목록 조회 엔드포인트
    @GET("diary/diaries")
    suspend fun getDiaryList(): Response<List<DiaryDTO>>

    //캘린더에서 날짜 누르면 일기 보여주는 엔드포인트
   // @GET("diary/{date}")
    //suspend fun getDiaryByDate(@Path("date") date: String): Response<DiaryDTO>


}