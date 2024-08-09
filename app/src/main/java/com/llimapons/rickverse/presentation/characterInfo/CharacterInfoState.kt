package com.llimapons.rickverse.presentation.characterInfo

import com.llimapons.rickverse.domain.model.CharacterBO

data class CharacterInfoState(
    val isLoading: Boolean = false,
    val character: CharacterBO? = null,
    val shouldShowEpisodesList: Boolean = false,
    )
