package com.example.responsizahratul.data.model

import com.google.gson.annotations.SerializedName

/**
 * Model pembungkus respons dari endpoint daftar game (GET /games) RAWG API.
 * Sesuai pedoman null-safety, semua field dibuat nullable (?) untuk menghindari crash akibat data kosong.
 */
data class GameResponse(
    @SerializedName("count")
    val count: Int? = null,

    @SerializedName("next")
    val next: String? = null,

    @SerializedName("previous")
    val previous: String? = null,

    @SerializedName("results")
    val results: List<GameItem>? = null
)

/**
 * Model data ringkas untuk setiap item game dalam daftar game.
 * Menyimpan informasi utama seperti nama, tanggal rilis, gambar, rating, dan genre.
 */
data class GameItem(
    @SerializedName("id")
    val id: Int? = null,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("released")
    val released: String? = null,

    @SerializedName("background_image")
    val backgroundImage: String? = null,

    @SerializedName("rating")
    val rating: Double? = null,

    @SerializedName("rating_top")
    val ratingTop: Int? = null,

    @SerializedName("ratings_count")
    val ratingsCount: Int? = null,

    @SerializedName("metacritic")
    val metacritic: Int? = null,

    @SerializedName("playtime")
    val playtime: Int? = null,

    @SerializedName("genres")
    val genres: List<Genre>? = null
)

/**
 * Model data genre game (misalnya: Action, RPG, Adventure).
 */
data class Genre(
    @SerializedName("id")
    val id: Int? = null,

    @SerializedName("name")
    val name: String? = null
)
