package com.llimapons.rickverse.presentation.episodes

import com.llimapons.rickverse.domain.model.EpisodeBO

sealed interface EpisodesActions {
    data class EpisodeClicked(val episode: EpisodeBO) : EpisodesActions
}