package com.llimapons.rickverse.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.llimapons.rickverse.data.mappers.toCharacterBO
import com.llimapons.rickverse.data.mappers.toLocationBo
import com.llimapons.rickverse.data.networking.get
import com.llimapons.rickverse.data.networking.model.CharacterDto
import com.llimapons.rickverse.data.networking.model.LocationDto
import com.llimapons.rickverse.data.networking.remoteDataSource.KtorLocationsPagingDatasource
import com.llimapons.rickverse.domain.model.LocationBO
import com.llimapons.rickverse.domain.repositories.LocationRepository
import com.llimapons.rickverse.domain.util.DataError
import com.llimapons.rickverse.domain.util.Result
import com.llimapons.rickverse.domain.util.map
import io.ktor.client.HttpClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class KtorLocationRepository @Inject constructor(
    private val ktorLocationsPagingDatasource: KtorLocationsPagingDatasource,
    private val httpClient: HttpClient
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

    override suspend fun getLocation(locationId: Int): Result<LocationBO, DataError.Network> {
        return httpClient.get<LocationDto>(
            route = "/api/location/$locationId"
        ).map { locationDto ->
            locationDto.toLocationBo()
        }
    }
}