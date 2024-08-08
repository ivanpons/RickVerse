package com.llimapons.rickverse.data.mappers

import com.llimapons.rickverse.data.networking.model.CharacterDto
import com.llimapons.rickverse.data.networking.model.LocationDto
import com.llimapons.rickverse.domain.model.CharacterBO
import com.llimapons.rickverse.domain.model.LocationBO

fun CharacterDto.toCharacterBO(): CharacterBO =
    CharacterBO(
        created = this.created ?: "",
        gender = this.gender ?: "",
        id = this.id ?: -1,
        image = this.image ?: "",
        name = this.name ?: "",
        species = this.species ?: "",
        status = this.status ?: "",
        url = this.url ?: "",
        type = this.type ?: "",
        location = this.location.toLocationBO(),
        origin = this.origin.toLocationBO(),
        episodesId = getEpisodesIds(this.episode),
    )

fun LocationDto?.toLocationBO(): LocationBO =
    LocationBO(
        name = this?.name ?: "",
        id = this?.url?.substringAfterLast("/") ?: ""
    )

private fun getEpisodesIds(episodes: List<String>?): List<String> {
    return episodes?.map { episodeUrl ->
        val episodeId = episodeUrl.substringAfterLast("/")
        episodeId
    } ?: emptyList()
}