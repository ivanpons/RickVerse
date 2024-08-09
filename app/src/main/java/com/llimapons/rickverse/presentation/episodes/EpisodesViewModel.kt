package com.llimapons.rickverse.presentation.episodes

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.llimapons.rickverse.domain.model.EpisodeBO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class EpisodesViewModel @Inject constructor(): ViewModel() {

    private val _episodesState = MutableStateFlow<PagingData<EpisodeBO>>(PagingData.empty())
    val episodesState: StateFlow<PagingData<EpisodeBO>> = _episodesState

    fun onAction(action: EpisodesActions){

    }

}