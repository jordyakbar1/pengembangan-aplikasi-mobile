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

![Success State](Screensho090033.png)

### 2. Status Memuat (Loading)
Indikator progres melingkar ditampilkan saat data sedang diambil.

![Loading State](c:\Users\USER\Pictures\Screenshots\Screenshotrwefrefrere.png)

### 3. Pull-to-Refresh
Pengguna dapat menarik daftar ke bawah untuk memperbarui konten.

![Pull to Refresh](c:\Users\USER\Pictures\Screenshots\Screensho6-04-19190446.png)

### 4. Detail saat di klik

![Klik for detail](c:\Users\USER\Pictures\Screenshots\Screensho26-04-0653.png)
## Tech Stack
- **Compose Multiplatform:**
- **Ktor:** Library jaringan
- **Kotlinx Serialization:**
- **Arsitektur MVVM:** 
- **Coil:** Pemuatan gambar 
