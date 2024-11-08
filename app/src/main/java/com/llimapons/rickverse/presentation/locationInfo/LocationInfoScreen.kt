package com.llimapons.rickverse.presentation.locationInfo

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
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.PagingData
import com.llimapons.rickverse.R
import com.llimapons.rickverse.designSystem.RickVerseTheme
import com.llimapons.rickverse.designSystem.components.CharacterItem
import com.llimapons.rickverse.designSystem.components.PagingGrid
import com.llimapons.rickverse.designSystem.components.RickVerseTopAppBar
import com.llimapons.rickverse.domain.model.CharacterBO
import com.llimapons.rickverse.domain.model.LocationBO
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun LocationInfoScreenRoot(
    locationId: Int,
    onBackClicked: () -> Unit,
    onCharacterClicked: (CharacterBO) -> Unit,
    viewModel: LocationInfoViewModel = hiltViewModel(),
) {

    LaunchedEffect(true) {
        viewModel.onAction(LocationInfoActions.LoadLocation(locationId))
    }

    LocationInfoScreen(
        state = viewModel.state,
        onAction = {
            when (it) {
                LocationInfoActions.OnBackClicked -> onBackClicked()
                is LocationInfoActions.CharacterClicked -> onCharacterClicked(it.character)
                else -> Unit
            }
            viewModel.onAction(it)
        }
    )
}

@Composable
private fun LocationInfoScreen(
    state: LocationInfoState,
    onAction: (LocationInfoActions) -> Unit,
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
                    LocationHeader(state.location)
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = stringResource(id = R.string.residents),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onBackground,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                    if (state.location.residents.isEmpty()) {
                        Text(
                            text = stringResource(id = R.string.residents_not_found),
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onBackground,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    } else {
                        PagingGrid(
                            elements = MutableStateFlow(PagingData.from(state.location.residents)),
                            content = { character ->
                                CharacterItem(
                                    character = character,
                                    onItemClick = {
                                        onAction(LocationInfoActions.CharacterClicked(character))
                                    },
                                    size = DpSize(120.dp, 120.dp)
                                )
                            },
                            onKey = { value ->
                                value.id
                            },
                            modifier = Modifier
                                .padding(vertical = 16.dp),
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun LocationHeader(location: LocationBO) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.location),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = location.name,
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
                text = stringResource(id = R.string.type_location),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = location.type,
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
                text = stringResource(id = R.string.dimension),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = location.dimension,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.SemiBold
            )
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
                    name = "Tierra C-233",
                    type = "Planeta",
                    url = "",
                    dimension = "desconocida",
                    residents = emptyList(),
                    residentsId = emptyList()
                )
            ),
            onAction = {}
        )
    }
}