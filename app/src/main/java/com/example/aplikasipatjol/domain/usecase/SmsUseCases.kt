package com.example.aplikasipatjol.domain.usecase

import com.example.aplikasipatjol.domain.model.SmsMessage
import com.example.aplikasipatjol.domain.repository.SmsRepository
import kotlinx.coroutines.flow.Flow

class GetInboxMessagesUseCase(private val repository: SmsRepository) {
    operator fun invoke(): Flow<List<SmsMessage>> {
        return repository.getInboxMessages()
    }
}

class GetSpamMessagesUseCase(private val repository: SmsRepository) {
    operator fun invoke(): Flow<List<SmsMessage>> {
        return repository.getSpamMessages()
    }
}

class ScanMessagesUseCase(private val repository: SmsRepository) {
    suspend operator fun invoke() {
        repository.scanMessages()
    }
}
