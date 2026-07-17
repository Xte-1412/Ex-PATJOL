package com.example.aplikasipatjol.domain.model

enum class MessageCategory {
    SAFE,
    SPAM,
    JUDOL,
    PINJOL
}

data class SmsMessage(
    val id: Int,
    val sender: String,
    val date: String,
    val snippet: String,
    val fullBody: String,
    val time: String,
    val category: MessageCategory = MessageCategory.SAFE,
    val isScanned: Boolean = false
)
