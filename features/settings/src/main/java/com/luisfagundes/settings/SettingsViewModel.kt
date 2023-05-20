package com.luisfagundes.settings

import com.luisfagundes.framework.base.BaseViewModel
import com.luisfagundes.provider.ThemeProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val themeProvider: ThemeProvider,
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState = _uiState.asStateFlow()

    init {
        updateIsNightModeState()
    }

    fun onEvent(event: SettingsEvent) {
        when (event) {
            is SettingsEvent.OnSaveTheme -> {
                saveThemeMode(
                    isNightMode = event.isNightMode,
                )
            }
        }
    }

    private fun saveThemeMode(isNightMode: Boolean) {
        themeProvider.theme = if (isNightMode) {
            ThemeProvider.Theme.DARK
        } else {
            ThemeProvider.Theme.LIGHT
        }
        updateIsNightModeState()
    }

    private fun updateIsNightModeState() {
        _uiState.update { state ->
            state.copy(isNightMode = themeProvider.isNightMode())
        }
    }
}
