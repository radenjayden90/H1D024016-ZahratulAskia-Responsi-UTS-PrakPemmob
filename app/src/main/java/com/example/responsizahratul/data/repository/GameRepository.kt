package com.example.responsizahratul.data.repository

import com.example.responsizahratul.data.model.GameDetailResponse
import com.example.responsizahratul.data.model.GameItem
import com.example.responsizahratul.data.network.ApiClient
import com.example.responsizahratul.data.network.ApiService

/**
 * Repository yang bertindak sebagai sumber data terpusat (Single Source of Truth) untuk data Game.
 * Menghubungkan lapisan jaringan (ApiService) dengan ViewModel sesuai arsitektur MVVM.
 */
class GameRepository(
    private val apiService: ApiService = ApiClient.apiService
) {

    /**
     * Mengambil daftar game dari RAWG API.
     * Mengembalikan list kosong jika list respons bernilai null untuk menjamin keamanan null (null-safety).
     *
     * @param search Kata kunci pencarian nama game (opsional)
     * @param pageSize Batas jumlah game yang dimuat (default 20)
     * @return List GameItem yang valid
     */
    suspend fun getGames(search: String? = null, pageSize: Int? = 20): List<GameItem> {
        // Kirim search sebagai null jika query kosong atau hanya berisi spasi (blank)
        val queryParam = if (search.isNullOrBlank()) null else search.trim()
        val response = apiService.getGames(search = queryParam, pageSize = pageSize)
        return response.results ?: emptyList()
    }

    /**
     * Mengambil data detail lengkap suatu game berdasarkan ID game.
     *
     * @param id ID unik game
     * @return GameDetailResponse detail game
     */
    suspend fun getGameDetail(id: Int): GameDetailResponse {
        return apiService.getGameDetail(id = id)
    }
}
