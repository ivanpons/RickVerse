package com.llimapons.rickverse.presentation.characters

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.llimapons.rickverse.designSystem.RickVerseTheme

@Composable
fun CharactersScreenRoot(
    viewModel: CharactersViewModel = hiltViewModel()
){}

@Composable
private fun CharactersScreen(
    state: CharactersState,
    onAction: (CharactersActions) -> Unit
){

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