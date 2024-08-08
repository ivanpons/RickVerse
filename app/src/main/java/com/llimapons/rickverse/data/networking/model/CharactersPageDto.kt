package com.llimapons.rickverse.data.networking.model
import kotlinx.serialization.Serializable

import kotlinx.serialization.SerialName


@Serializable
data class CharactersPageDto(
    @SerialName("info") val info: InfoDto?,
    @SerialName("results") val results: List<CharacterDto>?,
)

@Serializable
data class InfoDto(
    @SerialName("count") val count: Int?,
    @SerialName("next") val next: String?,
    @SerialName("pages") val pages: Int?,
    @SerialName("prev") val prev: String?
)