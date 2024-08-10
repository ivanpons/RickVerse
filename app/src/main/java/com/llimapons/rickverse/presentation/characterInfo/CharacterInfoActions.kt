package com.llimapons.rickverse.presentation.characterInfo

import com.llimapons.rickverse.domain.model.ShortLocationBO

sealed interface CharacterInfoActions {
    data class LoadCharacter(val characterId: Int) : CharacterInfoActions

    data object OnBackClicked: CharacterInfoActions
    data class OnLocationClicked(val location: ShortLocationBO) : CharacterInfoActions
}