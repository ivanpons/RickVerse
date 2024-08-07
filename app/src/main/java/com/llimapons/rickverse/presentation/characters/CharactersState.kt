package com.llimapons.rickverse.presentation.characters

import com.llimapons.rickverse.domain.model.CharacterBO

data class CharactersState(
    val characters: List<CharacterBO> = emptyList(),
    val isLoading: Boolean = false
)