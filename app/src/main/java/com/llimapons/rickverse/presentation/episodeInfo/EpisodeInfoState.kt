package com.llimapons.rickverse.presentation.episodeInfo

import com.llimapons.rickverse.domain.model.EpisodeBO

data class EpisodeInfoState(
    val isLoading: Boolean = false,
    val episode: EpisodeBO? = null,
)
