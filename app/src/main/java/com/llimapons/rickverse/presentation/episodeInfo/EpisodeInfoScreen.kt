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
                    EpisodeHeader(state.episode)
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = stringResource(id = R.string.characters),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onBackground,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                    if (state.episode.characters.isEmpty()) {
                        Text(
                            text = stringResource(id = R.string.characters_not_found),
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onBackground,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    } else {
                        CharacterGrid(
                            characters = MutableStateFlow(PagingData.from(state.episode.characters)),
                            onCharacterClicked = {
                                onAction(EpisodeInfoActions.CharacterClicked(it))
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun EpisodeHeader(episode: EpisodeBO) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.title),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = episode.name,
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center
        )
    }
    Spacer(modifier = Modifier.height(16.dp))
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.air_date),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = episode.airDate,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.SemiBold
            )
        }
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.episode),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = episode.episode,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.SemiBold
            )
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
                    name = "A proposito de Rick",
                    url = "",
                    characters = emptyList(),
                    charactersId = emptyList(),
                    airDate = "25 de agosto 2025",
                    episode = "T1S2"
                )
            ),
            onAction = {}
        )
    }
}