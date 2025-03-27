package com.example.jc_example_1.AppModule

import com.example.jc_example_1.models.Routes
import com.example.jc_example_1.services.AuthService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS) // Timeout khi kết nối
        .readTimeout(10, TimeUnit.SECONDS)    // Timeout khi đọc dữ liệu
        .writeTimeout(10, TimeUnit.SECONDS)   // Timeout khi ghi dữ liệu
        .build()

    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Routes.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideAuthService(retrofit: Retrofit): AuthService {
        return retrofit.create(AuthService::class.java)
    }
}