package com.example.jc_example_1.AppModule

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.jc_example_1.models.Const
import com.example.jc_example_1.services.AuthService
import com.example.jc_example_1.services.UserService
import com.example.jc_example_1.storage.DTaStoreManager
import com.example.jc_example_1.storage.dataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

class InterceptorBaseUrl @Inject constructor( private  val dataStoreManager : DTaStoreManager ) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()



        val serviceType = originalRequest.header("Service-Type")
        // Lấy URL gốc của request
        val originalUrl = originalRequest.url()

        // Nếu có nhiều domain dựa vào Service-Type để phân chia domain
        // Xác định baseUrl mới dựa vào serviceType
        val newBaseUrl = when (serviceType) {
            Const.AUTH_SERVICE -> HttpUrl.get(Const.BASE_URL_PTI)
            Const.USER_SERVICE -> HttpUrl.get(Const.BASE_URL)
            else -> null // Nếu không có header hoặc không khớp, giữ nguyên URL gốc
        }

        val newUrl = newBaseUrl?.let {
            originalUrl.newBuilder().scheme(it.scheme()).host(it.host()).port(it.port()).build()
        } ?: originalUrl

       val headers = mutableMapOf(
            "Content-Type" to "application/json; charset=UTF-8",
            "lang" to "vi"
        )

        // Dùng runBlocking để lấy accessToken ngay lập tức (Vì Interceptor không hỗ trợ suspend function)
        val accessToken = runBlocking {
            dataStoreManager.getString(Const.ACCESS_TOKEN).first()
        }

        //case specific: chỉ dùng trong call api: /pticare/customers
        if (newUrl.url().path.contains("/pticare/customers") && accessToken.isNotBlank()){
            headers["token-id"] = accessToken
        }else if (accessToken.isNotBlank()){
            headers["Authorization"] = accessToken
        }

        // Xây dựng request mới, loại bỏ header "Service-Type" nếu không cần thiết cho server
        val newRequest =
            originalRequest.newBuilder().url(newUrl).removeHeader("Service-Type")
                .apply {
                    headers.forEach {
                        (k,v) -> addHeader(k,v)
                    }
                }
                .build()


        return chain.proceed(newRequest)
    }


}
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(interceptorBaseUrl: InterceptorBaseUrl): OkHttpClient {
        return  OkHttpClient.Builder()
            .addInterceptor(interceptorBaseUrl)
            .connectTimeout(1000, TimeUnit.SECONDS) // Timeout khi kết nối
            .readTimeout(1000, TimeUnit.SECONDS)    // Timeout khi đọc dữ liệu
            .writeTimeout(1000, TimeUnit.SECONDS)   // Timeout khi ghi dữ liệu
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder().baseUrl(Const.BASE_URL_PTI).client(client)
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