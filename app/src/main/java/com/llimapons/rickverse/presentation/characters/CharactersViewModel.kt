package com.llimapons.rickverse.presentation.characters

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
class CharactersViewModel @Inject constructor(
    private val characterRepository: CharacterRepository
): ViewModel() {

    var state by mutableStateOf(CharactersState())
        private set

    init {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            state = when(val result = characterRepository.getAllCharacters()){
                is Result.Error -> {
                    state.copy(
                        isLoading = false,
                        characters = emptyList()
                    )
                }

                is Result.Success -> {
                    state.copy(
                        isLoading = false,
                        characters = result.data
                    )
                }
            }
        }
    }

    fun onAction(action: CharactersActions){

    }
}