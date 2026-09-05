package com.kushal.jarvis.data.remote

import com.google.gson.annotations.SerializedName
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface GeminiApiService {
    @POST("v1beta/models/gemini-1.5-flash:generateContent")
    suspend fun generateContent(
        @Query("key") apiKey: String,
        @Body request: GeminiRequest
    ): GeminiResponse
}

data class GeminiRequest(
    val contents: List<Content>,
    val systemInstruction: SystemInstruction? = null
)

data class Content(
    val parts: List<Part>,
    val role: String = "user"
)

data class Part(
    val text: String
)

data class SystemInstruction(
    val parts: List<Part>
)

data class GeminiResponse(
    val candidates: List<Candidate>?,
    val error: GeminiError?
)

data class Candidate(
    val content: Content?,
    val finishReason: String?,
    @SerializedName("safetyRatings")
    val safetyRatings: List<SafetyRating>?
)

data class SafetyRating(
    val category: String,
    val probability: String
)

data class GeminiError(
    val code: Int,
    val message: String,
    val status: String
)

object GeminiApiClient {
    private const val BASE_URL = "https://generativelanguage.googleapis.com/"

    fun getService(): GeminiApiService {
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

        return retrofit.create(GeminiApiService::class.java)
    }
}
