package com.kushal.jarvis

import android.app.Application
import com.kushal.jarvis.data.local.SharedPrefsManager
import com.kushal.jarvis.domain.usecase.JokeUseCase
import com.kushal.jarvis.ui.viewmodel.MainViewModel

class JarvisApplication : Application() {
    companion object {
        lateinit var sharedPrefsManager: SharedPrefsManager
        lateinit var jokeUseCase: JokeUseCase
        lateinit var mainViewModel: MainViewModel
    }

    override fun onCreate() {
        super.onCreate()
        
        // Initialize managers and use cases
        sharedPrefsManager = SharedPrefsManager(this)
        jokeUseCase = JokeUseCase()
        mainViewModel = MainViewModel(sharedPrefsManager, jokeUseCase)
    }
}
