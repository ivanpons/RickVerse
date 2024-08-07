package com.llimapons.rickverse.presentation.characters

sealed interface CharactersActions {
    data class CharacterClicked(val characterId: Int) : CharactersActions
}