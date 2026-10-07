package com.example.responsizahratul

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.responsizahratul.ui.screen.GameDetailScreen
import com.example.responsizahratul.ui.screen.HomeScreen
import com.example.responsizahratul.ui.theme.ResponsizahratulTheme
import com.example.responsizahratul.ui.viewmodel.GameViewModel

/**
 * Activity utama aplikasi GameDex.
 * Mengimplementasikan Navigation Compose sesuai materi Pertemuan 2 Bab F dan Pertemuan 4 Bab E.
 * Menggunakan satu instance GameViewModel terpusat yang dioper ke layar-layar untuk menjaga konsistensi state.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ResponsizahratulTheme {
                // Inisialisasi NavController untuk mengatur perpindahan antar layar
                val navController = rememberNavController()

                // Membuat instance GameViewModel bersama di level Activity agar state pencarian & list tetap tersimpan
                val sharedViewModel: GameViewModel = viewModel()

                NavHost(
                    navController = navController,
                    startDestination = "home",
                    modifier = Modifier.fillMaxSize()
                ) {
                    // Rute Beranda (Daftar Game & Pencarian)
                    composable(route = "home") {
                        HomeScreen(
                            viewModel = sharedViewModel,
                            onGameClick = { gameId ->
                                navController.navigate("detail/$gameId")
                            }
                        )
                    }

                    // Rute Detail Game dengan argumen ID game bertipe Int
                    composable(
                        route = "detail/{gameId}",
                        arguments = listOf(
                            navArgument("gameId") {
                                type = NavType.IntType
                            }
                        )
                    ) { backStackEntry ->
                        // Ambil gameId dengan aman dari bundle argumen tanpa operator !!
                        val gameId = backStackEntry.arguments?.getInt("gameId") ?: 0
                        GameDetailScreen(
                            gameId = gameId,
                            viewModel = sharedViewModel,
                            onBackClick = {
                                navController.popBackStack()
                            }
                        )
                    }
                }
            }
        }
    }
}