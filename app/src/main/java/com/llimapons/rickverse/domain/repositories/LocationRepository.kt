package com.llimapons.rickverse.domain.repositories

import androidx.paging.PagingData
import com.llimapons.rickverse.domain.model.LocationBO
import kotlinx.coroutines.flow.Flow

interface LocationRepository {
    suspend fun getAllLocations(): Flow<PagingData<LocationBO>>

}