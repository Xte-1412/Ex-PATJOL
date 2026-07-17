package com.example.aplikasipatjol.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.aplikasipatjol.domain.model.SmsMessage
import com.example.aplikasipatjol.domain.usecase.GetInboxMessagesUseCase
import com.example.aplikasipatjol.domain.usecase.GetSpamMessagesUseCase
import com.example.aplikasipatjol.domain.usecase.ScanMessagesUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SmsViewModel(
    getInboxMessagesUseCase: GetInboxMessagesUseCase,
    getSpamMessagesUseCase: GetSpamMessagesUseCase,
    private val scanMessagesUseCase: ScanMessagesUseCase
) : ViewModel() {

    val inboxMessages: StateFlow<List<SmsMessage>> = getInboxMessagesUseCase()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    val spamMessages: StateFlow<List<SmsMessage>> = getSpamMessagesUseCase()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun scanMessages() {
        viewModelScope.launch {
            scanMessagesUseCase()
        }
    }
}

class SmsViewModelFactory(
    private val getInboxMessagesUseCase: GetInboxMessagesUseCase,
    private val getSpamMessagesUseCase: GetSpamMessagesUseCase,
    private val scanMessagesUseCase: ScanMessagesUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SmsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SmsViewModel(getInboxMessagesUseCase, getSpamMessagesUseCase, scanMessagesUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
