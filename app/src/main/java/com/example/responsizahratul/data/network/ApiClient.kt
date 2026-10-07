package com.example.responsizahratul.data.network

import com.example.responsizahratul.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Singleton object untuk inisialisasi Retrofit.
 * Sesuai materi Pertemuan 5, konfigurasi instance dibungkus dalam objek singleton
 * dan menggunakan delegasi `by lazy` agar inisialisasi hanya berjalan saat pertama kali dibutuhkan.
 *
 * Menggunakan OkHttpClient Interceptor untuk menyematkan query parameter `key` (API Key RAWG)
 * secara otomatis dari BuildConfig ke setiap HTTP request.
 */
object ApiClient {

    private const val BASE_URL = "https://api.rawg.io/api/"

    // Klien HTTP dengan interceptor untuk menyematkan API key RAWG secara otomatis
    private val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor { chain ->
                val originalRequest = chain.request()
                val originalUrl = originalRequest.url

                // Menambahkan query parameter "key" dengan nilai dari BuildConfig
                val urlWithApiKey = originalUrl.newBuilder()
                    .addQueryParameter("key", BuildConfig.RAWG_API_KEY)
                    .build()

                val newRequest = originalRequest.newBuilder()
                    .url(urlWithApiKey)
                    .build()

                chain.proceed(newRequest)
            }
            .build()
    }

    /**
     * Instance ApiService Retrofit dengan konverter Gson
     */
    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    /**
     * Alias `instance` agar kompatibel dengan penamaan di materi Pertemuan 5 (ApiClient.instance)
     */
    val instance: ApiService
        get() = apiService
}
