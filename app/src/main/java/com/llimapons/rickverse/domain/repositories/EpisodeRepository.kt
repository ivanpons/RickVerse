package com.llimapons.rickverse.domain.repositories

import androidx.paging.PagingData
import com.llimapons.rickverse.domain.model.EpisodeBO
import com.llimapons.rickverse.domain.model.LocationBO
import com.llimapons.rickverse.domain.util.DataError
import com.llimapons.rickverse.domain.util.Result
import kotlinx.coroutines.flow.Flow

interface EpisodeRepository {
    suspend fun getAllEpisodes(): Flow<PagingData<EpisodeBO>>
    suspend fun getEpisode(episodeId: Int): Result<EpisodeBO, DataError.Network>
}