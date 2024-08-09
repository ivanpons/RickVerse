package com.llimapons.rickverse.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.llimapons.rickverse.data.mappers.toCharacterBO
import com.llimapons.rickverse.data.networking.get
import com.llimapons.rickverse.data.networking.model.CharacterDto
import com.llimapons.rickverse.data.networking.remoteDataSource.KtorCharactersPagingDatasource
import com.llimapons.rickverse.data.networking.remoteDataSource.KtorSearchPagingDatasource
import com.llimapons.rickverse.domain.model.CharacterBO
import com.llimapons.rickverse.domain.repositories.CharacterRepository
import com.llimapons.rickverse.domain.util.DataError
import com.llimapons.rickverse.domain.util.Result
import com.llimapons.rickverse.domain.util.map
import io.ktor.client.HttpClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class KtorCharacterRepository @Inject constructor(
    private val ktorCharactersPagingDatasource: KtorCharactersPagingDatasource,
    private val ktorSearchPagingDatasource: KtorSearchPagingDatasource,
    private val httpClient: HttpClient
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

    override suspend fun getCharacter(characterId: Int): Result<CharacterBO, DataError.Network> {
        return httpClient.get<CharacterDto>(
            route = "/api/character/$characterId"
        ).map { characterDto ->
           characterDto.toCharacterBO()
        }
    }

    override suspend fun getMultipleCharacters(characterIds: List<Int>): Result<List<CharacterBO>, DataError.Network> {
        val characterIdsString = characterIds.joinToString(",")
        return httpClient.get<List<CharacterDto>>(
            route = "/api/character/[$characterIdsString]"
        ).map { characterDtoList ->
            characterDtoList.map { characterDto ->
                characterDto.toCharacterBO()
            }
        }
    }

}