package com.kushal.jarvis.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    commanderName: String,
    onFetchJoke: suspend () -> Result<String>,
    onSettingsClick: () -> Unit,
    onLogout: () -> Unit
) {
    var jokeText by remember { mutableStateOf("Tap below to fetch a hilarious joke! 😄") }
    var isLoading by remember { mutableStateOf(false) }
    var jokeCount by remember { mutableStateOf(0) }
    var showCopySnackbar by remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()
    val isDarkTheme = isSystemInDarkTheme()
    val scope = rememberCoroutineScope()
    val clipboardManager = LocalClipboardManager.current

    fun fetchJoke() {
        scope.launch {
            isLoading = true
            val result = onFetchJoke()
            result.onSuccess { joke ->
                jokeText = joke
                jokeCount++
            }
            result.onFailure { error ->
                jokeText = "❌ Error: ${error.message ?: "Failed to fetch joke"}"
            }
            isLoading = false
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                if (isDarkTheme) Color(0xFF121212) else Color(0xFFF5F5F5)
            )
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Top App Bar
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding(),
            color = if (isDarkTheme) Color(0xFF1E1E1E) else Color.White,
            shadowElevation = 4.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "🤖 JARVIS",
                        fontSize = 20.sp,
                        color = Color(0xFF00BCD4),
                        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                    )
                    Text(
                        text = "Welcome, $commanderName",
                        fontSize = 12.sp,
                        color = if (isDarkTheme) Color(0xFFB0BEC5) else Color(0xFF78909C)
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    IconButton(onClick = onSettingsClick) {
                        Icon(
                            imageVector = Icons.Filled.Settings,
                            contentDescription = "Settings",
                            tint = Color(0xFF00BCD4)
                        )
                    }
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Stats Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (isDarkTheme) Color(0xFF1E1E1E) else Color.White
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = jokeCount.toString(),
                            fontSize = 28.sp,
                            color = Color(0xFF00BCD4),
                            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                        )
                        Text(
                            text = "Jokes Fetched",
                            fontSize = 12.sp,
                            color = if (isDarkTheme) Color(0xFF9E9E9E) else Color(0xFF999999)
                        )
                    }
                }
            }

            // Joke Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (isDarkTheme) Color(0xFF1E1E1E) else Color.White
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "😂 Joke Generator",
                        fontSize = 16.sp,
                        color = Color(0xFF00BCD4),
                        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                    )

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                color = if (isDarkTheme) Color(0xFF0D1117) else Color(0xFFF5F5F5),
                                shape = RoundedCornerShape(8.dp)
                            ),
                        shape = RoundedCornerShape(8.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (isDarkTheme) Color(0xFF0D1117) else Color(0xFFF5F5F5)
                        )
                    ) {
                        Text(
                            text = jokeText,
                            fontSize = 14.sp,
                            color = if (isDarkTheme) Color(0xFFE0E0E0) else Color(0xFF333333),
                            modifier = Modifier.padding(12.dp),
                            textAlign = TextAlign.Center,
                            lineHeight = 20.sp
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = { fetchJoke() },
                            modifier = Modifier
                                .weight(1f)
                                .height(44.dp),
                            enabled = !isLoading,
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF00BCD4)
                            )
                        ) {
                            if (isLoading) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(20.dp),
                                    color = Color.White,
                                    strokeWidth = 2.dp
                                )
                            } else {
                                Icon(
                                    imageVector = Icons.Filled.Refresh,
                                    contentDescription = "Fetch Joke",
                                    modifier = Modifier.size(18.dp),
                                    tint = Color.White
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("New Joke")
                            }
                        }

                        OutlinedButton(
                            onClick = {
                                clipboardManager.setText(AnnotatedString(jokeText))
                                showCopySnackbar = true
                            },
                            modifier = Modifier
                                .weight(1f)
                                .height(44.dp),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.ContentCopy,
                                contentDescription = "Copy",
                                modifier = Modifier.size(18.dp),
                                tint = Color(0xFF00BCD4)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Copy")
                        }
                    }
                }
            }

            // Info Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (isDarkTheme) Color(0xFF1E1E1E) else Color.White
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Icon(
                        imageVector = Icons.Filled.Info,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp),
                        tint = Color(0xFF00BCD4)
                    )
                    Column {
                        Text(
                            text = "API Information",
                            fontSize = 12.sp,
                            color = Color(0xFF00BCD4),
                            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                        )
                        Text(
                            text = "Jokes powered by Official Joke API. Fetches random jokes without offline fallback.",
                            fontSize = 11.sp,
                            color = if (isDarkTheme) Color(0xFF9E9E9E) else Color(0xFF999999),
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Logout Button
            OutlinedButton(
                onClick = onLogout,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(44.dp),
                shape = RoundedCornerShape(8.dp),
                colors = OutlinedButtonDefaults.outlinedButtonColors(
                    contentColor = Color(0xFFEF5350)
                )
            ) {
                Text("Logout")
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}
