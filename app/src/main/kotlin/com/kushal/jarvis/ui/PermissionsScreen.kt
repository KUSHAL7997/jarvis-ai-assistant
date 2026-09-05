package com.kushal.jarvis.ui

import android.content.Intent
import android.os.Build
import android.provider.Settings
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PermissionsScreen(
    onPermissionsGranted: () -> Unit
) {
    val context = LocalContext.current
    val isDarkTheme = isSystemInDarkTheme()
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                if (isDarkTheme) Color(0xFF121212) else Color(0xFFF5F5F5)
            )
            .verticalScroll(scrollState)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "🔐 Permissions & Setup",
            fontSize = 28.sp,
            color = Color(0xFF00BCD4),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Text(
            text = "Jarvis needs your permission to function",
            fontSize = 14.sp,
            color = if (isDarkTheme) Color(0xFF9E9E9E) else Color(0xFF999999),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        PermissionCard(
            title = "🎤 Microphone Access",
            description = "Required for voice commands",
            isDarkTheme = isDarkTheme
        )

        PermissionCard(
            title = "🪟 Display Over Other Apps",
            description = "Required for floating overlay",
            isDarkTheme = isDarkTheme,
            action = {
                context.startActivity(Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION))
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onPermissionsGranted,
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF00BCD4)
            )
        ) {
            Text(
                text = "Continue to Home",
                fontSize = 16.sp,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
fun PermissionCard(
    title: String,
    description: String,
    isDarkTheme: Boolean,
    action: (() -> Unit)? = null
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isDarkTheme) Color(0xFF1E1E1E) else Color.White
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = title, fontSize = 14.sp, color = Color(0xFF00BCD4))
                Text(text = description, fontSize = 12.sp, color = if (isDarkTheme) Color(0xFF9E9E9E) else Color(0xFF999999))
            }
            if (action != null) {
                Button(onClick = action, modifier = Modifier.height(36.dp), shape = RoundedCornerShape(6.dp)) {
                    Text("Configure", fontSize = 11.sp)
                }
            }
        }
    }
}
