package com.example.diaryapp.Serivce

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserChartApiService {
    data class UserChartDTO(
        val userID: String, // 엔티티의 diaryUserID에 매핑
        val favoriteWord: String,      // 엔티티의 dtitle에 매핑
        val unfavoriteWord: String
        )

    @POST("user/regUserChart")
    suspend fun registChart(@Body userChartDTO: UserChartApiService.UserChartDTO): Response<Void>
}