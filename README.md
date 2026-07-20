# 🛡️ PATJOL (Pesan Aman Tanpa Judi Online)

**PATJOL** adalah aplikasi Android inovatif yang dirancang untuk memberikan perlindungan kepada pengguna dari pesan-pesan singkat (SMS) yang mengandung unsur Judi Online (Judol), Pinjaman Online Ilegal (Pinjol), serta konten *phishing* atau *malware*. Aplikasi ini mengintegrasikan teknologi *Machine Learning/AI* untuk mendeteksi, melabeli, dan menyaring pesan secara otomatis.

Repositori ini secara khusus disiapkan dan dikelola untuk keperluan **Software Development Competition**.

## 📖 Deskripsi Aplikasi
Aplikasi PATJOL bertujuan untuk memfilter kotak masuk SMS pengguna dari pesan-pesan berbahaya. Dengan bertindak sebagai aplikasi SMS bawaan (*Default SMS Handler*), PATJOL dapat mencegat SMS yang masuk, memproses teksnya menggunakan kecerdasan buatan secara lokal di perangkat, dan mengkarantina pesan yang terdeteksi sebagai ancaman ke dalam kotak "Pesan Waspada". Hal ini memastikan kotak masuk utama pengguna tetap bersih, aman, dan meminimalisir risiko penipuan.

## 🛠️ Teknologi yang Digunakan
Proyek ini mengadopsi standar pengembangan Android modern (*best practices*):
*   **Bahasa Pemrograman**: Kotlin
*   **UI Toolkit**: Jetpack Compose (Material Design 3)
*   **Arsitektur Kode**: *Clean Architecture* (pemisahan *Domain, Data,* dan *Presentation layer*) dipadukan dengan pola **MVVM** (*Model-View-ViewModel*)
*   **Asynchronous Programming**: Kotlin Coroutines & StateFlow
*   **Machine Learning**: TensorFlow Lite (TFLite) untuk pemrosesan teks lokal (*On-Device ML*)
*   **Version Control**: Git & GitHub (dengan riwayat *commit* bertahap)

## 🚀 Cara Instalasi
1. Pastikan komputer Anda telah terinstal **Android Studio** versi terbaru (minimal versi yang mendukung lingkungan *Jetpack Compose* modern).
2. Lakukan *clone* repositori ini ke dalam direktori lokal Anda:
   ```bash
   git clone https://github.com/muhammadariefand/aplikasi-patjol.git
   ```
3. Buka proyek melalui Android Studio (`File -> Open` dan pilih folder proyek hasil *clone*).
4. Tunggu beberapa saat hingga proses Sinkronisasi Gradle (*Gradle Sync*) selesai secara otomatis.
5. Siapkan perangkat Android fisik (aktifkan *USB Debugging*) atau jalankan emulator (*Android Virtual Device*).
6. Tekan tombol **Run** (ikon segitiga hijau) atau `Shift + F10` untuk melakukan *build* dan menjalankan aplikasi.

## 📱 Cara Penggunaan
1. **Layar Onboarding**: Saat aplikasi dibuka, ikuti panduan layar pengenalan.
2. **Pemilihan Mode (Uji Coba vs Default)**:
   - Pilih **Mode Uji Coba (Trial)** untuk menyimulasikan fitur antarmuka tanpa perlu mengubah pengaturan sistem operasi perangkat.
   - Pilih **Jadikan Aplikasi Utama** (saat nanti terintegrasi penuh) agar sistem dapat melakukan intersepsi SMS secara nyata.
3. **Navigasi Utama (Bottom Nav)**:
   - **Pesan Utama**: Melihat daftar pesan masuk yang dikategorikan aman.
   - **Pesan Waspada**: Area karantina pesan terindikasi spam/judol. Di sini Anda bisa melihat alasan pelabelan spam dan peringatan tautan berbahaya.
   - **Statistik**: Memantau grafik visual perbandingan jenis pesan dan informasi "Waktu Rawan" masuknya pesan spam.
   - **Pengaturan**: Untuk melakukan penyesuaian profil, mengisi biodata akun, dan memberikan laporan (*feedback*).

## 📂 Struktur Folder
Proyek ini dikelola dengan sangat rapi menggunakan pedoman **Clean Architecture** demi menjaga skalabilitas kode:
```text
app/src/main/java/com/example/aplikasipatjol/
├── data/           # Layer Data: Implementasi Repository, DataSource, dan modul SmsReceiver
├── domain/         # Layer Domain: Logika bisnis inti, UseCases, dan Data Models
└── presentation/   # Layer Presentasi: 
    ├── ui/         # Komponen Antarmuka Jetpack Compose (screens, components)
    └── viewmodel/  # Manajemen State & UI Logic (SmsViewModel)
```
*Catatan Tambahan: Terdapat folder `assets/` di direktori `src/main/` yang dialokasikan sebagai tempat penyimpanan model klasifikasi TFLite.*

## 📜 Pernyataan Kepemilikan & Lisensi
Repository ini dirancang, ditulis, dan dibangun secara orisinal (bukan hasil *fork* pihak ketiga) oleh tim pengembang aplikasi **PATJOL** secara spesifik untuk dikompetisikan pada ajang lomba. Segala hak cipta terkait baris kode (*source code*), rancangan arsitektur, dan aset UI yang ada di dalam repositori ini merupakan hak milik intelektual tim (All Rights Reserved). Pihak panitia memiliki hak akses penuh untuk melakukan peninjauan dan penilaian komprehensif. Dilarang keras melakukan plagiarisme terhadap karya ini.
