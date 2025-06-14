package org.example.project.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class AddEntryUiState(
    val title: String = "",
    val content: String = "",
    val mood: String = "",
    val weather: String = "",
    val titleError: String? = null,
    val contentError: String? = null,
    val isLoading: Boolean = false,
    val isEntrySaved: Boolean = false,
    val canSave: Boolean = false
)

class AddEntryViewModel(
    // TODO: Inject repository and use cases (Koinでmoduleに登録する)
    // private val saveDiaryEntryUseCase: SaveDiaryEntryUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddEntryUiState())
    val uiState: StateFlow<AddEntryUiState> = _uiState.asStateFlow()

    fun updateTitle(title: String) {
        _uiState.value = _uiState.value.copy(
            title = title,
            titleError = if (title.isBlank()) "タイトルを入力してください" else null
        )
        updateCanSave()
    }

    fun updateContent(content: String) {
        _uiState.value = _uiState.value.copy(
            content = content,
            contentError = if (content.isBlank()) "内容を入力してください" else null
        )
        updateCanSave()
    }

    fun updateMood(mood: String) {
        val moodInt = mood.toIntOrNull()
        val isValid = moodInt != null && moodInt in 1..10
        
        _uiState.value = _uiState.value.copy(
            mood = mood
        )
        updateCanSave()
    }

    fun updateWeather(weather: String) {
        _uiState.value = _uiState.value.copy(
            weather = weather
        )
    }

    fun saveEntry() {
        if (!_uiState.value.canSave) return

        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isLoading = true)
                
                // TODO: Implement actual save logic
                // val entry = DiaryEntry(
                //     title = _uiState.value.title,
                //     content = _uiState.value.content,
                //     mood = _uiState.value.mood.toIntOrNull() ?: 5,
                //     weather = _uiState.value.weather,
                //     createdAt = System.currentTimeMillis()
                // )
                // saveDiaryEntryUseCase(entry)
                
                // Simulate network delay
                delay(1000)
                
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    isEntrySaved = true
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false
                )
                // TODO: Handle error
            }
        }
    }

    private fun updateCanSave() {
        val state = _uiState.value
        _uiState.value = state.copy(
            canSave = state.title.isNotBlank() && 
                     state.content.isNotBlank() && 
                     state.titleError == null && 
                     state.contentError == null &&
                     !state.isLoading
        )
    }
}