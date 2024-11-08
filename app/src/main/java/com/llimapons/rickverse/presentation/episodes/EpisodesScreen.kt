package com.llimapons.rickverse.presentation.episodes

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.PagingData
import com.llimapons.rickverse.R
import com.llimapons.rickverse.designSystem.RickVerseTheme
import com.llimapons.rickverse.designSystem.components.PagingList
import com.llimapons.rickverse.designSystem.components.RickVerseTopAppBar
import com.llimapons.rickverse.designSystem.components.TextItem
import com.llimapons.rickverse.domain.model.EpisodeBO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun EpisodesScreenRoot(
    onEpisodeClicked: (EpisodeBO) -> Unit,
    viewModel: EpisodesViewModel = hiltViewModel()
) {
    EpisodesScreen(
        state = viewModel.episodesState,
        onAction = {
            when (it) {
                is EpisodesActions.EpisodeClicked -> onEpisodeClicked(it.episode)
                else -> Unit
            }
            viewModel.onAction(it)
        }
    )
}

@Composable
private fun EpisodesScreen(
    state: StateFlow<PagingData<EpisodeBO>>,
    onAction: (EpisodesActions) -> Unit
) {
    Scaffold(
        topBar = {
            RickVerseTopAppBar(title = stringResource(id = R.string.episodes))
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = padding.calculateTopPadding()),
        ) {
            PagingList(
                items = state,
                content = { episode ->
                    TextItem(
                        text = episode.episode + " - " + episode.name,
                        onItemClicked = {
                            onAction(EpisodesActions.EpisodeClicked(episode))
                        }
                    )
                },
                modifier = Modifier
                    .padding(vertical = 16.dp)
            )
        }
    }
}

@Preview
@Composable
fun EpisodesScreenPreview() {
    RickVerseTheme {
        EpisodesScreen(
            state = MutableStateFlow(PagingData.empty()),
            onAction = {}
        )
    }
}