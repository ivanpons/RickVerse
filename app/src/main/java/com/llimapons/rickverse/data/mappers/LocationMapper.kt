package com.llimapons.rickverse.data.mappers

import com.llimapons.rickverse.data.networking.model.LocationDto
import com.llimapons.rickverse.domain.model.LocationBO

fun LocationDto.toLocationBo(): LocationBO =
    LocationBO(
        id = id ?: 0,
        name = name ?: "",
        type = type ?: "",
        dimension = dimension ?: "",
        residents = listOf(),
        url = url ?: "",
        created = created ?: ""
    )