package com.kushal.jarvis.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kushal.jarvis.data.local.SharedPrefsManager
import com.kushal.jarvis.domain.usecase.JokeUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val sharedPrefsManager: SharedPrefsManager,
    private val jokeUseCase: JokeUseCase
) : ViewModel() {

    private val _isFirstLaunch = MutableStateFlow(true)
    val isFirstLaunch: StateFlow<Boolean> = _isFirstLaunch

    private val _commanderName = MutableStateFlow<String?>(null)
    val commanderName: StateFlow<String?> = _commanderName

    init {
        loadAuthState()
    }

    fun loadAuthState() {
        val isCompleted = sharedPrefsManager.isFirstLaunchCompleted()
        _isFirstLaunch.value = !isCompleted
        _commanderName.value = sharedPrefsManager.getCommanderName()
    }

    fun loginUser(commanderName: String, apiKey: String?) {
        viewModelScope.launch {
            sharedPrefsManager.setCommanderName(commanderName)
            if (apiKey != null) {
                sharedPrefsManager.setGeminiApiKey(apiKey)
            }
            sharedPrefsManager.setFirstLaunchCompleted(true)
            _isFirstLaunch.value = false
            _commanderName.value = commanderName
        }
    }

    fun logout() {
        viewModelScope.launch {
            sharedPrefsManager.clearAll()
            _isFirstLaunch.value = true
            _commanderName.value = null
        }
    }

    suspend fun fetchRandomJoke(): Result<String> {
        return jokeUseCase.fetchRandomJoke()
    }
}
