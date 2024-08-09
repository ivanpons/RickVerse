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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.PagingData
import com.llimapons.rickverse.R
import com.llimapons.rickverse.designSystem.RickVerseTheme
import com.llimapons.rickverse.designSystem.components.CharacterGrid
import com.llimapons.rickverse.designSystem.components.SearchToolbar
import com.llimapons.rickverse.domain.model.CharacterBO
import com.llimapons.rickverse.presentation.characters.CharactersActions
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun SearchScreenRoot(
    onCharacterClicked: (CharacterBO) -> Unit,
    viewModel: SearchViewModel = hiltViewModel()
) {
    SearchScreen(
        state = viewModel.searchState,
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

            CharacterGrid(
                characters = state,
                onCharacterClicked = {
                    onAction(SearchActions.CharacterClicked(it))
                }
            )

    }
}

@Preview
@Composable
fun SearchScreenPreview() {
    RickVerseTheme {
        SearchScreen(
            state = MutableStateFlow(PagingData.empty()),
            onAction = {}
        )
    }
}