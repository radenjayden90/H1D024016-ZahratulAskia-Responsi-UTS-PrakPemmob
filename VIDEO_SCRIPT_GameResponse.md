# Naskah Penjelasan Kode: GameResponse.kt

- **File yang Dibahas**: `app/src/main/java/com/example/responsizahratul/data/model/GameResponse.kt`
- **Target Durasi**: 60 – 90 detik (sekitar 180 – 210 kata)
- **Gaya Penyampaian**: Sopan, akademik, santai, dan jelas untuk didengar dosen penguji.

---

## Tabel Runtutan Naskah & Tampilan Layar

| No | Yang Ditunjukkan di Layar | Yang Diucapkan |
| :---: | :--- | :--- |
| **1** | **Buka file `GameResponse.kt`**, sorot baris 1 sampai 7 (package, import, dan deklarasi `GameResponse`). | "Sekarang saya membuka file `GameResponse.kt` yang berada di dalam package `data.model`. File ini berfungsi sebagai model data untuk menampung respon JSON dari RAWG API agar datanya dapat diolah di dalam bahasa Kotlin." |
| **2** | **Arahkan kursor ke baris 5**, sorot kata kunci `data class`. | "Di sini saya menggunakan `data class`. Sederhananya, data class ini bekerja seperti wadah atau formulir kosong. Ketika server mengirimkan data berupa teks JSON, pustaka Retrofit akan membaca teks tersebut, lalu memindahkannya ke dalam formulir ini melalui proses yang disebut parsing." |
| **3** | **Sorot baris 16** (`results: List<GameItem>?`), lalu **baris 19** (`data class GameItem`), dan **baris 48–51** (`genres` dan `data class Genre`). | "Di file ini terdapat tiga class yang saling terhubung. Pertama, `GameResponse` sebagai pembungkus utama yang menyimpan daftar game di properti `results`. Kedua, `GameItem` yang memuat data rinci tiap game. Dan ketiga, `Genre` untuk kategori game yang berada di dalam `GameItem`." |
| **4** | **Sorot baris 29–30** (`@SerializedName("background_image") val backgroundImage: String? = null`). | "Setiap field dilengkapi anotasi `@SerializedName`. Anotasi ini berfungsi sebagai penerjemah nama. Contohnya pada baris 29, key JSON dari server bernama `background_image` yang memakai garis bawah, dipetakan ke variabel Kotlin bernama `backgroundImage` dengan gaya camelCase agar sesuai standar penulisan Kotlin." |
| **5** | **Sorot tanda tanya `?` dan `= null`** pada baris 20–33 di dalam `GameItem`. | "Semua tipe data sengaja saya buat nullable dengan tanda tanya dan diberi nilai bawaan `null`. Alasannya, data dari internet tidak selalu lengkap, misalnya ada game yang belum memiliki tanggal rilis atau gambar cover. Jika tidak dibuat nullable, aplikasi bisa mengalami crash saat membaca data yang kosong. Ini menerapkan prinsip null safety sesuai ketentuan tugas." |
| **6** | **Tampilkan kembali keseluruhan file** dari atas ke bawah secara ringkas. | "Model data di file ini nantinya digunakan langsung oleh `ApiService` dan `GameRepository`, yang akan saya jelaskan pada file berikutnya." |

---

## Kemungkinan Pertanyaan Dosen & Rekomendasi Jawaban

### Pertanyaan 1: Mengapa dibuat tiga data class terpisah dalam file ini, bukan disatukan menjadi satu class saja?
**Jawaban yang Diucapkan**:
> "Karena struktur data JSON dari server RAWG berbentuk hierarkis atau bersarang. Respon utama membungkus kumpulan data game, dan di dalam setiap item game terdapat daftar objek genre tersendiri. Membaginya menjadi `GameResponse`, `GameItem`, dan `Genre` mencerminkan struktur asli data tersebut sehingga proses pemetaan berjalan tepat dan mudah dibaca."

### Pertanyaan 2: Apa fungsi tanda `= null` jika tipe datanya sudah diberi tanda tanya nullable (`?`)?
**Jawaban yang Diucapkan**:
> "Tanda `= null` memberikan nilai bawaan atau default. Jika server RAWG mengirimkan respons yang sama sekali tidak memuat field tertentu, variabel tersebut otomatis bernilai `null` tanpa memicu error saat objek data class dibuat."
