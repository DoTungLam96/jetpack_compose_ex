package com.example.jc_example_1.services

import com.example.jc_example_1.models.Comment
import com.example.jc_example_1.models.Routes
import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface UserService {
    @GET("/comments")
    @Headers("Service-Type: ${Routes.USER_SERVICE}")
    suspend fun getComments(
        @Query("postId") postId: Int
    ): Response<List<Comment>>

}