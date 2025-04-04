package com.example.jc_example_1.repository

import ApiResult
import android.util.Log
import com.example.jc_example_1.models.LoginRequest
import com.example.jc_example_1.models.LoginResponse
import com.example.jc_example_1.services.AuthService
import javax.inject.Inject

interface AuthRepository {
    suspend fun login(loginRequest: LoginRequest): ApiResult<LoginResponse?>

}

class AuthRepositoryImpl @Inject constructor(private val authService: AuthService) :
    AuthRepository {

    override suspend fun login(loginRequest: LoginRequest): ApiResult<LoginResponse?> {

        return try {
            val response = authService.login(loginRequest)

            if (response.isSuccessful) {

                if((response.body()?.code ?: 0) > 300){
                  return  ApiResult.Error(response.code(),  response.body()?.description)
                }
                return  ApiResult.Success(response.body())
            } else {
                // Log hoặc xử lý theo từng mã lỗi (ví dụ 403 → Unauthorized)
                Log.e("API_ERROR", "Code: ${response.code()}, Message: ${response.message()}")
                ApiResult.Error(response.code(), response.message())
            }
        } catch (e: Exception) {
            ApiResult.Error(-1, "")
        }
    }
}