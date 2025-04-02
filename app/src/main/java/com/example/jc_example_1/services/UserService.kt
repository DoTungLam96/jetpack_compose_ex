package com.example.jc_example_1.services

import com.example.jc_example_1.models.Comment
import com.example.jc_example_1.models.Const
import com.example.jc_example_1.models.ContactModel
import com.example.jc_example_1.models.User
import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface UserService {
    @GET("/comments")
    @Headers("Service-Type: ${Const.USER_SERVICE}")
    suspend fun getComments(
        @Query("postId") postId: Int
    ): Response<List<Comment>>

    @GET("/pticare/customers")
    @Headers(
        "User-Agent: DLife",
    )
    suspend fun getUserInfo(
    ): Response<User?>

    @GET("/pticare/customers/contact")
    suspend fun getContact(
        @Query("identityNo") identityNo: String
    ):Response<ContactModel?>

}