package com.llimapons.rickverse.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.llimapons.rickverse.data.mappers.toCharacterBO
import com.llimapons.rickverse.data.networking.remoteDataSource.KtorCharactersPagingDatasource
import com.llimapons.rickverse.data.networking.remoteDataSource.KtorSearchPagingDatasource
import com.llimapons.rickverse.domain.model.CharacterBO
import com.llimapons.rickverse.domain.repositories.CharacterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class KtorCharacterRepository @Inject constructor(
    private val ktorCharactersPagingDatasource: KtorCharactersPagingDatasource,
    private val ktorSearchPagingDatasource: KtorSearchPagingDatasource
) : CharacterRepository {

    override suspend fun getAllCharacters(): Flow<PagingData<CharacterBO>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                prefetchDistance = 2,
                initialLoadSize = 40
            )
        ) {
            ktorCharactersPagingDatasource
        }.flow.map {
            it.map { characterDto ->
                characterDto.toCharacterBO()
            }
        }
    }

    override suspend fun searchCharacters(query: String): Flow<PagingData<CharacterBO>> {
        ktorSearchPagingDatasource.setQuery(query)
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                prefetchDistance = 2,
                initialLoadSize = 40
            )
        ) {
            ktorSearchPagingDatasource
        }.flow.map {
            it.map { characterDto ->
                characterDto.toCharacterBO()
            }
        }
    }

}