package com.example.diaryapp.Serivce
import com.example.diaryapp.HotPostItem
import com.example.diaryapp.CommentItem
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.GET
import retrofit2.Call
import retrofit2.http.Path
interface PostApiService {
    data class PostRequest(
        val userID: String,
        val ptitle: String,
        val pContent: String,
        val postType: String // "normal" or "photo"
    )
    data class CommentRequest(
        val ptitle: String,
        val userId: String,
        val comment: String
    )

    @POST("community/writepost")
    fun createPost(@Body postRequest: PostRequest): Call<Void>

    @GET("community/readposts") // 백엔드에서 게시글 데이터를 제공하는 엔드포인트
    fun getAllPosts(): Call<List<HotPostItem>>
    @GET("comments/{ptitle}")
    fun getCommentsForPost(@Path("ptitle") postId: String): Call<List<CommentItem>>

    @POST("comments/add")
    fun addComment(@Body commentRequest: CommentRequest): Call<Void>

    @GET("community/readposts")
    fun getHotPosts(): Call<List<HotPostItem>>


}