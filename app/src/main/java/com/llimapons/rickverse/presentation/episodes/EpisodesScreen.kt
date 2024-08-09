package com.llimapons.rickverse.presentation.episodes

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
import com.llimapons.rickverse.designSystem.components.ListTextPaging
import com.llimapons.rickverse.designSystem.components.RickVerseTopAppBar
import com.llimapons.rickverse.domain.model.EpisodeBO
import com.llimapons.rickverse.domain.model.LocationBO
import com.llimapons.rickverse.presentation.locations.LocationsActions
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun EpisodesScreenRoot(
    viewModel: EpisodesViewModel = hiltViewModel()
) {
    EpisodesScreen(
        state = viewModel.episodesState,
        onAction = viewModel::onAction
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
            ListTextPaging(
                items = state,
                onItemClicked = { onAction(EpisodesActions.EpisodeClicked(it)) }
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