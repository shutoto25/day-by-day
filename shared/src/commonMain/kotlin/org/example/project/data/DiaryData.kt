package org.example.project.data

import kotlinx.serialization.Serializable

/**
 * 日記データモデル
 * @property date 日付（YYYY-MM-DD形式）
 * @property content 日記本文
 * @property photoUrl 写真のURL（オプション）
 */
@Serializable
data class DiaryData(
    val date: String,
    val content: String,
    val photoUrl: String? = null
)