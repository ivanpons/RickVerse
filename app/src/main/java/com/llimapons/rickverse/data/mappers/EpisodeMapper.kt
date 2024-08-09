package com.llimapons.rickverse.data.mappers

import com.llimapons.rickverse.data.networking.model.EpisodeDto
import com.llimapons.rickverse.data.utils.getUrlIds
import com.llimapons.rickverse.domain.model.EpisodeBO

fun EpisodeDto.toEpisodeBo(): EpisodeBO =
    EpisodeBO(
        id = id ?: 0,
        name = name ?: "",
        airDate = airDate ?: "",
        episode = episode ?: "",
        characters = listOf(),
        url = url ?: "",
        created = created ?: "",
        charactersId = getUrlIds(characters)
    )