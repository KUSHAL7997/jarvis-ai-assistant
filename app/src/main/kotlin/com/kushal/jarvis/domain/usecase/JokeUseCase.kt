package com.kushal.jarvis.domain.usecase

import com.kushal.jarvis.data.remote.JokeApiClient
import com.kushal.jarvis.data.remote.JokeResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class JokeUseCase {
    private val jokeService = JokeApiClient.getService()

    suspend fun fetchRandomJoke(): Result<String> = withContext(Dispatchers.IO) {
        return@withContext try {
            val response = jokeService.getRandomJoke().execute()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.success(body.data?.joke ?: "Failed to parse joke")
                } else {
                    Result.failure(Exception("Empty response body"))
                }
            } else {
                Result.failure(Exception("API Error: ${response.code()} - ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
