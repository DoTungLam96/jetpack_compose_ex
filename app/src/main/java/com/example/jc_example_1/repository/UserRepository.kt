package com.example.jc_example_1.repository

import ApiResult
import android.util.Log
import com.example.jc_example_1.models.Comment
import com.example.jc_example_1.models.LoginRequest
import com.example.jc_example_1.models.User
import com.example.jc_example_1.services.AuthService
import com.example.jc_example_1.services.UserService
import javax.inject.Inject

interface UserRepository {
    suspend fun getComments(postId: Int): ApiResult<List<Comment>?>

    suspend fun getUserInfo(): ApiResult<User?>
}

class UserRepositoryImpl @Inject constructor(private val userService: UserService) :
    UserRepository {
    override suspend fun getComments(postId: Int): ApiResult<List<Comment>?> {
        return try {
            val response = userService.getComments(postId)
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
            ApiResult.Error(-1, "")
        }
    }

    override suspend fun getUserInfo(): ApiResult<User?> {
        return try {
            val res = userService.getUserInfo()
            if (res.isSuccessful) {
                ApiResult.Success(res.body())
            } else {
                ApiResult.Error(res.code(), res.message())
            }
        } catch (e: Exception) {
            ApiResult.Error(-1, "")
        }
    }

}