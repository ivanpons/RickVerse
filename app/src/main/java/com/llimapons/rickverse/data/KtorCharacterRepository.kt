package com.llimapons.rickverse.data

import android.util.Log
import com.llimapons.rickverse.data.mappers.toCharacterBO
import com.llimapons.rickverse.data.networking.get
import com.llimapons.rickverse.data.networking.model.CharacterDto
import com.llimapons.rickverse.data.networking.model.CharactersPageDto
import com.llimapons.rickverse.domain.model.CharacterBO
import com.llimapons.rickverse.domain.repositories.CharacterRepository
import com.llimapons.rickverse.domain.util.DataError
import com.llimapons.rickverse.domain.util.Result
import com.llimapons.rickverse.domain.util.map
import io.ktor.client.HttpClient
import javax.inject.Inject

class KtorCharacterRepository @Inject constructor(
    private val httpClient: HttpClient
): CharacterRepository {

    override suspend fun getAllCharacters(): Result<List<CharacterBO>, DataError.Network> {
        Log.e("KtorCharacterRepository", "getAllCharacters call ")
       return httpClient.get<CharactersPageDto>(
           route = "/api/character"
       ).map { page ->
           page.results?.map {
               it.toCharacterBO()
           } ?: emptyList()
       }
    }
}