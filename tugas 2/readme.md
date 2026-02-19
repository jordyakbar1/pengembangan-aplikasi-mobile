# News Feed Simulator 📰

Aplikasi **News Feed Simulator** adalah aplikasi berbasis **Kotlin Multiplatform** yang mensimulasikan aliran berita secara realtime menggunakan **Kotlin Coroutines**, **Flow**, dan **StateFlow**.

Aplikasi ini dibuat sebagai bagian dari **Tugas Praktikum Advanced Kotlin, Coroutines, dan Flow** pada mata kuliah **Pengembangan Aplikasi Mobile** Institut Teknologi Sumatera.

Nama: JORDY ANUGRAH AKBAR

NIM: (123140141)
---

# 📱 Screenshot Aplikasi

## Tampilan Utama

![Screenshot 1](Screenshot%202026-02-19%20200337.png)







# 🎯 Tujuan Aplikasi

Aplikasi ini bertujuan untuk mengimplementasikan konsep:

* Kotlin Flow
* Flow Operators (filter, map)
* StateFlow
* Kotlin Coroutines
* Asynchronous Programming
* Kotlin Multiplatform Compose UI

---

# ⚙️ Fitur Utama

## 1. Simulasi Berita Realtime (Flow)

Aplikasi menggunakan **Kotlin Flow** untuk menghasilkan berita baru setiap **2 detik**.

```kotlin
fun newsFlow(): Flow<News>
```

---

## 2. Filter Berita Berdasarkan Kategori

Aplikasi hanya menampilkan berita dengan kategori:

```
Technology
```

Menggunakan operator:

```kotlin
filter { }
```

---

## 3. Transformasi Data

Data berita ditampilkan dalam format:

```
📰 Breaking News 1 (Technology)
```

---

## 4. State Management dengan StateFlow

StateFlow digunakan untuk menyimpan jumlah berita yang telah dibaca:

```kotlin
private val _readCount = MutableStateFlow(0)
```

Ditampilkan pada UI:

```
Jumlah Dibaca: 3
```

---

## 5. Pengambilan Detail Berita secara Async (Coroutines)

Saat berita diklik, aplikasi mengambil detail berita menggunakan coroutine:

```kotlin
suspend fun getDetail(news: News)
```

Dengan delay simulasi:

```
1 detik
```

---

# 🧠 Konsep yang Digunakan

| Konsep          | Implementasi             |
| --------------- | ------------------------ |
| Flow            | Simulasi berita realtime |
| filter          | Filter kategori          |
| map             | Transform data           |
| StateFlow       | State management         |
| Coroutine       | Async detail             |
| Jetpack Compose | UI                       |

---



# 📊 Cara Kerja Aplikasi

1. Flow menghasilkan berita tiap 2 detik
2. Berita difilter kategori Technology
3. Berita ditampilkan di UI
4. User klik berita
5. Coroutine mengambil detail
6. StateFlow update jumlah dibaca

---

# 🧪 Contoh Output

```
NEWS FEED SIMULATOR

Jumlah Dibaca: 0

Breaking News 1 (Technology)

DETAIL BERITA:
Klik berita untuk lihat detail
```

---

# 🛠️ Teknologi yang Digunakan

* Kotlin
* Kotlin Multiplatform
* Kotlin Coroutines
* Kotlin Flow
* StateFlow
* Compose Multiplatform

