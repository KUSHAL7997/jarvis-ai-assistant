package com.kushal.jarvis.data.remote

import com.google.gson.JsonObject
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface JokeApiService {
    @GET("jokes/random")
    fun getRandomJoke(): Call<JokeResponse>
}

data class JokeResponse(
    val success: Boolean,
    val data: JokeData?
)

data class JokeData(
    val joke: String,
    val type: String
)

object JokeApiClient {
    private const val BASE_URL = "https://official-joke-api.appspot.com/"

    fun getService(): JokeApiService {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val httpClient = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(JokeApiService::class.java)
    }
}
