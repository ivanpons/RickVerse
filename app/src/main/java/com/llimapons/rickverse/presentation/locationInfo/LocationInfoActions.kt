package com.llimapons.rickverse.presentation.locationInfo

import com.llimapons.rickverse.domain.model.CharacterBO

sealed interface LocationInfoActions {
    data class LoadLocation(val locationId: Int) : LocationInfoActions
    data class CharacterClicked(val character: CharacterBO) : LocationInfoActions

    data object OnBackClicked: LocationInfoActions
}