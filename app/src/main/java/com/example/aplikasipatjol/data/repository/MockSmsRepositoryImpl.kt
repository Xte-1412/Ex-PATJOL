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
            SmsMessage(id = 1, sender = "Telkomsel", date = "07/07/2026", snippet = "PROMO!!, Kuota malam hari cocok buat nonton vtuber sampe ...", fullBody = "PROMO!!, Kuota tak terbatas malam hari cocok buat nonton vtuber sampe subuh. CUMA 5K untuk mendapatkan kuota tak terbatas.\n\nHubungi 911.", time = "10:00 PM", category = MessageCategory.SAFE),
            
            // Pesan Mencurigakan (PINJOL/SPAM)
            SmsMessage(id = 2, sender = "JasaPinjol", date = "07/07/2026", snippet = "Cari Uang Tambahan?! Cek Jumlah Limit Pinjamanmu. Daftar RupiahCepat disini...", fullBody = "Cari Uang Tambahan?! Cek Jumlah Limit Pinjamanmu. Daftar RupiahCepat disini...", time = "09:30 AM", category = MessageCategory.PINJOL),
            SmsMessage(id = 3, sender = "+628******2", date = "07/07/2026", snippet = "SELAMAT! 100 Juta langsung cair! RupiahCepat Limit Besar Untuk bisnis...", fullBody = "SELAMAT! 100 Juta langsung cair! RupiahCepat Limit Besar Untuk bisnis...", time = "10:10 PM", category = MessageCategory.SPAM),
            
            // Pesan Judi Online (JUDOL)
            SmsMessage(id = 4, sender = "+628******1", date = "07/07/2026", snippet = "(Isi pesan tidak ditampilkan oleh sistem. Klik Pesan untuk selengkapnya)", fullBody = "AKU KSH KM SA_LDO 100K UTK MA_IN ID BA'RU 100% P'AS'TI DIKASIH MENANG SISTEM PG'SO_FT BAGI2 MA,XWI'N MA_IN MAHJ_ONG 5 MENIT WD 2 JT DP&KL,AIM SKRG: ****************", time = "10:10 PM", category = MessageCategory.JUDOL)
        )
        
        val safeMessages = initialData.filter { it.category == MessageCategory.SAFE }
        val detectedSpam = initialData.filter { it.category != MessageCategory.SAFE }
        
        allMessages.value = safeMessages
        spamMessages.value = detectedSpam
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
