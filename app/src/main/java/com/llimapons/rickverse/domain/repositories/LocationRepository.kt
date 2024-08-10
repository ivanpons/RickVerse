package com.llimapons.rickverse.domain.repositories

import androidx.paging.PagingData
import com.llimapons.rickverse.domain.model.LocationBO
import com.llimapons.rickverse.domain.util.DataError
import com.llimapons.rickverse.domain.util.Result
import kotlinx.coroutines.flow.Flow

interface LocationRepository {
    suspend fun getAllLocations(): Flow<PagingData<LocationBO>>
    suspend fun getLocation(locationId: Int): Result<LocationBO, DataError.Network>
}