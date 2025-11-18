# FoodOrderApp (Aplikasi Pemesanan Makanan)

## Tentang Aplikasi

FoodOrderApp adalah aplikasi desktop berbasis Java yang dirancang untuk memudahkan pengguna dalam memesan makanan dan minuman secara interaktif melalui antarmuka grafis (GUI). Aplikasi ini dibangun dengan prinsip **Pemrograman Berorientasi Objek (OOP)** yang kuat, menerapkan konsep seperti **Unit Testing**, **Java Collection Framework**, **Generic Programming**, **Clean Code**, dan minimal **3 Design Pattern**. GUI merupakan komponen **wajib** dalam aplikasi ini, bukan tambahan opsional.

Dengan FoodOrderApp, pengguna dapat:

- Melihat daftar menu makanan & minuman
- Memilih dan menambahkan item ke keranjang
- Mengelola pesanan secara real-time
- Mendapatkan tampilan yang responsif dan profesional

## Fitur Utama

### Manajemen Menu

- Daftar makanan dan minuman dengan harga dan kategori
- Penambahan dan penghapusan item melalui antarmuka admin (opsional di pengembangan lanjut)

### Pemesanan Interaktif

- Tambah/kurangi jumlah item pesanan
- Hitung total harga otomatis
- Validasi input sebelum checkout

### Antarmuka Pengguna (GUI Wajib)

- Tampilan berbasis **JavaFX** modern dan responsif
- Navigasi antar layar (daftar menu → keranjang → konfirmasi)
- Umpan balik visual saat aksi dilakukan (misal: item ditambahkan ke keranjang)

### Arsitektur & Teknologi

- **Bahasa**: Java 17+
- **Framework GUI**: JavaFX
- **Build Tool**: Maven
- **Testing**: JUnit 5 (untuk logika non-GUI)
- **Prinsip OOP**:
  - Singleton (manajemen pesanan global)
  - Factory (pembuatan jenis menu)
  - Observer (notifikasi perubahan keranjang)
- **Struktur Proyek**:
  - `model/` → entitas domain (Menu, Order, dll)
  - `service/` → logika bisnis
  - `view/` → controller & tampilan GUI (JavaFX FXML + Controller)
  - `repository/` → penyimpanan data sementara (menggunakan `List`/`Map`)
  - `util/` → kelas generic & helpers

## Panduan Pengembangan

### Prasyarat

- **Java 17** atau lebih tinggi
- **Maven** (untuk manajemen dependensi dan build)
- **Git**
- IDE (disarankan: VS Code + Extension _Extension Pack for Java_ atau IntelliJ IDEA)

---

### 1. Clone Repository

```bash
git clone https://github.com/Cherry100106/TubesPBO-Pemesanan-Makanan.git
cd TubesPBO-Pemesanan-Makanan
```
