package com.llimapons.rickverse.presentation.characters

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.PagingData
import com.llimapons.rickverse.R
import com.llimapons.rickverse.designSystem.RickVerseTheme
import com.llimapons.rickverse.designSystem.components.CharacterGrid
import com.llimapons.rickverse.designSystem.components.RickVerseTopAppBar
import com.llimapons.rickverse.domain.model.CharacterBO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun CharactersScreenRoot(
    viewModel: CharactersViewModel = hiltViewModel()
) {
    CharactersScreen(
        state = viewModel.charactersState,
        onAction = viewModel::onAction
    )
}

@Composable
private fun CharactersScreen(
    state: StateFlow<PagingData<CharacterBO>>,
    onAction: (CharactersActions) -> Unit
) {
    Scaffold(
        topBar = {
            RickVerseTopAppBar(title = stringResource(id = R.string.characters))
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = padding.calculateTopPadding()),
        ) {
            CharacterGrid(
                characters = state,
                onCharacterClicked = {
                    onAction(CharactersActions.CharacterClicked(it))
                }
            )
        }
    }
}

@Preview
@Composable
fun CharactersScreenPreview() {
    RickVerseTheme {
        CharactersScreen(
            state = MutableStateFlow(PagingData.empty()),
            onAction = {}
        )
    }
}