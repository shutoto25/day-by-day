package org.example.project.util

import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.plus
import kotlinx.datetime.minus

/**
 * 日付操作のためのユーティリティクラス
 */
object DateUtils {
    /**
     * 今日の日付を取得（YYYY-MM-DD形式）
     */
    fun getTodayDate(): String {
        val today = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        return "${today.year}-${today.monthNumber.toString().padStart(2, '0')}-${today.dayOfMonth.toString().padStart(2, '0')}"
    }

    fun createTiemestamp(): String {
        val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        return "${now.hour.toString().padStart(2, '0')}:${now.minute.toString().padStart(2, '0')}:${now.second.toString().padStart(2, '0')}.${(now.nanosecond / 1000000).toString().padStart(3, '0')}"
    }

    /**
     * 今月分の全日付（YYYY-MM-DD形式）リストを返す
     */
    fun getDatesOfCurrentMonth(): List<String> {
        val today = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
        val year = today.year
        val month = today.monthNumber
        val firstDay = LocalDate(year, month, 1)
        val lastDay = firstDay.plus(DatePeriod(months = 1)).minus(DatePeriod(days = 1))
        return (1..lastDay.dayOfMonth).map { day ->
            val dayStr = day.toString().padStart(2, '0')
            val monthStr = month.toString().padStart(2, '0')
            "$year-$monthStr-$dayStr"
        }
    }
}