package com.llimapons.rickverse.presentation.episodeInfo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.PagingData
import com.llimapons.rickverse.R
import com.llimapons.rickverse.designSystem.RickVerseTheme
import com.llimapons.rickverse.designSystem.components.CharacterGrid
import com.llimapons.rickverse.designSystem.components.RickVerseTopAppBar
import com.llimapons.rickverse.domain.model.CharacterBO
import com.llimapons.rickverse.domain.model.EpisodeBO
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun EpisodeInfoScreenRoot(
    episodeId: Int,
    onBackClicked: () -> Unit,
    onCharacterClicked: (CharacterBO) -> Unit,
    viewModel: EpisodeInfoViewModel = hiltViewModel(),
) {

    LaunchedEffect(true) {
        viewModel.onAction(EpisodeInfoActions.LoadEpisode(episodeId))
    }

    EpisodeInfoScreen(
        state = viewModel.state,
        onAction = {
            when (it) {
                EpisodeInfoActions.OnBackClicked -> onBackClicked()
                is EpisodeInfoActions.CharacterClicked -> onCharacterClicked(it.character)
                else -> Unit
            }
            viewModel.onAction(it)
        }
    )
}

@Composable
private fun EpisodeInfoScreen(
    state: EpisodeInfoState,
    onAction: (EpisodeInfoActions) -> Unit,
) {
    Scaffold(
        topBar = {
            RickVerseTopAppBar(
                title = stringResource(id = R.string.episode_information),
                showBackButton = true,
                onBackClick = { onAction(EpisodeInfoActions.OnBackClicked) }
            )
        }
    ) { padding ->

        when {
            state.isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            state.episode == null -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = stringResource(id = R.string.error_loading_episode))
                }
            }

            else -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = padding.calculateTopPadding()),
                ) {

                    Text(text = state.episode.name)

                }
            }
        }
    }
}


@Preview
@Composable
fun EpisodeInfoScreenPreview() {
    RickVerseTheme {
        EpisodeInfoScreen(
            state = EpisodeInfoState(
                episode = EpisodeBO(
                    created = "",
                    id = 0,
                    name = "Tierra C-233",
                    url = "",
                    characters = emptyList(),
                    charactersId = emptyList(),
                    airDate = "",
                    episode = ""
                )
            ),
            onAction = {}
        )
    }
}