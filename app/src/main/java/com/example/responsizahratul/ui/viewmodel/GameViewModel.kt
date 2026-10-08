package com.example.responsizahratul.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.responsizahratul.data.repository.GameRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class GameViewModel(
    private val repository: GameRepository = GameRepository()
) : ViewModel() {

    // State untuk query teks pencarian
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    // State untuk daftar game (Loading, Success, Error)
    private val _uiState = MutableStateFlow<GameUiState>(GameUiState.Loading)
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    // State untuk detail game (Loading, Success, Error)
    private val _detailUiState = MutableStateFlow<GameDetailUiState>(GameDetailUiState.Loading)
    val detailUiState: StateFlow<GameDetailUiState> = _detailUiState.asStateFlow()

    // Coroutine Job untuk mengontrol debounce pada fitur pencarian
    private var searchJob: Job? = null

    init {
        // Otomatis memuat daftar awal game saat ViewModel pertama kali dibuat
        fetchGames()
    }

    fun fetchGames(query: String? = null) {
        _uiState.value = GameUiState.Loading
        viewModelScope.launch {
            try {
                val games = repository.getGames(search = query)
                _uiState.value = GameUiState.Success(games = games)
            } catch (e: kotlinx.coroutines.CancellationException) {
                // Lempar kembali CancellationException agar pembatalan coroutine (seperti debounce) tidak diperlakukan sebagai error
                throw e
            } catch (e: Exception) {
                android.util.Log.e("GameViewModel", "Gagal memuat game", e)
                _uiState.value = GameUiState.Error(
                    message = "Gagal memuat data game. Silakan periksa koneksi internet Anda."
                )
            }
        }
    }

    fun onQueryChange(newQuery: String) {
        _searchQuery.value = newQuery

        // Batalkan tugas pencarian sebelumnya yang sedang menunggu delay
        searchJob?.cancel()

        // Buat tugas coroutine baru dengan penundaan (debounce) 500 ms
        searchJob = viewModelScope.launch {
            delay(500L)
            fetchGames(query = newQuery)
        }
    }

    fun search(query: String = _searchQuery.value) {
        searchJob?.cancel()
        fetchGames(query = query)
    }

    fun loadGameDetail(id: Int) {
        _detailUiState.value = GameDetailUiState.Loading
        viewModelScope.launch {
            try {
                val detail = repository.getGameDetail(id = id)
                _detailUiState.value = GameDetailUiState.Success(game = detail)
            } catch (e: kotlinx.coroutines.CancellationException) {
                throw e
            } catch (e: Exception) {
                android.util.Log.e("GameViewModel", "Gagal memuat game", e)
                _detailUiState.value = GameDetailUiState.Error(
                    message = "Gagal memuat detail game. Silakan periksa koneksi internet Anda."
                )
            }
        }
    }
}
