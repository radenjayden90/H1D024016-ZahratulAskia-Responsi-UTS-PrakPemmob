package com.example.responsizahratul.data.repository

import com.example.responsizahratul.data.model.GameDetailResponse
import com.example.responsizahratul.data.model.GameItem
import com.example.responsizahratul.data.network.ApiClient
import com.example.responsizahratul.data.network.ApiService

class GameRepository(
    private val apiService: ApiService = ApiClient.apiService
) {

    suspend fun getGames(search: String? = null, pageSize: Int? = 20): List<GameItem> {
        // Kirim search sebagai null jika query kosong atau hanya berisi spasi (blank)
        val queryParam = if (search.isNullOrBlank()) null else search.trim()
        val response = apiService.getGames(search = queryParam, pageSize = pageSize)
        return response.results ?: emptyList()
    }

    suspend fun getGameDetail(id: Int): GameDetailResponse {
        return apiService.getGameDetail(id = id)
    }
}
