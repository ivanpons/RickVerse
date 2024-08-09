package com.llimapons.rickverse.presentation.episodes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.llimapons.rickverse.domain.model.EpisodeBO
import com.llimapons.rickverse.domain.repositories.EpisodeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EpisodesViewModel @Inject constructor(
    private val episodeRepository: EpisodeRepository
): ViewModel() {

    private val _episodesState = MutableStateFlow<PagingData<EpisodeBO>>(PagingData.empty())
    val episodesState: StateFlow<PagingData<EpisodeBO>> = _episodesState

    init {
        viewModelScope.launch {
            episodeRepository.getAllEpisodes()
                .cachedIn(viewModelScope)
                .collectLatest { pagingData ->
                    _episodesState.value = pagingData
                }
        }
    }

    fun onAction(action: EpisodesActions){

    }

}