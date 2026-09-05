package com.kushal.jarvis.domain.usecase

import com.kushal.jarvis.data.remote.Content
import com.kushal.jarvis.data.remote.GeminiApiClient
import com.kushal.jarvis.data.remote.GeminiRequest
import com.kushal.jarvis.data.remote.Part
import com.kushal.jarvis.data.remote.SystemInstruction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GeminiUseCase {
    private val geminiService = GeminiApiClient.getService()

    suspend fun generateText(
        apiKey: String,
        prompt: String,
        systemPrompt: String = "You are Jarvis, a helpful Android AI assistant."
    ): Result<String> = withContext(Dispatchers.IO) {
        return@withContext try {
            val request = GeminiRequest(
                contents = listOf(
                    Content(
                        parts = listOf(Part(text = prompt)),
                        role = "user"
                    )
                ),
                systemInstruction = SystemInstruction(
                    parts = listOf(Part(text = systemPrompt))
                )
            )

            val response = geminiService.generateContent(apiKey, request)

            if (response.error != null) {
                Result.failure(Exception("Gemini Error: ${response.error.message}"))
            } else if (response.candidates?.isNotEmpty() == true) {
                val firstCandidate = response.candidates.first()
                val text = firstCandidate.content?.parts?.firstOrNull()?.text
                    ?: "No response generated"
                Result.success(text)
            } else {
                Result.failure(Exception("No candidates in response"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
