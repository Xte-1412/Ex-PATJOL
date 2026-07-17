# 🛡️ PATJOL (Pesan Aman Tanpa Judi Online)

**PATJOL** adalah aplikasi Android inovatif yang dirancang untuk memberikan perlindungan kepada pengguna dari pesan-pesan singkat (SMS) yang mengandung unsur Judi Online (Judol), Pinjaman Online Ilegal (Pinjol), serta konten *phishing* atau *malware*. Aplikasi ini mengintegrasikan teknologi *Machine Learning/AI* untuk mendeteksi, melabeli, dan menyaring pesan secara otomatis.

## 📱 Fitur Utama (UI/UX)
Saat ini, proyek berada pada tahap pengembangan antarmuka (UI/UX) menggunakan **Jetpack Compose**. Beberapa alur fitur yang telah dikembangkan antara lain:

*   **Onboarding & Splash Screen**: Pengenalan aplikasi dengan desain yang bersih dan responsif.
*   **Pemilihan Mode Sistem**:
    *   **Mode Trial**: Simulasi penggunaan aplikasi untuk menguji fitur deteksi tanpa harus mengubah pengaturan sistem.
    *   **Mode Set Default App**: Mode penuh di mana aplikasi bertindak sebagai aplikasi SMS utama (*Default SMS Handler*) untuk memfilter pesan secara otomatis.
*   **Pemilihan Mode Pengguna**: Memisahkan peran antara Pengirim Pesan dan Penerima Pesan (Fokus utama pada Penerima Pesan).
*   **Kotak Masuk (Inbox) SMS**: Antarmuka daftar pesan masuk yang modern dengan *Custom Bottom Navigation*.
*   **Simulasi Deteksi & Filter (Scanning Flow)**: Animasi dan alur kerja aplikasi saat melakukan pemindaian pesan (*scanning*), pelabelan, hingga menyembunyikan pesan spam dari kotak masuk utama.
*   **Kotak Penampungan Spam (Pesan Waspada/Judol)**: 
    *   Area khusus (karantina) untuk pesan yang terdeteksi sebagai Judol/Pinjol.
    *   Dilengkapi dengan sistem peringatan keamanan ganda (Sensor Link).
    *   Fitur edukasi interaktif (*Tooltips*) yang menjelaskan alasan pelabelan pesan.
    *   Fitur **Aju Banding**, memungkinkan pengguna melaporkan kesalahan deteksi oleh sistem.

## 🛠️ Tech Stack
*   **Bahasa Pemrograman**: Kotlin
*   **UI Toolkit**: Jetpack Compose
*   **Arsitektur**: MVVM / MVI (Tahap Perencanaan)
*   **Machine Learning/AI**: Integrasi deteksi teks dan klasifikasi pola spam (Akan Dikerjakan oleh Tim ML/Backend)
*   **Backend / API**: Pengiriman log dan sinkronisasi data *blacklist* (Akan Dikerjakan oleh Tim Backend)

## 🚀 Cara Menjalankan Proyek
1. *Clone* atau unduh *repository* ini.
2. Buka proyek menggunakan **Android Studio** (Disarankan versi terbaru yang mendukung Jetpack Compose).
3. Tunggu hingga proses *Gradle Sync* selesai.
4. Hubungkan perangkat Android (fisik) atau jalankan Emulator.
5. Tekan tombol **Run** (`Shift + F10`) untuk melihat dan menguji *flow* UI yang telah dibuat.

## 🤝 Kolaborasi Tim
Proyek ini dikembangkan secara kolaboratif:
*   **Frontend (Android/UI)**: Pengembangan alur aplikasi, navigasi, dan antarmuka interaktif menggunakan Jetpack Compose (Saat ini sedang berjalan).
*   **Backend**: Persiapan *server*, *database* terpusat, dan API.
*   **AI / Machine Learning**: Pengembangan algoritma deteksi teks (*Natural Language Processing*) untuk mengidentifikasi *keyword* dan *link* berbahaya.

---
*Dokumentasi ini akan terus diperbarui seiring dengan berjalannya proses pengembangan (Integrasi Backend & AI).*
