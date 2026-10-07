# Naskah Video Penjelasan Kode Aplikasi GameDex

- **Nama Mahasiswa** : Zahratul Askia
- **NIM** : H1D024016
- **Mata Kuliah** : Praktikum Pemrograman Mobile
- **Target Durasi** : 5 – 8 Menit
- **Fokus Video** : **Penjelasan Arsitektur & Logika Kode** (Bukan demo jalannya aplikasi)

---

## Panduan Perekaman Video
1. Buka project **Android Studio** dengan struktur project terlihat jelas di panel kiri (*Project Tool Window*).
2. Tampilkan wajah melalui webcam di sudut layar (jika diwajibkan oleh dosen pengampu).
3. Buka file sesuai urutan di bawah ini untuk menunjukkan pemahaman alur data dari bawah (*Data Layer*) hingga ke atas (*UI Layer*).

---

## Runtutan Alur Penjelasan Kode

### Bagian 1: Pembukaan & Gambaran Umum Arsitektur (Durasi: ~45 Detik)
- **Tindakan**: Buka tampilan editor project, perlihatkan struktur package.
- **Naskah / Poin Bicara**:
  > *"Halo, perkenalkan nama saya Zahratul Askia dengan NIM H1D024016. Pada video ini, saya akan menjelaskan arsitektur dan alur kode sumber aplikasi Android 'GameDex' yang saya bangun untuk tugas Responsi Praktikum Pemrograman Mobile.*  
  > *Aplikasi ini menerapkan arsitektur MVVM (Model-View-ViewModel) yang memisahkan tanggung jawab kode menjadi lapisan Data (Model, Retrofit, Repository), ViewModel (pengelola status dan logika bisnis dengan StateFlow), dan View (antarmuka deklaratif dengan Jetpack Compose). Mari kita telusuri alur kodenya mulai dari lapisan data."*

---

### Bagian 2: Data Layer - Model Data & Null Safety (Durasi: ~1 Menit)
- **File yang Dibuka**: 
  1. `data/model/GameResponse.kt`
  2. `data/model/GameDetailResponse.kt`
- **Poin Bicara**:
  > *"Pertama, di dalam package `data.model`, terdapat data class yang memetakan respons JSON dari RAWG API.*  
  > *Di `GameResponse.kt`, terdapat `GameItem` yang mewakili item di daftar katalog. Setiap atribut menggunakan anotasi `@SerializedName` dari pustaka Gson agar nama variabel di Kotlin tetap rapi (*camelCase*) meski di JSON menggunakan format *snake_case*.*  
  > *Poin penting di sini adalah penerapan **Null Safety**: semua tipe data sengaja saya buat nullable (`?`) dengan nilai default `null` (misalnya `id`, `name`, `rating`, `released`, dan `backgroundImage`). Tujuannya adalah untuk menjamin aplikasi tidak mengalami crash akibat `NullPointerException` jika sewaktu-waktu server mengembalikan nilai kosong atau field yang hilang."*

---

### Bagian 3: Network Layer - Retrofit & ApiClient (Durasi: ~1 Menit)
- **File yang Dibuka**: 
  1. `data/network/ApiService.kt`
  2. `data/network/ApiClient.kt`
- **Poin Bicara**:
  > *"Kedua, kita masuk ke package `data.network`.*  
  > *Di file `ApiService.kt`, saya mendefinisikan kontrak interface Retrofit dengan kata kunci `suspend` pada fungsi `getGames()` dan `getGameDetail()`. Kata kunci `suspend` ini menandakan bahwa pemanggilan jaringan akan berjalan di dalam Kotlin Coroutines secara asinkron di background thread sehingga tidak membekukan UI.*  
  > *Selanjutnya di `ApiClient.kt`, instance Retrofit dibuat sebagai singleton object dengan inisialisasi malas `by lazy`. Keunggulan implementasi di sini adalah penggunaan `OkHttpClient Interceptor`: interceptor ini secara otomatis menyisipkan parameter `key` dari `BuildConfig.RAWG_API_KEY` ke setiap URL request. Dengan demikian, API Key tersimpan aman di `local.properties` dan kita tidak perlu menuliskannya secara manual dan berulang di interface ApiService."*

---

### Bagian 4: Repository Pattern (Durasi: ~45 Detik)
- **File yang Dibuka**: `data/repository/GameRepository.kt`
- **Poin Bicara**:
  > *"Ketiga, pada `GameRepository.kt`, lapisan ini berperan sebagai Single Source of Truth.*  
  > *Repository membungkus pemanggilan dari ApiService. Di fungsi `getGames()`, terdapat logika pengecekan: jika query pencarian bernilai kosong atau spasi (`isNullOrBlank()`), maka parameter dikirim sebagai `null` agar Retrofit tidak menyertakan parameter search di URL.*  
  > *Selain itu, fungsi mengembalikan `response.results ?: emptyList()` agar list yang diterima ViewModel selalu aman dan non-null. Repository sengaja tidak menangkap exception dengan try-catch agar error jaringan diteruskan secara utuh ke ViewModel untuk diproses statusnya."*

---

### Bagian 5: UI State & ViewModel (Durasi: ~1.5 Menit)
- **File yang Dibuka**: 
  1. `ui/viewmodel/GameUiState.kt`
  2. `ui/viewmodel/GameViewModel.kt`
- **Poin Bicara**:
  > *"Keempat, kita masuk ke lapisan logika di `ui.viewmodel`.*  
  > *Di file `GameUiState.kt`, saya mendefinisikan Sealed Interface untuk memodelkan 3 kondisi layar secara jelas: `Loading`, `Success`, dan `Error(message)`.*  
  > *Di `GameViewModel.kt`, kita menerapkan prinsip **State Encapsulation**: variabel internal menggunakan `MutableStateFlow` privat (`_uiState` dan `_searchQuery`), namun diekspos keluar sebagai `StateFlow` publik bertipe read-only.*  
  > *Saat ViewModel dibuat, blok `init` langsung memicu `fetchGames()` untuk menampilkan daftar awal.*  
  > *Untuk fungsi pencarian `onQueryChange()`, saya menyematkan teknik **Debounce 500 ms**: setiap kali pengguna mengetik karakter baru, `searchJob?.cancel()` membatalkan coroutine pencarian sebelumnya, lalu memberi jeda `delay(500L)` sebelum memanggil API. Hal ini sangat penting untuk mencegah pemanggilan API berulang-ulang di setiap ketikan keyboard dan menghemat kuota request.*  
  > *Perhatikan juga blok try-catch di fungsi fetch: saya secara eksplisit menangkap `CancellationException` dan melemparnya kembali (`throw e`) agar pembatalan coroutine saat debounce tidak keliru dianggap sebagai status Error."*

---

### Bagian 6: UI Layer & Komponen Compose (Durasi: ~1.5 Menit)
- **File yang Dibuka**: 
  1. `ui/components/GameCard.kt`
  2. `ui/screen/HomeScreen.kt`
  3. `ui/screen/GameDetailScreen.kt`
- **Poin Bicara**:
  > *"Kelima, pada lapisan UI di package `ui.screen` dan `ui.components`:*  
  > *Di `GameCard.kt`, kita menampilkan cover game menggunakan pustaka Coil `AsyncImage` dengan animasi crossfade dan box fallback jika URL bernilai null. Terdapat penanganan nilai null pada rating dan tanggal rilis sehingga menampilkan fallback '-' jika belum tersedia.*  
  > *Di `HomeScreen.kt`, komponen menerapkan **State Hoisting** yang memisahkan Composable stateful dan stateless. Terdapat kolom `OutlinedTextField` untuk pencarian dan percabangan `when (uiState)` untuk merender CircularProgressIndicator saat Loading, pesan error beserta tombol Coba Lagi saat Error, dan `LazyColumn` dengan `key = { item.id }` saat Success agar rekomposisi berjalan efisien.*  
  > *Di `GameDetailScreen.kt`, data detail dipicu saat pertama kali layar terbuka menggunakan `LaunchedEffect(gameId)`. Seluruh konten dapat digulir dengan `verticalScroll`. Pada bagian deskripsi, saya membersihkan tag HTML mentah menggunakan `HtmlCompat.fromHtml()` sehingga teks deskripsi tampil bersih dan rapi."*

---

### Bagian 7: Navigasi & Integrasi di MainActivity (Durasi: ~45 Detik)
- **File yang Dibuka**: `MainActivity.kt`
- **Poin Bicara**:
  > *"Terakhir, di `MainActivity.kt`:*  
  > *Aplikasi dihubungkan menggunakan Navigation Compose dengan `NavHost` dan `rememberNavController()`. Terdapat dua rute utama: `"home"` dan `"detail/{gameId}"` dengan argumen bertipe `NavType.IntType`.*  
  > *Satu instance `GameViewModel` dibuat di level Activity (`val sharedViewModel: GameViewModel = viewModel()`) dan dioper ke HomeScreen maupun GameDetailScreen. Hal ini memastikan state daftar game dan teks pencarian di HomeScreen tidak terhapus ketika pengguna membuka detail dan menekan tombol kembali.*  
  > *Ketika gameId dikirim dari `backStackEntry`, nilainya dibaca secara aman tanpa operator `!!` menggunakan fallback `?: 0`.*  
  > *Seluruh tampilan dibungkus oleh `ResponsizahratulTheme` yang mengimplementasikan Material Design 3 dengan nuansa gaming dan mendukung Dark Mode."*

---

### Bagian 8: Penutup (Durasi: ~30 Detik)
- **Naskah Bicara**:
  > *"Demikian penjelasan komprehensif mengenai arsitektur, keamanan data null-safety, manajemen state, serta integrasi REST API pada aplikasi GameDex. Seluruh kode telah mengikuti standar yang diajarkan pada praktikum Pemrograman Mobile. Terima kasih atas perhatian Bapak/Ibu dosen."*

---

## 5 Kemungkinan Pertanyaan Dosen & Jawaban Singkat

### Pertanyaan 1: Mengapa semua field pada data class dibuat nullable (`?`) dan tidak langsung non-null?
**Jawaban**:
> *"Karena data dari REST API pihak ketiga bersifat eksternal dan tidak dapat kita kontrol sepenuhnya. Jika server mengembalikan respons di mana suatu field bernilai `null` atau tidak disertakan dalam JSON, mendeklarasikannya sebagai tipe non-null akan memicu `NullPointerException` saat deserialisasi oleh Gson dan membuat aplikasi crash. Dengan nullable, kita mematuhi prinsip null-safety Kotlin dan dapat memberikan nilai fallback yang aman di UI seperti `'-'` atau `'Belum ada rating'`."*

---

### Pertanyaan 2: Apa fungsi `by lazy` pada inisialisasi Retrofit di `ApiClient`?
**Jawaban**:
> *"Delegasi `by lazy` menerapkan pola inisialisasi malas (*lazy initialization*). Artinya, instance Retrofit dan OkHttpClient tidak langsung dialokasikan ke memori saat aplikasi pertama kali dinyalakan, melainkan baru dibuat tepat saat properti `apiService` pertama kali diakses. Selain itu, `by lazy` bersifat thread-safe dan memastikan objek tersebut hanya dibuat satu kali saja (*singleton*), sehingga sangat hemat penggunaan memori."*

---

### Pertanyaan 3: Kenapa di ViewModel fungsi pencarian menggunakan debounce 500 ms dan melempar kembali `CancellationException`?
**Jawaban**:
> *"Debounce 500 ms bertujuan menunda panggilan API sampai pengguna berhenti mengetik sejenak. Setiap kali ada huruf baru, coroutine pencarian sebelumnya dibatalkan dengan `searchJob?.cancel()`. Hal ini mencegah aplikasi mengirim puluhan request ke server di setiap ketukan keyboard, menghemat kuota rate-limit API, dan menghemat baterai HP.*  
> *Sedangkan `CancellationException` wajib dilempar kembali (*rethrow*) karena ketika suatu coroutine dibatalkan, Kotlin sengaja melempar exception ini untuk menghentikan thread coroutine tersebut secara normal. Jika ditangkap oleh `catch (e: Exception)` umum, pembatalan akibat mengetik akan keliru dianggap sebagai error jaringan dan memunculkan tampilan error di layar."*

---

### Pertanyaan 4: Apa fungsi parameter `key` pada `items` di dalam `LazyColumn`?
**Jawaban**:
> *"Secara bawaan, LazyColumn mengidentifikasi item berdasarkan posisi indeksnya. Jika kita memberikan `key = { item.id }`, Jetpack Compose akan mengenali setiap item berdasarkan ID uniknya. Keuntungannya adalah ketika daftar game diurutkan ulang, disaring, atau diperbarui, Compose hanya akan merender ulang (*recompose*) item yang benar-benar berubah posisinya atau datanya tanpa harus menggambar ulang seluruh list dari awal. Ini meningkatkan performa rendering UI secara drastis."*

---

### Pertanyaan 5: Bagaimana Anda memastikan API Key RAWG tetap aman dan tidak bocor ke publik?
**Jawaban**:
> *"Kunci API disimpan di dalam berkas `local.properties` dengan variabel `RAWG_API_KEY`. File `local.properties` secara default didaftarkan pada `.gitignore` sehingga tidak pernah ikut ter-commit ke GitHub.*  
> *Pada berkas `build.gradle.kts`, kunci ini dibaca saat kompilasi dan disuntikkan ke dalam `BuildConfig.RAWG_API_KEY`. Di dalam kode Kotlin, nilai tersebut disematkan ke setiap request melalui OkHttp Interceptor secara dinamis, sehingga tidak ada string API Key yang tertulis langsung (*hardcoded*) di dalam source code."*
