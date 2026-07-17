package com.example.aplikasipatjol.domain.util

object Preprocessing {
    
    /**
     * Membersihkan teks mentah dari simbol, huruf ganda, dll,
     * sebelum diumpankan ke model AI (TFLite).
     */
    fun cleanText(rawText: String): String {
        // TODO: Implementasi logika pembersihan teks dari tim AI
        return rawText.lowercase().replace(Regex("[^a-z0-9 ]"), "")
    }
}
