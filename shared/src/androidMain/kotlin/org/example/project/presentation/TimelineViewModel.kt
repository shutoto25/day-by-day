package org.example.project.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.*

data class DiaryEntryUi(
    val id: String,
    val title: String,
    val content: String,
    val mood: Int?,
    val weather: String,
    val createdAt: Long
)

enum class TimelineFilter(val displayName: String) {
    ALL("すべて"),
    TODAY("今日"),
    THIS_WEEK("今週"),
    THIS_MONTH("今月"),
    THIS_YEAR("今年")
}

data class TimelineUiState(
    val entries: List<DiaryEntryUi> = emptyList(),
    val filteredEntries: List<DiaryEntryUi> = emptyList(),
    val currentFilter: TimelineFilter = TimelineFilter.ALL,
    val isLoading: Boolean = false,
    val showFilterDialog: Boolean = false,
    val selectedEntryId: String? = null,
    val error: String? = null
)

class TimelineViewModel() : ViewModel() {

    private val _uiState = MutableStateFlow(TimelineUiState())
    val uiState: StateFlow<TimelineUiState> = _uiState.asStateFlow()

    fun loadEntries() {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isLoading = true, error = null)
                
                // TODO: Implement actual data loading
                // val entries = getDiaryEntriesUseCase()
                
                // Mock data for demonstration
                val mockEntries = generateMockEntries()
                
                _uiState.value = _uiState.value.copy(
                    entries = mockEntries,
                    filteredEntries = filterEntries(mockEntries, _uiState.value.currentFilter),
                    isLoading = false
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = "データの読み込みに失敗しました"
                )
            }
        }
    }

    fun applyFilter(filter: TimelineFilter) {
        val filteredEntries = filterEntries(_uiState.value.entries, filter)
        _uiState.value = _uiState.value.copy(
            currentFilter = filter,
            filteredEntries = filteredEntries
        )
    }

    fun toggleFilterDialog() {
        _uiState.value = _uiState.value.copy(
            showFilterDialog = !_uiState.value.showFilterDialog
        )
    }

    fun selectEntry(entryId: String) {
        _uiState.value = _uiState.value.copy(
            selectedEntryId = entryId
        )
        // TODO: Navigate to detail screen or handle selection
    }

    fun refreshEntries() {
        loadEntries()
    }

    private fun filterEntries(entries: List<DiaryEntryUi>, filter: TimelineFilter): List<DiaryEntryUi> {
        val now = System.currentTimeMillis()
        val calendar = Calendar.getInstance()
        
        return when (filter) {
            TimelineFilter.ALL -> entries
            
            TimelineFilter.TODAY -> {
                calendar.timeInMillis = now
                calendar.set(Calendar.HOUR_OF_DAY, 0)
                calendar.set(Calendar.MINUTE, 0)
                calendar.set(Calendar.SECOND, 0)
                calendar.set(Calendar.MILLISECOND, 0)
                val startOfDay = calendar.timeInMillis
                
                calendar.add(Calendar.DAY_OF_MONTH, 1)
                val startOfNextDay = calendar.timeInMillis
                
                entries.filter { it.createdAt >= startOfDay && it.createdAt < startOfNextDay }
            }
            
            TimelineFilter.THIS_WEEK -> {
                calendar.timeInMillis = now
                calendar.set(Calendar.DAY_OF_WEEK, calendar.firstDayOfWeek)
                calendar.set(Calendar.HOUR_OF_DAY, 0)
                calendar.set(Calendar.MINUTE, 0)
                calendar.set(Calendar.SECOND, 0)
                calendar.set(Calendar.MILLISECOND, 0)
                val startOfWeek = calendar.timeInMillis
                
                entries.filter { it.createdAt >= startOfWeek }
            }
            
            TimelineFilter.THIS_MONTH -> {
                calendar.timeInMillis = now
                calendar.set(Calendar.DAY_OF_MONTH, 1)
                calendar.set(Calendar.HOUR_OF_DAY, 0)
                calendar.set(Calendar.MINUTE, 0)
                calendar.set(Calendar.SECOND, 0)
                calendar.set(Calendar.MILLISECOND, 0)
                val startOfMonth = calendar.timeInMillis
                
                entries.filter { it.createdAt >= startOfMonth }
            }
            
            TimelineFilter.THIS_YEAR -> {
                calendar.timeInMillis = now
                calendar.set(Calendar.DAY_OF_YEAR, 1)
                calendar.set(Calendar.HOUR_OF_DAY, 0)
                calendar.set(Calendar.MINUTE, 0)
                calendar.set(Calendar.SECOND, 0)
                calendar.set(Calendar.MILLISECOND, 0)
                val startOfYear = calendar.timeInMillis
                
                entries.filter { it.createdAt >= startOfYear }
            }
        }.sortedByDescending { it.createdAt }
    }

    private fun generateMockEntries(): List<DiaryEntryUi> {
        val now = System.currentTimeMillis()
        return listOf(
            DiaryEntryUi(
                id = "1",
                title = "良い一日でした",
                content = "今日は友達と久しぶりに会えて、とても楽しい時間を過ごしました。カフェで話に花が咲いて、あっという間に時間が過ぎていました。",
                mood = 8,
                weather = "晴れ",
                createdAt = now - 86400000 // 1 day ago
            ),
            DiaryEntryUi(
                id = "2",
                title = "新しいことに挑戦",
                content = "今日から新しい習慣を始めました。毎朝のジョギングです。最初は大変でしたが、気持ちよく汗をかけました。",
                mood = 7,
                weather = "曇り",
                createdAt = now - 172800000 // 2 days ago
            ),
            DiaryEntryUi(
                id = "3",
                title = "リラックスな休日",
                content = "のんびりと家で過ごしました。本を読んだり、映画を見たり、とても充実した休日でした。",
                mood = 6,
                weather = "雨",
                createdAt = now - 259200000 // 3 days ago
            )
        )
    }
}