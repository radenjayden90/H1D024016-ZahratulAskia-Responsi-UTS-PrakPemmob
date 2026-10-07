package com.example.responsizahratul.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.responsizahratul.data.model.Developer
import com.example.responsizahratul.data.model.GameDetailResponse
import com.example.responsizahratul.data.model.Genre
import com.example.responsizahratul.data.model.Publisher
import com.example.responsizahratul.ui.theme.RatingGold
import com.example.responsizahratul.ui.theme.ResponsizahratulTheme
import com.example.responsizahratul.ui.viewmodel.GameDetailUiState
import com.example.responsizahratul.ui.viewmodel.GameViewModel

/**
 * Stateful composable untuk GameDetailScreen.
 * Memanggil loadGameDetail(gameId) di LaunchedEffect dan mengamati detailUiState dari GameViewModel.
 */
@Composable
fun GameDetailScreen(
    gameId: Int,
    modifier: Modifier = Modifier,
    viewModel: GameViewModel = viewModel(),
    onBackClick: () -> Unit = {}
) {
    // Muat data detail game baru saat layar dibuka atau gameId berubah
    LaunchedEffect(gameId) {
        viewModel.loadGameDetail(id = gameId)
    }

    val detailUiState by viewModel.detailUiState.collectAsState()

    GameDetailContent(
        modifier = modifier,
        uiState = detailUiState,
        onRetry = { viewModel.loadGameDetail(id = gameId) },
        onBackClick = onBackClick
    )
}

/**
 * Stateless composable untuk GameDetailScreen.
 * Mengikuti arsitektur Pertemuan 4 Bab D & Pertemuan 5 Bab H.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameDetailContent(
    uiState: GameDetailUiState,
    onRetry: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Detail Game",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Kembali ke Beranda"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                    navigationIconContentColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (uiState) {
                is GameDetailUiState.Loading -> {
                    // Indikator Loading saat mengambil data dari API
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = "Memuat detail game...",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }

                is GameDetailUiState.Error -> {
                    // Tampilan error jika koneksi gagal atau data tidak ditemukan
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Warning,
                                contentDescription = "Peringatan",
                                tint = MaterialTheme.colorScheme.error,
                                modifier = Modifier.size(48.dp)
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = uiState.message,
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Button(onClick = onRetry) {
                                Text(
                                    text = "Coba Lagi",
                                    style = MaterialTheme.typography.labelLarge
                                )
                            }
                        }
                    }
                }

                is GameDetailUiState.Success -> {
                    val game = uiState.game
                    val scrollState = rememberScrollState()

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(scrollState)
                    ) {
                        // Gambar sampul detail game dengan Coil AsyncImage
                        val imageUrl = game.backgroundImage
                        if (!imageUrl.isNullOrBlank()) {
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(imageUrl)
                                    .crossfade(true)
                                    .build(),
                                contentDescription = game.name ?: "Cover game",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(250.dp)
                            )
                        } else {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp)
                                    .background(MaterialTheme.colorScheme.surfaceVariant),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "Gambar tidak tersedia",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }

                        // Bagian Informasi Teks Detail
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            // Judul Game
                            Text(
                                text = game.name ?: "Tanpa Judul",
                                style = MaterialTheme.typography.headlineMedium,
                                color = MaterialTheme.colorScheme.onBackground
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            // Baris Rating dan Tanggal Rilis
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                // Badge Rating dengan fallback jika belum ada rating
                                val ratingValue = game.rating
                                val ratingText = if (ratingValue != null && ratingValue > 0.0) "$ratingValue / 5" else "Belum ada rating"

                                Surface(
                                    color = MaterialTheme.colorScheme.surfaceVariant,
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Row(
                                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = "★",
                                            color = RatingGold,
                                            style = MaterialTheme.typography.titleMedium
                                        )
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text(
                                            text = ratingText,
                                            style = MaterialTheme.typography.labelLarge,
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                }

                                // Tanggal rilis (format ISO 8601: YYYY-MM-DD) dengan fallback "-" jika null/kosong
                                val releaseDateText = if (!game.released.isNullOrBlank()) "Rilis: ${game.released}" else "Rilis: -"
                                Text(
                                    text = releaseDateText,
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.Medium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }

                            // Genre list
                            val genresText = game.genres?.mapNotNull { it.name }?.joinToString(", ")
                            if (!genresText.isNullOrBlank()) {
                                Spacer(modifier = Modifier.height(12.dp))
                                Text(
                                    text = "Genre: $genresText",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.primary,
                                    fontWeight = FontWeight.Medium
                                )
                            }

                            // Developer & Publisher (opsional)
                            val devText = game.developers?.mapNotNull { it.name }?.joinToString(", ")
                            if (!devText.isNullOrBlank()) {
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = "Developer: $devText",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }

                            Spacer(modifier = Modifier.height(16.dp))
                            HorizontalDivider(color = MaterialTheme.colorScheme.surfaceVariant)
                            Spacer(modifier = Modifier.height(16.dp))

                            // Bagian Deskripsi Game
                            Text(
                                text = "Deskripsi",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onBackground
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            // Bersihkan deskripsi: prioritaskan descriptionRaw murni. Jika fallback ke description HTML, bersihkan tag dengan HtmlCompat
                            val finalDescription = when {
                                !game.descriptionRaw.isNullOrBlank() -> game.descriptionRaw.trim()
                                !game.description.isNullOrBlank() -> androidx.core.text.HtmlCompat.fromHtml(
                                    game.description,
                                    androidx.core.text.HtmlCompat.FROM_HTML_MODE_COMPACT
                                ).toString().trim()
                                else -> "Deskripsi tidak tersedia"
                            }

                            Text(
                                text = finalDescription,
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurface,
                                lineHeight = 24.sp
                            )

                            Spacer(modifier = Modifier.height(24.dp))
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GameDetailScreenPreview() {
    ResponsizahratulTheme {
        GameDetailContent(
            uiState = GameDetailUiState.Success(
                game = GameDetailResponse(
                    id = 1,
                    name = "The Witcher 3: Wild Hunt",
                    rating = 4.65,
                    released = "2015-05-18",
                    descriptionRaw = "The Witcher: Wild Hunt is a story-driven open world RPG set in a visually stunning fantasy universe full of meaningful choices and impactful consequences.",
                    genres = listOf(Genre(1, "RPG"), Genre(2, "Action")),
                    developers = listOf(Developer(1, "CD PROJEKT RED")),
                    publishers = listOf(Publisher(1, "CD PROJEKT RED"))
                )
            ),
            onRetry = {},
            onBackClick = {}
        )
    }
}
