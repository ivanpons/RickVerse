package com.llimapons.rickverse.presentation.locationInfo

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.llimapons.rickverse.R
import com.llimapons.rickverse.designSystem.RickVerseTheme
import com.llimapons.rickverse.designSystem.components.RickVerseTopAppBar
import com.llimapons.rickverse.domain.model.LocationBO
import com.llimapons.rickverse.domain.model.ShortLocationBO

@Composable
fun LocationInfoScreenRoot(
    locationId: Int,
    viewModel: LocationInfoViewModel = hiltViewModel()
) {

    LaunchedEffect(true) {
        viewModel.onAction(LocationInfoActions.LoadLocation(locationId))
    }

    LocationInfoScreen(
        state = viewModel.state,
        onAction = viewModel::onAction
    )
}

@Composable
private fun LocationInfoScreen(
    state: LocationInfoState,
    onAction: (LocationInfoActions) -> Unit
) {
    Scaffold(
        topBar = {
            RickVerseTopAppBar(
                title = stringResource(id = R.string.location_information),
                showBackButton = true,
                onBackClick = { onAction(LocationInfoActions.OnBackClicked) }
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

            state.location == null -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = stringResource(id = R.string.error_loading_location))
                }
            }

            else -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = padding.calculateTopPadding()),
                ) {
                    Text(text = state.location.name)
                }
            }
        }
    }
}


@Preview
@Composable
fun LocationInfoScreenPreview() {
    RickVerseTheme {
        LocationInfoScreen(
            state = LocationInfoState(
                location = LocationBO(
                    created = "",
                    id = 0,
                    name = "Rick Sanchez",
                    type = "",
                    url = "",
                    dimension = "",
                    residents = emptyList(),
                    residentsId = emptyList()
                )
            ),
            onAction = {}
        )
    }
}