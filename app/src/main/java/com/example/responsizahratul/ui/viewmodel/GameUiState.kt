package com.example.responsizahratul.ui.viewmodel

import com.example.responsizahratul.data.model.GameDetailResponse
import com.example.responsizahratul.data.model.GameItem

/**
 * State untuk merepresentasikan kondisi tampilan (UI State) pada daftar game.
 * Mengikuti pola Sealed Interface dari materi Pertemuan 5.
 */
sealed interface GameUiState {
    /**
     * Kondisi saat data sedang diambil dari API RAWG (menampilkan indikator loading)
     */
    object Loading : GameUiState

    /**
     * Kondisi saat pemanggilan API berhasil mengembalikan daftar game
     * @param games Kumpulan data game yang berhasil dimuat
     */
    data class Success(val games: List<GameItem>) : GameUiState

    /**
     * Kondisi saat terjadi kegagalan jaringan atau parsing
     * @param message Pesan kesalahan dalam Bahasa Indonesia
     */
    data class Error(val message: String) : GameUiState
}

/**
 * State untuk merepresentasikan kondisi tampilan (UI State) pada layar detail game.
 */
sealed interface GameDetailUiState {
    /**
     * Kondisi saat data detail sedang dimuat (menampilkan indikator loading)
     */
    object Loading : GameDetailUiState

    /**
     * Kondisi saat data detail game berhasil diperoleh
     * @param game Objek detail lengkap game
     */
    data class Success(val game: GameDetailResponse) : GameDetailUiState

    /**
     * Kondisi saat terjadi kesalahan ketika mengambil detail game
     * @param message Pesan kesalahan dalam Bahasa Indonesia
     */
    data class Error(val message: String) : GameDetailUiState
}
