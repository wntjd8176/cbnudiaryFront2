package com.example.diaryapp.Serivce

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.GET
import retrofit2.http.Query

interface UserApiService {

    data class UserDTO(
        val userID: String, // 엔티티의 diaryUserID에 매핑
        val userPW: String,      // 엔티티의 dtitle에 매핑
        val name: String,
        val babyname: String,
        val preg: Int,
        val email: String


    )
    data class UserStatusResponse(
        val userID: String,
        val prg: Int
    )

    @GET("user/preStatus")
    suspend fun getUserStatus(@Query("userID") userID: String): Response<UserStatusResponse>
    @POST("user/register")
    suspend fun registUser(@Body userDTO: UserApiService.UserDTO): Response<Void>

    @GET("user/checkIDAvailability")
    suspend fun checkIDAvailability(@Query("userID") userID: String): Response<Boolean>
}