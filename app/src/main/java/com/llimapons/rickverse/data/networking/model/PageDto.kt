package com.llimapons.rickverse.data.networking.model

import kotlinx.serialization.Serializable

import kotlinx.serialization.SerialName

@Serializable
data class PageDto<T>(
    @SerialName("info") val info: InfoDto?,
    @SerialName("results") val results: List<T>?,
)