package com.llimapons.rickverse.data.networking.model
import kotlinx.serialization.Serializable

import kotlinx.serialization.SerialName


@Serializable
data class CharacterDto(
    @SerialName("created") val created: String?,
    @SerialName("episode") val episode: List<String>?,
    @SerialName("gender") val gender: String?,
    @SerialName("id") val id: Int?,
    @SerialName("image") val image: String?,
    @SerialName("location") val location: LocationDto?,
    @SerialName("name") val name: String?,
    @SerialName("origin") val origin: LocationDto?,
    @SerialName("species") val species: String?,
    @SerialName("status") val status: String?,
    @SerialName("type") val type: String?,
    @SerialName("url") val url: String?
)


