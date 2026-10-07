package com.example.responsizahratul.data.network

import com.example.responsizahratul.data.model.GameDetailResponse
import com.example.responsizahratul.data.model.GameResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Interface Retrofit yang mendefinisikan endpoint RAWG API.
 * Sesuai materi Pertemuan 5, fungsi menggunakan keyword suspend agar berjalan asinkron di coroutine (background thread).
 */
interface ApiService {

    /**
     * Mengambil daftar game dari RAWG API.
     * Endpoint: GET /games
     * @param search Kata kunci pencarian judul game (opsional)
     * @param pageSize Jumlah data per halaman (opsional)
     */
    @GET("games")
    suspend fun getGames(
        @Query("search") search: String? = null,
        @Query("page_size") pageSize: Int? = null
    ): GameResponse

    /**
     * Mengambil informasi detail dari game tertentu berdasarkan ID game.
     * Endpoint: GET /games/{id}
     * @param id ID unik dari game di database RAWG
     */
    @GET("games/{id}")
    suspend fun getGameDetail(
        @Path("id") id: Int
    ): GameDetailResponse
}
