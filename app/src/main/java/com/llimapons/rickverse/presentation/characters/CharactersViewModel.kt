package com.llimapons.rickverse.presentation.characters

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.llimapons.rickverse.domain.model.CharacterBO
import com.llimapons.rickverse.domain.repositories.CharacterRepository
import com.llimapons.rickverse.domain.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val characterRepository: CharacterRepository
): ViewModel() {

    private val _charactersState = MutableStateFlow<PagingData<CharacterBO>>(PagingData.empty())
    val charactersState: StateFlow<PagingData<CharacterBO>> = _charactersState


    init {
        viewModelScope.launch {
            characterRepository.getAllCharacters()
                .cachedIn(viewModelScope)
                .collectLatest { pagingData ->
                _charactersState.value = pagingData
            }
        }
    }

    fun onAction(action: CharactersActions){

    }
}