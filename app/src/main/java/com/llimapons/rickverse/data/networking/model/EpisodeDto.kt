package com.llimapons.rickverse.data.networking.model
import kotlinx.serialization.Serializable

import kotlinx.serialization.SerialName


@Serializable
data class EpisodeDto(
    @SerialName("air_date") val airDate: String?,
    @SerialName("characters") val characters: List<String>?,
    @SerialName("created") val created: String?,
    @SerialName("episode") val episode: String?,
    @SerialName("id") val id: Int?,
    @SerialName("name") val name: String?,
    @SerialName("url") val url: String?
)