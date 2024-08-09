package com.llimapons.rickverse.domain.model

data class EpisodeBO(
    val air_date: String,
    val characters: List<CharacterBO>,
    val created: String,
    val episode: String,
    val id: Int,
    val name: String,
    val url: String
)