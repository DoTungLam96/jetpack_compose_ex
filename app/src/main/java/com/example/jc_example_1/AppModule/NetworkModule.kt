package com.example.jc_example_1.AppModule

import com.example.jc_example_1.models.Routes
import com.example.jc_example_1.services.AuthService
import com.example.jc_example_1.services.UserService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class InterceptorBaseUrl : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val serviceType = originalRequest.header("Service-Type")
        // Lấy URL gốc của request
        val originalUrl = originalRequest.url()

        // Xác định baseUrl mới dựa vào serviceType
        val newBaseUrl = when (serviceType) {
            Routes.AUTH_SERVICE -> HttpUrl.get(Routes.BASE_URL_PTI)
            Routes.USER_SERVICE -> HttpUrl.get(Routes.BASE_URL)
            else -> null // Nếu không có header hoặc không khớp, giữ nguyên URL gốc
        }

        val newUrl = newBaseUrl?.let {
            originalUrl.newBuilder().scheme(it.scheme()).host(it.host()).port(it.port()).build()
        } ?: originalUrl

        // Xây dựng request mới, loại bỏ header "Service-Type" nếu không cần thiết cho server
        val newRequest =
            originalRequest.newBuilder().url(newUrl).removeHeader("Service-Type").build()

        return chain.proceed(newRequest)
    }


}

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    val okHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(InterceptorBaseUrl())
            .connectTimeout(10, TimeUnit.SECONDS) // Timeout khi kết nối
            .readTimeout(10, TimeUnit.SECONDS)    // Timeout khi đọc dữ liệu
            .writeTimeout(10, TimeUnit.SECONDS)   // Timeout khi ghi dữ liệu
            .build()

    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl(Routes.BASE_URL).client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    @Provides
    fun provideAuthService(retrofit: Retrofit): AuthService {
        return retrofit.create(AuthService::class.java)
    }

    @Provides
    fun provideUserService(retrofit: Retrofit): UserService {
        return retrofit.create(UserService::class.java)
    }
}