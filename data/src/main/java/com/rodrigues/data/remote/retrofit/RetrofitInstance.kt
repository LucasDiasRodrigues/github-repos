package com.rodrigues.data.remote.retrofit

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.rodrigues.data.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitInstance {

    val retrofit: Retrofit by lazy {
        builder()
    }

    private fun builder(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(initOkHttpClient())
            .build()
    }

//    private fun initGson(): Gson {
//        return GsonBuilder().setLenient().create()
//    }

    private fun initOkHttpClient(): OkHttpClient {
        val client = OkHttpClient.Builder()
        client.addInterceptor(loggingInterceptor())

        client.readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)

        return client.build()
    }

    private fun loggingInterceptor(): HttpLoggingInterceptor {
        val logger = HttpLoggingInterceptor()
        logger.level = BuildConfig.LOG_LEVEL
        return logger
    }
}