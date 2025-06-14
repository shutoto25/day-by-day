package org.example.project.presentation

import androidx.lifecycle.ViewModel
import org.example.project.diary.data.DiaryData
import org.example.project.diary.usecase.GetAllDiariesUseCase
import org.example.project.util.DateUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * ホーム画面の状態
 */
data class DiaryHomeState(
    val fullGridList: List<DiaryData> = emptyList()
)

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
        loadDiaries()
    }

    /**
     * 日記一覧を取得し、今月分の全日付とマージして状態に反映
     */
    fun loadDiaries() {
        CoroutineScope(Dispatchers.Default).launch {
            val diaries = getAllDiariesUseCase.execute()
            val allDates = DateUtils.getDatesOfCurrentMonth()
            val diaryMap = diaries.associateBy { it.date }
            val fullGridList = allDates.map { date ->
                diaryMap[date] ?: DiaryData(date = date, content = "", photoUrl = null)
            }
            _state.value = DiaryHomeState(fullGridList = fullGridList)
        }
    }
} 