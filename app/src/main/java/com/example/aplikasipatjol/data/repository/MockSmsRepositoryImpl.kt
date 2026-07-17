package com.example.aplikasipatjol.data.repository

import com.example.aplikasipatjol.domain.model.MessageCategory
import com.example.aplikasipatjol.domain.model.SmsMessage
import com.example.aplikasipatjol.domain.repository.SmsRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class MockSmsRepositoryImpl : SmsRepository {

    private val allMessages = MutableStateFlow<List<SmsMessage>>(emptyList())
    private val spamMessages = MutableStateFlow<List<SmsMessage>>(emptyList())

    init {
        // Initialize with some mock data mimicking what was in the UI
        val initialData = listOf(
            SmsMessage(id = 1, sender = "Telkomsel", date = "02 Juni 2025", snippet = "PROMO!!, Kuota malam hari cocok buat nonton vtuber sampe ...", fullBody = "PROMO!!, Kuota tak terbatas malam hari cocok buat nonton vtuber sampe subuh. CUMA 5K untuk mendapatkan kuota tak terbatas.\n\nHubungi 911.", time = "10:00 PM", category = MessageCategory.SAFE),
            SmsMessage(id = 2, sender = "+62123456789", date = "02 Juni 2025", snippet = "Pilih CITI 5i- O75, Terpercaya dan g@j! p0k0k area ter b4ik...", fullBody = "Pilih CITI 5i- O75, Terpercaya dan g@j! p0k0k area ter b4ik...\n\nKlik link ini: bit.ly/spam", time = "11:00 AM", category = MessageCategory.SPAM),
            SmsMessage(id = 3, sender = "JasaPinjol", date = "02 Juni 2025", snippet = "100 Juta Rupiah Bisa Untuk Apa Saja! Cairkan Pinjamanmu...", fullBody = "100 Juta Rupiah Bisa Untuk Apa Saja! Cairkan Pinjamanmu sekarang juga tanpa syarat rumit.", time = "09:30 AM", category = MessageCategory.PINJOL),
            SmsMessage(id = 4, sender = "Telkomsel", date = "02 Juni 2025", snippet = "PROMO!!, Kuota pagi hari cocok buat dengerin channel rohani...", fullBody = "PROMO!!, Kuota pagi hari...", time = "08:15 AM", category = MessageCategory.SAFE),
            SmsMessage(id = 5, sender = "+62987654321", date = "02 Juni 2025", snippet = "Depo 10k jadikan 100M! Gacor parah slot terbaru...", fullBody = "Depo 10k jadikan 100M! Gacor parah slot terbaru...", time = "07:00 AM", category = MessageCategory.JUDOL)
        )
        allMessages.value = initialData
    }

    override fun getInboxMessages(): Flow<List<SmsMessage>> {
        return allMessages
    }

    override fun getSpamMessages(): Flow<List<SmsMessage>> {
        return spamMessages
    }

    override suspend fun scanMessages() {
        // Simulate network/AI processing delay
        delay(2000)
        
        val currentMessages = allMessages.value
        val safeMessages = mutableListOf<SmsMessage>()
        val detectedSpam = mutableListOf<SmsMessage>()

        currentMessages.forEach { msg ->
            if (msg.category == MessageCategory.SAFE) {
                safeMessages.add(msg.copy(isScanned = true))
            } else {
                detectedSpam.add(msg.copy(isScanned = true))
            }
        }

        allMessages.value = safeMessages
        spamMessages.value = spamMessages.value + detectedSpam
    }
}
