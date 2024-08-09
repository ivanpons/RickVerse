package com.llimapons.rickverse.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.llimapons.rickverse.data.mappers.toLocationBo
import com.llimapons.rickverse.data.networking.remoteDataSource.KtorLocationsPagingDatasource
import com.llimapons.rickverse.domain.model.LocationBO
import com.llimapons.rickverse.domain.repositories.LocationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class KtorLocationRepository @Inject constructor(
    private val ktorLocationsPagingDatasource: KtorLocationsPagingDatasource
): LocationRepository {

    override suspend fun getAllLocations(): Flow<PagingData<LocationBO>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                prefetchDistance = 2,
                initialLoadSize = 40
            )
        ) {
            ktorLocationsPagingDatasource
        }.flow.map {
            it.map { locationDto ->
               locationDto.toLocationBo()
            }
        }
    }
}