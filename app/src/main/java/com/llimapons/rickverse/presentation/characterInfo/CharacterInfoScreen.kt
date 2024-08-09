package com.llimapons.rickverse.presentation.characterInfo

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.llimapons.rickverse.designSystem.RickVerseTheme

@Composable
fun CharacterInfoScreenRoot(
    characterId: Int,
    viewModel: CharacterInfoViewModel = hiltViewModel()
) {
    viewModel.onAction(CharacterInfoActions.LoadCharacter(characterId))
    CharacterInfoScreen(
        state = viewModel.state,
        onAction = viewModel::onAction
    )
}

@Composable
private fun CharacterInfoScreen(
    state: CharacterInfoState,
    onAction: (CharacterInfoActions) -> Unit
) {
    Scaffold { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = padding.calculateTopPadding()),
            contentAlignment = Alignment.Center
        ) {
            Text(text = state.character?.name ?: "nulo")
        }
    }
}

@Preview
@Composable
fun CharacterInfoScreenPreview() {
    RickVerseTheme {
        CharacterInfoScreen(
            state = CharacterInfoState(),
            onAction = {}
        )
    }
}