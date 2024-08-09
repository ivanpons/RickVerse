package com.llimapons.rickverse.presentation.locations

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
import com.llimapons.rickverse.domain.model.LocationBO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun LocationsScreenRoot(
    viewModel: LocationsViewModel = hiltViewModel()
) {
    LocationsScreen(
        state = viewModel.locationsState,
        onAction = viewModel::onAction
    )
}

@Composable
private fun LocationsScreen(
    state: StateFlow<PagingData<LocationBO>>,
    onAction: (LocationsActions) -> Unit
) {
    Scaffold(
        topBar = {
            RickVerseTopAppBar(title = stringResource(id = R.string.locations))
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = padding.calculateTopPadding()),
        ) {
            ListTextPaging(
                items = state,
                onItemClicked = { onAction(LocationsActions.LocationClicked(it)) }
                )
        }
    }
}

@Preview
@Composable
fun LocationsScreenPreview() {
    RickVerseTheme {
        LocationsScreen(
            state = MutableStateFlow(PagingData.empty()),
            onAction = {}
        )
    }
}