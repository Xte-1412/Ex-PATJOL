package com.example.aplikasipatjol.domain.repository

import com.example.aplikasipatjol.domain.model.SmsMessage
import kotlinx.coroutines.flow.Flow

interface SmsRepository {
    fun getInboxMessages(): Flow<List<SmsMessage>>
    fun getSpamMessages(): Flow<List<SmsMessage>>
    suspend fun scanMessages()
}
