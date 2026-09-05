package com.kushal.jarvis.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.content.ContextCompat
import com.kushal.jarvis.JarvisApplication
import com.kushal.jarvis.ui.screen.HomeScreen
import com.kushal.jarvis.ui.screen.LoginScreen
import com.kushal.jarvis.ui.theme.JarvisTheme
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    private val viewModel = JarvisApplication.mainViewModel
    private val requiredPermissions = mutableListOf(
        Manifest.permission.INTERNET,
        Manifest.permission.RECORD_AUDIO
    ).apply {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            add(Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            JarvisTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = Color.Transparent) {
                    val isFirstLaunch by viewModel.isFirstLaunch.collectAsState()
                    val commanderName by viewModel.commanderName.collectAsState()

                    when {
                        isFirstLaunch -> {
                            LoginScreen(onLoginSuccess = { name, apiKey ->
                                viewModel.loginUser(name, apiKey)
                                requestRequiredPermissions()
                            })
                        }
                        commanderName != null -> {
                            HomeScreen(
                                commanderName = commanderName!!,
                                onFetchJoke = { viewModel.fetchRandomJoke() },
                                onSettingsClick = { },
                                onLogout = { viewModel.logout() }
                            )
                        }
                    }
                }
            }
        }
    }

    private fun requestRequiredPermissions() {
        val missingPermissions = requiredPermissions.filter {
            ContextCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED
        }.toTypedArray()

        if (missingPermissions.isNotEmpty()) {
            permissionLauncher.launch(missingPermissions)
        }
    }
}
