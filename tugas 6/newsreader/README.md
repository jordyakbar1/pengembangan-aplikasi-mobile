# Aplikasi News Reader

Aplikasi News Reader sederhana yang dibangun menggunakan Compose Multiplatform.

## Fitur
- Daftar artikel yang diambil dari API remote.
- Fitur *Pull-to-refresh* untuk memperbarui daftar berita.
- Tampilan detail untuk setiap artikel.
- Penanganan status *loading* dan *error*.

## API yang Digunakan
Aplikasi ini menggunakan **JSONPlaceholder API** untuk tujuan demonstrasi:
- **Base URL:** `https://jsonplaceholder.typicode.com`
- **Endpoint:** `/posts` (Dipetakan sebagai artikel berita)

## Screenshots

### 1. Status Berhasil (Daftar Artikel)
Layar utama menampilkan daftar artikel yang diambil dari API.

![Success State](screenshots/success_state.png)

### 2. Status Memuat (Loading)
Indikator progres melingkar ditampilkan saat data sedang diambil.

![Loading State](screenshots/loading_state.png)

### 3. Status Error
Jika data gagal dimuat (misalnya, tidak ada koneksi internet), pesan kesalahan dan tombol coba lagi akan ditampilkan.

![Error State](screenshots/error_state.png)

### 4. Pull-to-Refresh
Pengguna dapat menarik daftar ke bawah untuk memperbarui konten.

![Pull to Refresh](screenshots/refresh_state.png)

## Tech Stack
- **Compose Multiplatform:** UI framework.
- **Ktor:** Library jaringan (networking).
- **Kotlinx Serialization:** Parsing JSON.
- **Arsitektur MVVM:** Struktur kode yang bersih.
- **Coil:** Pemuatan gambar (siap untuk dikembangkan lebih lanjut).
