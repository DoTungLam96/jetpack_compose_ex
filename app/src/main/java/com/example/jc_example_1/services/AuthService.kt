package com.example.jc_example_1.services

import com.example.jc_example_1.models.Comment
import com.example.jc_example_1.models.LoginRequest
import com.example.jc_example_1.models.LoginResponse
import com.example.jc_example_1.models.Routes
import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthService {
        @POST("/id-service/public/login/identityCard")
        @Headers("Service-Type: ${Routes.AUTH_SERVICE}")
        suspend fun login(
            @Body request: LoginRequest,
        ): Response<LoginResponse>
}