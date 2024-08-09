package com.llimapons.rickverse.domain.repositories

import androidx.paging.PagingData
import com.llimapons.rickverse.domain.model.EpisodeBO
import com.llimapons.rickverse.domain.model.LocationBO
import kotlinx.coroutines.flow.Flow

interface EpisodeRepository {
    suspend fun getAllEpisodes(): Flow<PagingData<EpisodeBO>>

}