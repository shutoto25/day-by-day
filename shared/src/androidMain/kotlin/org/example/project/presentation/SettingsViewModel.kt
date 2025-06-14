package org.example.project.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class SettingsUiState(
    // Notification Settings
    val notificationEnabled: Boolean = false,
    val reminderTime: String = "20:00",
    
    // Privacy Settings
    val passcodeEnabled: Boolean = false,
    val biometricEnabled: Boolean = false,
    
    // Backup Settings
    val autoBackupEnabled: Boolean = false,
    val lastBackupTime: String = "未実行",
    
    // Display Settings
    val currentTheme: String = "システム",
    val fontSize: String = "標準",
    
    // App Info
    val appVersion: String = "1.0.0",
    
    // Dialog States
    val showTimePickerDialog: Boolean = false,
    val showThemeDialog: Boolean = false,
    val showFontSizeDialog: Boolean = false,
    val showAboutDialog: Boolean = false,
    val showRestoreDialog: Boolean = false,
    
    // Loading States
    val isBackingUp: Boolean = false,
    val isRestoring: Boolean = false,
    
    // Error State
    val error: String? = null
)

class SettingsViewModel () : ViewModel() {

    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    fun loadSettings() {
        viewModelScope.launch {
            try {
                // TODO: Load actual settings from repository
                // val settings = settingsRepository.getSettings()
                
                // Mock data for demonstration
                _uiState.value = _uiState.value.copy(
                    notificationEnabled = true,
                    reminderTime = "20:00",
                    passcodeEnabled = false,
                    biometricEnabled = false,
                    autoBackupEnabled = true,
                    currentTheme = "システム",
                    fontSize = "標準"
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = "設定の読み込みに失敗しました"
                )
            }
        }
    }

    // Notification Settings
    fun updateNotificationEnabled(enabled: Boolean) {
        _uiState.value = _uiState.value.copy(notificationEnabled = enabled)
        saveSettings()
    }

    fun updateReminderTime(time: String) {
        _uiState.value = _uiState.value.copy(reminderTime = time)
        saveSettings()
    }

    fun showTimePickerDialog() {
        _uiState.value = _uiState.value.copy(showTimePickerDialog = true)
    }

    fun hideTimePickerDialog() {
        _uiState.value = _uiState.value.copy(showTimePickerDialog = false)
    }

    // Privacy Settings
    fun updatePasscodeEnabled(enabled: Boolean) {
        _uiState.value = _uiState.value.copy(
            passcodeEnabled = enabled,
            biometricEnabled = if (!enabled) false else _uiState.value.biometricEnabled
        )
        saveSettings()
    }

    fun updateBiometricEnabled(enabled: Boolean) {
        _uiState.value = _uiState.value.copy(biometricEnabled = enabled)
        saveSettings()
    }

    // Backup Settings
    fun updateAutoBackupEnabled(enabled: Boolean) {
        _uiState.value = _uiState.value.copy(autoBackupEnabled = enabled)
        saveSettings()
    }

    fun manualBackup() {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isBackingUp = true, error = null)
                
                // TODO: Implement actual backup
                // backupUseCase.execute()
                
                // Simulate backup process
                delay(2000)
                
                val currentTime = SimpleDateFormat(
                    "yyyy/MM/dd HH:mm", 
                    Locale.getDefault()
                ).format(Date())
                
                _uiState.value = _uiState.value.copy(
                    isBackingUp = false,
                    lastBackupTime = currentTime
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isBackingUp = false,
                    error = "バックアップに失敗しました"
                )
            }
        }
    }

    fun showRestoreDialog() {
        _uiState.value = _uiState.value.copy(showRestoreDialog = true)
    }

    fun hideRestoreDialog() {
        _uiState.value = _uiState.value.copy(showRestoreDialog = false)
    }

    fun restoreFromBackup() {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(
                    isRestoring = true,
                    showRestoreDialog = false,
                    error = null
                )
                
                // TODO: Implement actual restore
                // restoreUseCase.execute()
                
                // Simulate restore process
                delay(3000)
                
                _uiState.value = _uiState.value.copy(isRestoring = false)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isRestoring = false,
                    error = "復元に失敗しました"
                )
            }
        }
    }

    // Display Settings
    fun updateTheme(theme: String) {
        _uiState.value = _uiState.value.copy(currentTheme = theme)
        saveSettings()
    }

    fun showThemeDialog() {
        _uiState.value = _uiState.value.copy(showThemeDialog = true)
    }

    fun hideThemeDialog() {
        _uiState.value = _uiState.value.copy(showThemeDialog = false)
    }

    fun updateFontSize(fontSize: String) {
        _uiState.value = _uiState.value.copy(fontSize = fontSize)
        saveSettings()
    }

    fun showFontSizeDialog() {
        _uiState.value = _uiState.value.copy(showFontSizeDialog = true)
    }

    fun hideFontSizeDialog() {
        _uiState.value = _uiState.value.copy(showFontSizeDialog = false)
    }

    // Other Actions
    fun showAboutDialog() {
        _uiState.value = _uiState.value.copy(showAboutDialog = true)
    }

    fun hideAboutDialog() {
        _uiState.value = _uiState.value.copy(showAboutDialog = false)
    }

    fun sendFeedback() {
        // TODO: Implement feedback functionality
        // Launch email app or feedback form
    }

    fun shareApp() {
        // TODO: Implement app sharing functionality
        // Launch share intent
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }

    private fun saveSettings() {
        viewModelScope.launch {
            try {
                // TODO: Save settings to repository
                // settingsRepository.saveSettings(_uiState.value.toSettings())
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = "設定の保存に失敗しました"
                )
            }
        }
    }
}