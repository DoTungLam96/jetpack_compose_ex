package com.example.jc_example_1.repository

import ApiResult
import android.util.Log
import com.example.jc_example_1.models.Comment
import com.example.jc_example_1.models.User
import com.example.jc_example_1.services.AuthService
import kotlinx.coroutines.delay
import javax.inject.Inject


// data/AuthRepository.kt
interface AuthRepository {
    suspend fun login(username: String, password: String): User

    suspend fun getComments(postId: Int): ApiResult<List<Comment>?>
}

class AuthRepositoryImpl @Inject constructor(    private val authService: AuthService) : AuthRepository {

    override suspend fun login(username: String, password: String): User {
        delay(1000)
        return if (username == "admin" && password == "1") {
            User(id = "1", name = username)
        } else {
            throw Exception("Invalid credentials")
        }
    }

    override suspend fun getComments(postId: Int): ApiResult<List<Comment>?> {
        return try {
            val response = authService.getComments(postId)
            if (response.isSuccessful) {
                ApiResult.Success(response.body())
            } else {
                // Log hoặc xử lý theo từng mã lỗi (ví dụ 403 → Unauthorized)
                Log.e("API_ERROR", "Code: ${response.code()}, Message: ${response.message()}")
                ApiResult.Error(response.code(), response.message())
            }
        } catch (e: Exception) {

            // Xử lý lỗi mạng, timeout, JSON lỗi, v.v.
            Log.e("API_ERROR", "Lỗi gọi API: ${e.message}")
            ApiResult.Error(0,"")
        }
    }
}