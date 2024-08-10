package com.llimapons.rickverse.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.llimapons.rickverse.data.mappers.toEpisodeBo
import com.llimapons.rickverse.data.mappers.toLocationBo
import com.llimapons.rickverse.data.networking.get
import com.llimapons.rickverse.data.networking.model.EpisodeDto
import com.llimapons.rickverse.data.networking.model.LocationDto
import com.llimapons.rickverse.data.networking.remoteDataSource.KtorEpisodesPagingDatasource
import com.llimapons.rickverse.data.networking.remoteDataSource.KtorLocationsPagingDatasource
import com.llimapons.rickverse.domain.model.EpisodeBO
import com.llimapons.rickverse.domain.model.LocationBO
import com.llimapons.rickverse.domain.repositories.EpisodeRepository
import com.llimapons.rickverse.domain.repositories.LocationRepository
import com.llimapons.rickverse.domain.util.DataError
import com.llimapons.rickverse.domain.util.Result
import com.llimapons.rickverse.domain.util.map
import io.ktor.client.HttpClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class KtorEpisodeRepository @Inject constructor(
    private val ktorEpisodesPagingDatasource: KtorEpisodesPagingDatasource,
    private val httpClient: HttpClient
): EpisodeRepository {

    override suspend fun getAllEpisodes(): Flow<PagingData<EpisodeBO>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                prefetchDistance = 2,
                initialLoadSize = 40
            )
        ) {
            ktorEpisodesPagingDatasource
        }.flow.map {
            it.map { episodeDto ->
               episodeDto.toEpisodeBo()
            }
        }
    }

    override suspend fun getEpisode(episodeId: Int): Result<EpisodeBO, DataError.Network> {
        return httpClient.get<EpisodeDto>(
            route = "/api/episode/$episodeId"
        ).map { episodeDto ->
            episodeDto.toEpisodeBo()
        }
    }

}