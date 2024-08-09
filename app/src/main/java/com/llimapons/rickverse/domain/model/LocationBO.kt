package com.llimapons.rickverse.domain.model

data class LocationBO(
    val created: String,
    val dimension: String,
    val id: Int,
    val name: String,
    val residents: List<CharacterBO>,
    val residentsId: List<String>,
    val type: String,
    val url: String
)