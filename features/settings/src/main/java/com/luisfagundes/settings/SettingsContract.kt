package com.luisfagundes.settings

data class SettingsUiState(
    val isNightMode: Boolean = false,
)

sealed class SettingsEvent {
    data class OnSaveTheme(
        val isNightMode: Boolean,
    ) : SettingsEvent()
}
