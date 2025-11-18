# FoodOrderApp (Aplikasi Pemesanan Makanan)

## Tentang Aplikasi

FoodOrderApp adalah aplikasi desktop berbasis Java yang dirancang untuk memudahkan pengguna dalam memesan makanan dan minuman. Aplikasi ini dibangun dengan prinsip **Pemrograman Berorientasi Objek (OOP)** menggunakan **Maven** dan database **PostgreSQL**.

## Fitur Utama Saat Ini

- **Koneksi Database**: Terintegrasi dengan PostgreSQL.
- **Auto-Seeding**: Database otomatis terisi data awal (tabel & data dummy) saat aplikasi dijalankan.
- **Struktur Proyek**: Menggunakan Maven Standard Directory Layout.

## Prasyarat (Wajib Install)

1.  **Java 17** (atau lebih tinggi)
2.  **Maven** (Untuk build project)
3.  **PostgreSQL** (Database Server)
4.  **VS Code** (dengan Extension Pack for Java) atau **IntelliJ IDEA**.

---

## Panduan Instalasi & Setup (Langkah demi Langkah)

### 1. Clone Repository

```bash
git clone [https://github.com/Cherry100106/TubesPBO-Pemesanan-Makanan.git](https://github.com/Cherry100106/TubesPBO-Pemesanan-Makanan.git)
cd TubesPBO-Pemesanan-Makanan
```

### 2. Setup Database (PostgreSQL)

1. Buka aplikasi database manager (pgAdmin / DBeaver).
2. Buat database baru biarkan kosong. Jangan buat tabel apapun manual.

### 3. Atur Password Database

1. Buka File: src/main/java/com/tubes/db/Koneksi.java
2. Cari dan ubah bagian ini:

```bash
private static final String PASS = "password_laptop_kalian";
```

### 4. Build Project

Agar file SQL terbaca oleh sistem, jalankan perintah ini di terminal VS Code sebelum me-run aplikasi:

```bash
mvn clean compile
```

### 5. Jalankan Aplikasi

1. Buka file src/main/java/com/tubes/App.java, lalu klik tombol Run.
2. Jika berhasil, output terminal akan muncul:

```bash
Memulai Aplikasi...
Koneksi ke PostgreSQL Berhasil!
Database berhasil di-seed (Reset & Isi Data)!
```

## Struktur Folder Proyek

Berikut adalah struktur folder saat ini agar tidak bingung menaruh file:

```bash
project-pemesanan-makanan/
├── pom.xml                      <-- File konfigurasi Maven (Library)
└── src/
    └── main/
        ├── java/com/tubes/
        │   ├── db/
        │   │   ├── Koneksi.java         # Settingan Database
        │   │   └── DatabaseSeeder.java  # Script otomatis pengisi data
        │   └── App.java                 # File Utama (Main Class)
        └── resources/
            └── db_init.sql              # Script SQL (Jangan diubah namanya)
```

## Troubleshooting

Q: Muncul Error "File SQL tidak ditemukan!"

Solusi: Kamu lupa melakukan build. Stop aplikasi, ketik mvn clean compile di terminal, lalu coba Run lagi.

Q: Error "Connection Refused"

Solusi: Pastikan aplikasi PostgreSQL sudah nyala (Start Service) dan password di Koneksi.java sudah benar.
