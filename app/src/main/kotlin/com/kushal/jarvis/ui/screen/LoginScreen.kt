package com.kushal.jarvis.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LoginScreen(
    onLoginSuccess: (commanderName: String, apiKey: String?) -> Unit,
    isLoading: Boolean = false
) {
    var commanderName by remember { mutableStateOf("") }
    var geminiApiKey by remember { mutableStateOf("") }
    var apiKeyVisible by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    val scrollState = rememberScrollState()
    val isDarkTheme = isSystemInDarkTheme()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                if (isDarkTheme) Color(0xFF121212) else Color(0xFFF5F5F5)
            )
            .verticalScroll(scrollState)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Spacer(modifier = Modifier.height(32.dp))

        // Header
        Text(
            text = "🤖 JARVIS",
            fontSize = 48.sp,
            color = Color(0xFF00BCD4),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Text(
            text = "Android AI Assistant",
            fontSize = 16.sp,
            color = if (isDarkTheme) Color(0xFFBDBDBD) else Color(0xFF666666),
            textAlign = TextAlign.Center
        )

        Text(
            text = "Welcome, Commander",
            fontSize = 14.sp,
            color = if (isDarkTheme) Color(0xFF9E9E9E) else Color(0xFF999999),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Commander Name Field
        OutlinedTextField(
            value = commanderName,
            onValueChange = {
                commanderName = it
                errorMessage = ""
            },
            label = { Text("Commander Name") },
            placeholder = { Text("Enter your name") },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            enabled = !isLoading,
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )

        Divider(
            modifier = Modifier.padding(vertical = 8.dp),
            thickness = 0.5.dp,
            color = if (isDarkTheme) Color(0xFF424242) else Color(0xFFDDDDDD)
        )

        Text(
            text = "Optional: Google Gemini API Key",
            fontSize = 13.sp,
            color = if (isDarkTheme) Color(0xFFB0BEC5) else Color(0xFF78909C),
            modifier = Modifier.align(Alignment.Start)
        )

        Text(
            text = "If left empty, Jarvis will use a default API key for basic features. You can add your own API key for enhanced functionality.",
            fontSize = 11.sp,
            color = if (isDarkTheme) Color(0xFF9E9E9E) else Color(0xFFAAAAAA),
            modifier = Modifier
                .align(Alignment.Start)
                .padding(horizontal = 4.dp),
            textAlign = TextAlign.Justify
        )

        // Gemini API Key Field
        OutlinedTextField(
            value = geminiApiKey,
            onValueChange = {
                geminiApiKey = it
                errorMessage = ""
            },
            label = { Text("Gemini API Key (Optional)") },
            placeholder = { Text("Paste your API key here") },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            enabled = !isLoading,
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            visualTransformation = if (apiKeyVisible)
                VisualTransformation.None
            else
                PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                IconButton(onClick = { apiKeyVisible = !apiKeyVisible }) {
                    Icon(
                        imageVector = if (apiKeyVisible)
                            Icons.Filled.Visibility
                        else
                            Icons.Filled.VisibilityOff,
                        contentDescription = "Toggle API key visibility"
                    )
                }
            }
        )

        // API Key Help Link
        Text(
            text = "Get API Key from Google AI Studio",
            fontSize = 11.sp,
            color = Color(0xFF00BCD4),
            modifier = Modifier
                .align(Alignment.Start)
                .padding(horizontal = 4.dp)
        )

        // Error Message
        if (errorMessage.isNotEmpty()) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                shape = RoundedCornerShape(8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFFFEBEE)
                )
            ) {
                Text(
                    text = errorMessage,
                    fontSize = 12.sp,
                    color = Color(0xFFC62828),
                    modifier = Modifier.padding(12.dp),
                    textAlign = TextAlign.Start
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Login Button
        Button(
            onClick = {
                when {
                    commanderName.trim().isEmpty() -> {
                        errorMessage = "Commander name is required"
                    }
                    commanderName.trim().length < 2 -> {
                        errorMessage = "Commander name must be at least 2 characters"
                    }
                    else -> {
                        onLoginSuccess(
                            commanderName.trim(),
                            if (geminiApiKey.trim().isEmpty()) null else geminiApiKey.trim()
                        )
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp),
            enabled = !isLoading && commanderName.isNotEmpty(),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF00BCD4),
                disabledContainerColor = Color(0xFF80DEEA)
            )
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = Color.White,
                    strokeWidth = 2.dp
                )
            } else {
                Text(
                    text = "Authorize & Launch Jarvis",
                    fontSize = 16.sp,
                    color = Color.White
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Footer Info
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(
                containerColor = if (isDarkTheme) Color(0xFF1E1E1E) else Color(0xFFF9F9F9)
            )
        ) {
            Column(
                modifier = Modifier.padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = "🔒 Your data is encrypted",
                    fontSize = 11.sp,
                    color = if (isDarkTheme) Color(0xFF9E9E9E) else Color(0xFF666666)
                )
                Text(
                    text = "📱 Works offline with voice control",
                    fontSize = 11.sp,
                    color = if (isDarkTheme) Color(0xFF9E9E9E) else Color(0xFF666666)
                )
                Text(
                    text = "⚡ Optimized for Samsung Galaxy A21s",
                    fontSize = 11.sp,
                    color = if (isDarkTheme) Color(0xFF9E9E9E) else Color(0xFF666666)
                )
            }
        }
    }
}
