package com.example.responsizahratul.data.model

import com.google.gson.annotations.SerializedName

data class GameDetailResponse(
    @SerializedName("id")
    val id: Int? = null,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("description")
    val description: String? = null,

    @SerializedName("description_raw")
    val descriptionRaw: String? = null,

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

    @SerializedName("website")
    val website: String? = null,

    @SerializedName("genres")
    val genres: List<Genre>? = null,

    @SerializedName("publishers")
    val publishers: List<Publisher>? = null,

    @SerializedName("developers")
    val developers: List<Developer>? = null
)

data class Publisher(
    @SerializedName("id")
    val id: Int? = null,

    @SerializedName("name")
    val name: String? = null
)

data class Developer(
    @SerializedName("id")
    val id: Int? = null,

    @SerializedName("name")
    val name: String? = null
)
