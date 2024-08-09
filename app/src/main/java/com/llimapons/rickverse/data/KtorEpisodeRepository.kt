package com.llimapons.rickverse.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.llimapons.rickverse.data.mappers.toEpisodeBo
import com.llimapons.rickverse.data.mappers.toLocationBo
import com.llimapons.rickverse.data.networking.remoteDataSource.KtorEpisodesPagingDatasource
import com.llimapons.rickverse.data.networking.remoteDataSource.KtorLocationsPagingDatasource
import com.llimapons.rickverse.domain.model.EpisodeBO
import com.llimapons.rickverse.domain.model.LocationBO
import com.llimapons.rickverse.domain.repositories.EpisodeRepository
import com.llimapons.rickverse.domain.repositories.LocationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class KtorEpisodeRepository @Inject constructor(
    private val ktorEpisodesPagingDatasource: KtorEpisodesPagingDatasource
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

}