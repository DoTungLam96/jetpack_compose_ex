package com.example.jc_example_1.services

import com.example.jc_example_1.models.Comment
import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthService {
    // Định nghĩa AuthService với Retrofit annotations
        @POST("/api/login")
        suspend fun login(@Body request: Map<String, String>): Response<JsonObject>
        @GET("/comments")
        suspend fun getComments(
            @Query("postId") postId: Int
        ): Response<List<Comment>>

}