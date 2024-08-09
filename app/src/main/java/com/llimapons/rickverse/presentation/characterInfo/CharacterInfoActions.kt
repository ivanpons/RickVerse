package com.llimapons.rickverse.presentation.characterInfo

import com.llimapons.rickverse.domain.model.CharacterBO

sealed interface CharacterInfoActions {
    data class LoadCharacter(val characterId: Int) : CharacterInfoActions

    data object OnBackClicked: CharacterInfoActions
}