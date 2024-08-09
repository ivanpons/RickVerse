package com.llimapons.rickverse.presentation.locations

import com.llimapons.rickverse.domain.model.LocationBO

sealed interface LocationsActions {
    data class LocationClicked(val location: LocationBO): LocationsActions
}