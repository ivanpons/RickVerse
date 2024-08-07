package com.llimapons.rickverse.presentation.characters

import com.llimapons.rickverse.domain.model.CharacterBO

sealed interface CharactersActions {
    data class CharacterClicked(val character: CharacterBO) : CharactersActions
}