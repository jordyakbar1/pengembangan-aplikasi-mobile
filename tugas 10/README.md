# Notes App - DI & Testing Implementation

Proyek ini mendemonstrasikan implementasi Dependency Injection (DI) menggunakan Koin dan pengujian komprehensif (Unit, Flow, dan UI Testing) pada aplikasi pencatat berbasis Android.

## 🚀 Fitur & Teknologi
- **Dependency Injection**: Koin (Modules: `data`, `viewModel`)
- **Database**: SQLDelight (dengan SQLite Driver untuk testing)
- **State Management**: Kotlin Flow & StateFlow
- **AI Integration**: Gemini AI untuk ringkasan catatan
- **Testing Stack**: MockK, Turbine, JUnit4, Compose Test Rule

---

## 📊 Test Coverage Report
Berikut adalah ringkasan cakupan pengujian untuk logika bisnis (Repository & ViewModel).

![Test Coverage Report](path/to/your/screenshot.png)
> *Catatan: Pastikan untuk menjalankan "Run Tests with Coverage" di Android Studio dan mengganti path gambar di atas.*

**Target Minimal: 60% Business Logic Coverage**

---

## 🧪 Daftar Test Cases

### 1. Dependency Injection (Koin)
- [x] **Data Module**: Injeksi `NoteDatabase`, `SettingsManager`, `NetworkMonitor`, dan `NoteRepository`.
- [x] **ViewModel Module**: Injeksi `NoteViewModel` dengan semua dependensi yang diperlukan.

### 2. Unit Test: NoteRepository (5 Cases)
Lokasi: `app/src/test/java/.../data/NoteRepositoryTest.kt`
- `insert and get all notes`: Memastikan catatan yang disimpan dapat diambil kembali.
- `search notes by title`: Memastikan fungsi pencarian memfilter data dengan benar.
- `get note by id`: Memastikan pengambilan data spesifik berdasarkan ID berhasil.
- `delete note`: Memastikan penghapusan catatan sinkron dengan database.
- `upsert updates existing note`: Memastikan fungsi `insert` menangani update jika ID sudah ada.

### 3. Unit Test: NotesViewModel with MockK (4 Cases)
Lokasi: `app/src/test/java/.../NoteViewModelTest.kt`
- `initial state is correct`: Memastikan state awal (loading, network) sesuai.
- `search query change updates uiState`: Memastikan input pencarian memperbarui state UI.
- `notes are loaded into state`: Memastikan data dari repository masuk ke state UI.
- `ai error updates state`: Memastikan kegagalan servis AI tertangkap di state error.

### 4. Flow Test with Turbine (2 Cases)
Terintegrasi dalam `NoteViewModelTest.kt`
- `uiState.test { ... }`: Mengetes emisi StateFlow saat inisialisasi.
- `uiState.test { ... }`: Mengetes emisi StateFlow saat terjadi perubahan query pencarian.

### 5. UI Test: NotesScreen (3 Cases)
Lokasi: `app/src/androidTest/java/.../NoteListScreenTest.kt`
- `testEmptyState`: Memastikan pesan "No notes yet" muncul saat data kosong.
- `testNotesList`: Memastikan item catatan ditampilkan di layar saat data ada.
- `testLoadingState`: Memastikan indikator loading (CircularProgressIndicator) muncul saat state loading aktif.

---


