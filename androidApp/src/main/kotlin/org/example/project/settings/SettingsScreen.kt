package org.example.project.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    
    LaunchedEffect(Unit) {
        viewModel.loadSettings()
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopAppBar(
            title = { Text("設定") }
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                SettingsSection(title = "通知設定") {
                    SettingsToggleItem(
                        icon = Icons.Default.Notifications,
                        title = "日記リマインダー",
                        description = "毎日決まった時間に日記を書くリマインダーを送信",
                        checked = uiState.notificationEnabled,
                        onCheckedChange = { viewModel.updateNotificationEnabled(it) }
                    )
                    
                    if (uiState.notificationEnabled) {
                        SettingsClickableItem(
                            icon = Icons.Default.Schedule,
                            title = "リマインダー時間",
                            description = uiState.reminderTime,
                            onClick = { viewModel.showTimePickerDialog() }
                        )
                    }
                }
            }

            item {
                SettingsSection(title = "プライバシー") {
                    SettingsToggleItem(
                        icon = Icons.Default.Lock,
                        title = "パスコードロック",
                        description = "アプリ起動時にパスコードで保護",
                        checked = uiState.passcodeEnabled,
                        onCheckedChange = { viewModel.updatePasscodeEnabled(it) }
                    )
                    
                    SettingsToggleItem(
                        icon = Icons.Default.Fingerprint,
                        title = "生体認証",
                        description = "指紋認証または顔認証でロック解除",
                        checked = uiState.biometricEnabled,
                        onCheckedChange = { viewModel.updateBiometricEnabled(it) },
                        enabled = uiState.passcodeEnabled
                    )
                }
            }

            item {
                SettingsSection(title = "バックアップ") {
                    SettingsToggleItem(
                        icon = Icons.Default.CloudSync,
                        title = "自動バックアップ",
                        description = "クラウドに日記データを自動保存",
                        checked = uiState.autoBackupEnabled,
                        onCheckedChange = { viewModel.updateAutoBackupEnabled(it) }
                    )
                    
                    SettingsClickableItem(
                        icon = Icons.Default.Backup,
                        title = "今すぐバックアップ",
                        description = "手動でデータをバックアップ",
                        onClick = { viewModel.manualBackup() }
                    )
                    
                    SettingsClickableItem(
                        icon = Icons.Default.Restore,
                        title = "復元",
                        description = "バックアップからデータを復元",
                        onClick = { viewModel.showRestoreDialog() }
                    )
                }
            }

            item {
                SettingsSection(title = "表示設定") {
                    SettingsClickableItem(
                        icon = Icons.Default.Palette,
                        title = "テーマ",
                        description = uiState.currentTheme,
                        onClick = { viewModel.showThemeDialog() }
                    )
                    
                    SettingsClickableItem(
                        icon = Icons.Default.TextFields,
                        title = "フォントサイズ",
                        description = uiState.fontSize,
                        onClick = { viewModel.showFontSizeDialog() }
                    )
                }
            }

            item {
                SettingsSection(title = "その他") {
                    SettingsClickableItem(
                        icon = Icons.Default.Info,
                        title = "アプリについて",
                        description = "バージョン ${uiState.appVersion}",
                        onClick = { viewModel.showAboutDialog() }
                    )
                    
                    SettingsClickableItem(
                        icon = Icons.Default.Feedback,
                        title = "フィードバック",
                        description = "アプリの改善にご協力ください",
                        onClick = { viewModel.sendFeedback() }
                    )
                    
                    SettingsClickableItem(
                        icon = Icons.Default.Share,
                        title = "アプリを共有",
                        description = "友達にアプリを紹介する",
                        onClick = { viewModel.shareApp() }
                    )
                }
            }
        }
    }

    // Dialogs
    if (uiState.showTimePickerDialog) {
        TimePickerDialog(
            currentTime = uiState.reminderTime,
            onTimeSelected = { time -> 
                viewModel.updateReminderTime(time)
                viewModel.hideTimePickerDialog()
            },
            onDismiss = { viewModel.hideTimePickerDialog() }
        )
    }

    if (uiState.showThemeDialog) {
        ThemeSelectionDialog(
            currentTheme = uiState.currentTheme,
            onThemeSelected = { theme ->
                viewModel.updateTheme(theme)
                viewModel.hideThemeDialog()
            },
            onDismiss = { viewModel.hideThemeDialog() }
        )
    }
}

@Composable
private fun SettingsSection(
    title: String,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
            content()
        }
    }
}

@Composable
private fun SettingsToggleItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    description: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    enabled: Boolean = true
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = if (enabled) MaterialTheme.colorScheme.onSurface 
                  else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
        )
        
        Spacer(modifier = Modifier.width(16.dp))
        
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                color = if (enabled) MaterialTheme.colorScheme.onSurface
                       else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
            )
            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            enabled = enabled
        )
    }
}

@Composable
private fun SettingsClickableItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    description: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
        
        Spacer(modifier = Modifier.width(16.dp))
        
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        
        Icon(
            imageVector = Icons.Default.ChevronRight,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun TimePickerDialog(
    currentTime: String,
    onTimeSelected: (String) -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("リマインダー時間") },
        text = {
            // TODO: Implement actual time picker
            Text("時間選択の実装が必要です")
        },
        confirmButton = {
            TextButton(onClick = { onTimeSelected("20:00") }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("キャンセル")
            }
        }
    )
}

@Composable
private fun ThemeSelectionDialog(
    currentTheme: String,
    onThemeSelected: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val themes = listOf("ライト", "ダーク", "システム")
    
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("テーマ選択") },
        text = {
            Column {
                themes.forEach { theme ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickable { onThemeSelected(theme) }
                    ) {
                        RadioButton(
                            selected = currentTheme == theme,
                            onClick = { onThemeSelected(theme) }
                        )
                        Text(
                            text = theme,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("OK")
            }
        }
    )
}