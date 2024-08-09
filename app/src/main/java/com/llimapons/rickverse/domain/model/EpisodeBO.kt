package com.llimapons.rickverse.domain.model

data class EpisodeBO(
    val airDate: String,
    val characters: List<CharacterBO>,
    val charactersId: List<String>,
    val created: String,
    val episode: String,
    val id: Int,
    val name: String,
    val url: String
)