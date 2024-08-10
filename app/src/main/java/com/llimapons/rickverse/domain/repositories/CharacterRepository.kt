package com.llimapons.rickverse.domain.repositories

import androidx.paging.PagingData
import com.llimapons.rickverse.domain.model.CharacterBO
import com.llimapons.rickverse.domain.util.DataError
import com.llimapons.rickverse.domain.util.Result
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {
    suspend fun getAllCharacters(): Flow<PagingData<CharacterBO>>
    suspend fun searchCharacters(query: String): Flow<PagingData<CharacterBO>>
    suspend fun getCharacter(characterId: Int): Result<CharacterBO, DataError.Network>
    suspend fun getMultipleCharacters(characterIds: List<String>): Result<List<CharacterBO>, DataError.Network>
}