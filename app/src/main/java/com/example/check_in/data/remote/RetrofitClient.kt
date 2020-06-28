package com.example.check_in.data.remote

import com.example.check_in.until.token
import okhttp3.OkHttpClient
import okhttp3.Protocol
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {
    private var retrofit: Retrofit? = null
    fun getClient(baseUrl: String): Retrofit? {
        if (retrofit == null) {
            val okHttpClient = OkHttpClient.Builder()
                .readTimeout(10000, TimeUnit.MILLISECONDS)
                .writeTimeout(10000, TimeUnit.MILLISECONDS)
                .connectTimeout(10000, TimeUnit.MILLISECONDS)
                .protocols(listOf(Protocol.HTTP_1_1)) // chua hieu
                .addInterceptor { chain ->

                    val original = chain.request()

                    // Request customization: add request headers
                    val requestBuilder = original.newBuilder()
                        .header("Authorization", token) // <-- this is the important line

                    val request = requestBuilder.build()
                    chain.proceed(request)
                }
                .build()

            retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        return retrofit
    }
}