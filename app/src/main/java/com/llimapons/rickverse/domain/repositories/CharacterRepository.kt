package com.llimapons.rickverse.domain.repositories

import com.llimapons.rickverse.domain.model.CharacterBO
import com.llimapons.rickverse.domain.util.DataError
import com.llimapons.rickverse.domain.util.Result

interface CharacterRepository {
    suspend fun getAllCharacters(): Result<List<CharacterBO>, DataError.Network>
}