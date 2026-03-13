# Profile App - Compose Multiplatform

## Deskripsi

Aplikasi sederhana yang dibuat menggunakan **Jetpack Compose Multiplatform** untuk menampilkan **profil pengguna** dalam bentuk kartu (Profile Card).
Aplikasi ini dibuat sebagai **Tugas Praktikum Minggu 3 – Compose Multiplatform Basics**.

Aplikasi menampilkan informasi profil seperti:

* Foto profil
* Nama
* Bio
* Email
* Nomor telepon
* Lokasi

UI dibangun menggunakan **Composable Components** agar kode lebih modular dan mudah digunakan kembali.

---

## Fitur

* Menampilkan **foto profil berbentuk lingkaran**
* Informasi profil ditampilkan dalam **Card**
* Layout menggunakan **Column dan Row**
* Komponen UI dipisahkan menjadi beberapa file agar lebih terstruktur

---

## Struktur Project

```
composeApp
│
├── model
│   └── Profile.kt
│
├── ui
│   └── components
│       ├── ProfileCard.kt
│       ├── ProfileHeader.kt
│       └── InfoItem.kt
│
├── App.kt
│
└── composeResources
    └── drawable
        └── profile.jpeg
```

Penjelasan:

| File                 | Fungsi                                                            |
| -------------------- | ----------------------------------------------------------------- |
| **Profile.kt**       | Model data untuk menyimpan informasi profil                       |
| **ProfileCard.kt**   | Komponen kartu utama yang menampilkan data profil                 |
| **ProfileHeader.kt** | Menampilkan foto profil, nama, dan bio                            |
| **InfoItem.kt**      | Menampilkan informasi tambahan seperti email, phone, dan location |
| **App.kt**           | Entry point UI aplikasi                                           |
| **profile.jpeg**     | Gambar foto profil                                                |

---

## Teknologi yang Digunakan

* Kotlin
* Jetpack Compose
* Compose Multiplatform
* Material 3

---

## Tampilan Aplikasi

Aplikasi menampilkan kartu profil dengan layout seperti berikut:

* Foto profil berbentuk **circular**
* Nama dan bio di samping foto
* Informasi tambahan ditampilkan di bawah

![Screenshot Aplikasi](Screenshot%202026-03-13%20155808.png)

---

## Cara Menjalankan Project

1. Buka project di **Android Studio**
2. Tunggu hingga **Gradle Sync selesai**
3. Jalankan aplikasi menggunakan emulator atau perangkat Android
4. Aplikasi akan menampilkan halaman profil

---


## Author

Nama: jordy anugrah akbar
Mata Kuliah: Pengembangan aplikasi Mobile
Praktikum: Minggu 3 – Compose Multiplatform Basics
