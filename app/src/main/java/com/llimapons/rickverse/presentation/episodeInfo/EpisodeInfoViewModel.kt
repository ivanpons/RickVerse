package com.llimapons.rickverse.presentation.episodeInfo

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.llimapons.rickverse.domain.useCase.GetFullEpisodeUseCase
import com.llimapons.rickverse.domain.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EpisodeInfoViewModel @Inject constructor(
    private val getFullEpisodeUseCase: GetFullEpisodeUseCase,
) : ViewModel() {

    var state by mutableStateOf(EpisodeInfoState())
        private set

    init {
        state = state.copy(isLoading = true)
    }

    fun onAction(action: EpisodeInfoActions) {
        when (action) {
            EpisodeInfoActions.OnBackClicked -> Unit
            is EpisodeInfoActions.LoadEpisode -> {
                getFullEpisode(action.episodeId)
            }

            is EpisodeInfoActions.CharacterClicked -> Unit
        }
    }

    private fun getFullEpisode(episodeId: Int) {
        viewModelScope.launch {
            state = when (val result = getFullEpisodeUseCase(episodeId)) {
                is Result.Error -> {
                    state.copy(isLoading = false)
                }

                is Result.Success -> {
                    state.copy(
                        isLoading = false,
                        episode = result.data
                    )
                }
            }
        }
    }

}