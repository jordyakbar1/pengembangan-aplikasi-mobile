# NoteApp - Aplikasi Pencatat Digital

Aplikasi Android yang memungkinkan pengguna untuk membuat, mengedit, menghapus, dan mencari catatan dengan antarmuka yang intuitif serta mendukung dark mode.


## 🗄️ Database Schema

### Tabel: `noteEntity`

Tabel ini menyimpan semua data catatan pengguna.

| Kolom | Tipe | Deskripsi |
|-------|------|-----------|
| `id` | INTEGER | Primary Key, auto-increment |
| `title` | TEXT | Judul catatan (wajib diisi) |
| `content` | TEXT | Isi catatan (wajib diisi) |
| `createdAt` | INTEGER | Timestamp pembuatan catatan (wajib diisi) |

### Entity Relationship Diagram

```
┌──────────────────────┐
│     noteEntity       │
├──────────────────────┤
│ id (PK)         [INT]│
│ title           [TXT]│
│ content         [TXT]│
│ createdAt       [INT]│
└──────────────────────┘
```

### Query Utama

- **selectAll**: Mengambil semua catatan diurutkan berdasarkan tanggal terbaru
- **insertNote**: Menambah atau memperbarui catatan
- **deleteNote**: Menghapus catatan berdasarkan ID
- **searchNotes**: Mencari catatan berdasarkan judul atau isi
- **getNoteById**: Mengambil catatan spesifik berdasarkan ID


## 📱 Screenshots

Berikut adalah tampilan aplikasi di berbagai screen:

### 1. **Note List Screen (Daftar Catatan)**

![alt text](Screens6-04-24458.png)

### 2. **Add Note Screen (Tambah Catatan)**

![alt text](Screensho26-04-24557.png)

### 3. **Note Detail Screen (Detail Catatan)**

![alt text](Screenshot2026-04-2414641.png)

### 4. **Edit Note Screen (Edit Catatan)**

![alt text](Screenshotuyt26-04-214745.png)

### 5. **Settings Screen (Pengaturan)**

![alt text](Screenshot87652026-044847.png)
