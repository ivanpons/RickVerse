package com.llimapons.rickverse.presentation.locationInfo

sealed interface LocationInfoActions {
    data class LoadLocation(val locationId: Int) : LocationInfoActions

    data object OnBackClicked: LocationInfoActions
}