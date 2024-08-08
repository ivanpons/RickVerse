package com.llimapons.rickverse.presentation.search

import com.llimapons.rickverse.domain.model.CharacterBO

sealed interface SearchActions {
    data class CharacterClicked(val character: CharacterBO) : SearchActions
    data class SearchQueryClicked(val query: String) : SearchActions
}