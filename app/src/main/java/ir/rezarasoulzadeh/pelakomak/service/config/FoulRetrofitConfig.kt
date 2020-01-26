package ir.rezarasoulzadeh.pelakomak.service.config

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object FoulRetrofitConfig {
    private const val foulUrl = "https://api.farmooon.ir/api/"

    fun retrofit() : Retrofit {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.HEADERS
        val okHttpClient = OkHttpClient.Builder()
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .build()
        return Retrofit
            .Builder()
            .client(okHttpClient)
            .baseUrl(foulUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}