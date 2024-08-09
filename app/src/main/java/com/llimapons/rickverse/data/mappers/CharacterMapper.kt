package com.llimapons.rickverse.data.mappers

import com.llimapons.rickverse.data.networking.model.CharacterDto
import com.llimapons.rickverse.data.networking.model.ShortLocationDto
import com.llimapons.rickverse.data.utils.getUrlIds
import com.llimapons.rickverse.domain.model.CharacterBO
import com.llimapons.rickverse.domain.model.ShortLocationBO

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
        episodesId = getUrlIds(this.episode),
    )

fun ShortLocationDto?.toLocationBO(): ShortLocationBO =
    ShortLocationBO(
        name = this?.name ?: "",
        id = this?.url?.substringAfterLast("/") ?: ""
    )

