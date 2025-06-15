package org.example.project.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import org.example.project.data.DiaryData
import org.example.project.domain.GetAllDiariesUseCase
import org.example.project.util.DateUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * ホーム画面の状態
 */
data class DiaryHomeState(
    val diaryDataList: List<DiaryData> = emptyList(),
    val isLoading: Boolean = false
)

sealed interface DiaryHomeEvent {
    data object Load : DiaryHomeEvent
}

/**
 * ホーム画面用ViewModel（MVIパターン）
 * @property getAllDiariesUseCase 日記取得ユースケース
 */
class DiaryHomeViewModel(
    private val getAllDiariesUseCase: GetAllDiariesUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(DiaryHomeState())
    val state: StateFlow<DiaryHomeState> = _state

    init {
        // 初期化時に日記一覧をロード
        handleEvent(DiaryHomeEvent.Load)
    }

    /**
     * イベントを処理するメソッド
     * @param event DiaryHomeEvent イベント
     */
    fun handleEvent(event: DiaryHomeEvent) {
        when (event) {
            is DiaryHomeEvent.Load -> loadDiaries()
        }
    }

    /**
     * 日記一覧を取得し、今月分の全日付とマージして状態に反映
     */
    private fun loadDiaries() {
        _state.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            try {
                delay(3000)
                val diaries = getAllDiariesUseCase()
                val allDates = DateUtils.getDatesOfCurrentMonth()
                val diaryMap = diaries.associateBy { it.date }
                val fullGridList = allDates.map { date ->
                    diaryMap[date] ?: DiaryData(date = date, content = "", photoUrl = null)
                }
                _state.update { currentState ->
                    currentState.copy(
                        diaryDataList = fullGridList,
                        isLoading = false
                    )
                }
            }catch (e: Exception) {
                // エラーハンドリング（ログ出力など）
                _state.update { currentState ->
                    currentState.copy(isLoading = false)
                }
            }
        }
    }
} 