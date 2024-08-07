package com.llimapons.rickverse.data.networking.model
import kotlinx.serialization.Serializable

import kotlinx.serialization.SerialName


@Serializable
data class LocationDto(
    @SerialName("created") val created: String?,
    @SerialName("dimension") val dimension: String?,
    @SerialName("id") val id: Int?,
    @SerialName("name") val name: String?,
    @SerialName("residents") val residents: List<String>?,
    @SerialName("type") val type: String?,
    @SerialName("url") val url: String?
)