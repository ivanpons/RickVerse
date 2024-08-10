package com.llimapons.rickverse.presentation.episodeInfo

import com.llimapons.rickverse.domain.model.CharacterBO

sealed interface EpisodeInfoActions {
    data class LoadEpisode(val episodeId: Int) : EpisodeInfoActions
    data class CharacterClicked(val character: CharacterBO) : EpisodeInfoActions

    data object OnBackClicked: EpisodeInfoActions
}