package com.llimapons.rickverse.presentation.characterInfo

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.llimapons.rickverse.domain.repositories.CharacterRepository
import com.llimapons.rickverse.domain.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterInfoViewModel @Inject constructor(
    private val characterRepository: CharacterRepository
) : ViewModel() {

    var state by mutableStateOf(CharacterInfoState())
        private set

    init {
        state = state.copy(isLoading = true)
    }

    fun onAction(action: CharacterInfoActions) {
        when (action) {
            CharacterInfoActions.OnBackClicked -> {}
            is CharacterInfoActions.LoadCharacter -> {
              getCharacter(action.characterId)
            }

            is CharacterInfoActions.OnLocationClicked -> Unit
        }
    }

    private fun getCharacter(characterId: Int) {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            state = when (val result = characterRepository.getCharacter(characterId)) {
                is Result.Error -> {
                    state.copy(isLoading = false)
                }

                is Result.Success -> {
                    state.copy(character = result.data, isLoading = false)
                }
            }
        }
    }
}