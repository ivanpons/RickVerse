package com.llimapons.rickverse.presentation.characters

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.llimapons.rickverse.R
import com.llimapons.rickverse.designSystem.RickVerseTheme
import com.llimapons.rickverse.designSystem.components.CharacterGrid
import com.llimapons.rickverse.designSystem.components.RickVerseTopAppBar

@Composable
fun CharactersScreenRoot(
    viewModel: CharactersViewModel = hiltViewModel()
){
    CharactersScreen(
        state = viewModel.state,
        onAction = viewModel::onAction
    )
}

@Composable
private fun CharactersScreen(
    state: CharactersState,
    onAction: (CharactersActions) -> Unit
){
    Scaffold(
        topBar = {
            RickVerseTopAppBar(title = stringResource(id = R.string.characters))
        }
    ) { padding ->
        if (state.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }else{
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
            ) {
                CharacterGrid(
                    characters = state.characters,
                    onCharacterClicked = {
                        onAction(CharactersActions.CharacterClicked(it))
                    }
                )
            }
        }
    }
}

@Preview
@Composable
fun CharactersScreenPreview() {
    RickVerseTheme {
        CharactersScreen(
            state = CharactersState(),
            onAction = {}
        )
    }
}