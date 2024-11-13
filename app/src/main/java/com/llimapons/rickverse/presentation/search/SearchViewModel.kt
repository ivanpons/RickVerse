package com.llimapons.rickverse.presentation.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.llimapons.rickverse.domain.model.CharacterBO
import com.llimapons.rickverse.domain.repositories.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val characterRepository: CharacterRepository
) : ViewModel() {

    private val _searchState = MutableStateFlow<PagingData<CharacterBO>>(PagingData.empty())
    val searchState: StateFlow<PagingData<CharacterBO>> = _searchState

    fun onAction(action: SearchActions) {
        when (action) {
            is SearchActions.CharacterClicked -> {}
            is SearchActions.SearchQueryClicked -> {
                search(action.query)
            }
        }
    }

    private fun search(query: String) {
        viewModelScope.launch {
            characterRepository.searchCharacters(query)
                .cachedIn(viewModelScope)
                .collectLatest { pagingData ->
                    _searchState.value = pagingData
                }
        }
    }

}