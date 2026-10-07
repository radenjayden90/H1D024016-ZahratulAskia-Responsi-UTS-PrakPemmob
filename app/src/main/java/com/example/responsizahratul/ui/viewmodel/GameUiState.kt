package com.example.responsizahratul.ui.viewmodel

import com.example.responsizahratul.data.model.GameDetailResponse
import com.example.responsizahratul.data.model.GameItem

sealed interface GameUiState {

    object Loading : GameUiState

    data class Success(val games: List<GameItem>) : GameUiState

    data class Error(val message: String) : GameUiState
}

sealed interface GameDetailUiState {

    object Loading : GameDetailUiState

    data class Success(val game: GameDetailResponse) : GameDetailUiState

    data class Error(val message: String) : GameDetailUiState
}
