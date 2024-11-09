package com.example.diaryapp.Serivce
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
interface ImageUploadService {
    @Multipart
    @POST("ocr/upload")
    suspend fun uploadImage(
        @Part image: MultipartBody.Part,
        @Part("userID") userID: RequestBody
    ): Response<String>
}