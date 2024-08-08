package com.llimapons.rickverse.domain.model

data class CharacterBO(
    val created: String,
    val episodesId: List<String>,
    val gender: String,
    val id: Int,
    val image: String,
    val location: LocationBO,
    val name: String,
    val origin: LocationBO,
    val species: String,
    val status: String,
    val type: String,
    val url: String
)

data class LocationBO(
    val name: String,
    val id: String,
)

