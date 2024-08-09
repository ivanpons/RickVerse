package com.llimapons.rickverse.domain.model

data class CharacterBO(
    val created: String,
    val episodesId: List<String>,
    val gender: String,
    val id: Int,
    val image: String,
    val location: shortLocationBO,
    val name: String,
    val origin: shortLocationBO,
    val species: String,
    val status: String,
    val type: String,
    val url: String
)

data class shortLocationBO(
    val name: String,
    val id: String,
)

