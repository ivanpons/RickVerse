package com.llimapons.rickverse.presentation.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.PagingData
import com.llimapons.rickverse.R
import com.llimapons.rickverse.designSystem.RickVerseTheme
import com.llimapons.rickverse.designSystem.components.CharacterItem
import com.llimapons.rickverse.designSystem.components.PagingGrid
import com.llimapons.rickverse.designSystem.components.SearchToolbar
import com.llimapons.rickverse.domain.model.CharacterBO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun SearchScreenRoot(
    onCharacterClicked: (CharacterBO) -> Unit,
    viewModel: SearchViewModel = hiltViewModel()
) {
    SearchScreen(
        state = viewModel.searchState,
        searchedQuery = viewModel.searchQuery,
        onAction = { action ->
            when (action) {
                is SearchActions.CharacterClicked -> {
                    onCharacterClicked(action.character)
                }

                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}

@Composable
private fun SearchScreen(
    state: StateFlow<PagingData<CharacterBO>>,
    searchedQuery: String,
    onAction: (SearchActions) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 36.dp),
    ) {
        var query by rememberSaveable { mutableStateOf("") }
        SearchToolbar(
            query = query,
            hint = stringResource(id = R.string.character),
            onQueryChanged = { newQuery ->
                query = newQuery
            },
            onSearchClicked = {
                onAction(SearchActions.SearchQueryClicked(query))
            }
        )

        PagingGrid(
            elements = state,
            content = { character ->
                CharacterItem(
                    character = character,
                    onItemClick = {
                        onAction(SearchActions.CharacterClicked(character))
                    },
                    size = DpSize(120.dp, 120.dp)
                )
            },
            onKey = { value ->
                value.id
            },
            modifier = Modifier
                .padding(vertical = 16.dp),
            search = searchedQuery
        )

    }
}

@Preview
@Composable
fun SearchScreenPreview() {
    RickVerseTheme {
        SearchScreen(
            state = MutableStateFlow(PagingData.empty()),
            searchedQuery = "",
            onAction = {}
        )
    }
}