package com.llimapons.rickverse.presentation.locationInfo

import com.llimapons.rickverse.domain.model.LocationBO

data class LocationInfoState(
    val isLoading: Boolean = false,
    val location: LocationBO? = null,
    )
