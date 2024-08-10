package com.llimapons.rickverse.presentation.characterInfo

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import com.llimapons.rickverse.domain.model.CharacterBO
import com.llimapons.rickverse.domain.model.ShortLocationBO

@Composable
fun CharacterInfoScreenRoot(
    characterId: Int,
    onBackClicked: () -> Unit,
    onLocationClicked: (String) -> Unit,
    viewModel: CharacterInfoViewModel = hiltViewModel(),
) {

    LaunchedEffect(true) {
        viewModel.onAction(CharacterInfoActions.LoadCharacter(characterId))
    }

    CharacterInfoScreen(
        state = viewModel.state,
        onAction = {
            when (it) {
                CharacterInfoActions.OnBackClicked -> onBackClicked()
                is CharacterInfoActions.OnLocationClicked -> onLocationClicked(it.location.id)
                else -> Unit
            }
            viewModel.onAction(it)
        }
    )
}

@Composable
private fun CharacterInfoScreen(
    state: CharacterInfoState,
    onAction: (CharacterInfoActions) -> Unit,
) {
    Scaffold(
        topBar = {
            RickVerseTopAppBar(
                title = stringResource(id = R.string.character_information),
                showBackButton = true,
                onBackClick = { onAction(CharacterInfoActions.OnBackClicked) }
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

            state.character == null -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = stringResource(id = R.string.error_loading_character))
                }
            }

            else -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = padding.calculateTopPadding()),
                ) {
                    CharacterHeader(
                        imageUrl = state.character.image,
                        name = state.character.name,
                        status = state.character.status,
                        gender = state.character.gender,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth(0.5f),
                        ) {
                            Text(
                                text = stringResource(id = R.string.specie),
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = state.character.species,
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onBackground,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                        Column {
                            Text(
                                text = stringResource(id = R.string.status),
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = state.character.status,
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onBackground,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Column(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                    ) {
                        Text(
                            text = stringResource(id = R.string.origin),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = state.character.origin.name,
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onBackground,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.weight(1f)
                            )
                            if (state.character.origin.name.lowercase() != "unknown") {
                                Text(
                                    text = stringResource(id = R.string.go_to_location),
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    fontWeight = FontWeight.SemiBold,
                                    modifier = Modifier.clickable {
                                        onAction(
                                            CharacterInfoActions.OnLocationClicked(
                                                state.character.origin
                                            )
                                        )

                                    }
                                )
                            }
                        }

                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Column(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                    ) {
                        Text(
                            text = stringResource(id = R.string.last_location),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = state.character.location.name,
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onBackground,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.weight(1f)
                            )
                            if (state.character.location.name.lowercase() != "unknown") {
                                Text(
                                    text = stringResource(id = R.string.go_to_location),
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    fontWeight = FontWeight.SemiBold,
                                    modifier = Modifier.clickable {
                                        onAction(
                                            CharacterInfoActions.OnLocationClicked(
                                                state.character.location
                                            )
                                        )

                                    }
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Column(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                    ) {
                        Text(
                            text = stringResource(id = R.string.total_episodes),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = state.character.episodesId.size.toString(),
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onBackground,
                                fontWeight = FontWeight.SemiBold
                            )
                            if (state.character.episodesId.isNotEmpty()) {
                                Text(
                                    text = stringResource(id = R.string.show_episodes_list),
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CharacterHeader(
    imageUrl: String,
    name: String,
    status: String,
    gender: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .padding(top = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .size(150.dp)
                .border(
                    width = 4.dp,
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(8.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(imageUrl)
                    .crossfade(false)
                    .build(),
                contentDescription = stringResource(R.string.character_picture),
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = stringResource(id = R.string.name),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = name,
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(id = R.string.genre),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = gender,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.SemiBold
            )
        }
    }

}

@Preview
@Composable
fun CharacterInfoScreenPreview() {
    RickVerseTheme {
        CharacterInfoScreen(
            state = CharacterInfoState(
                character = CharacterBO(
                    created = "",
                    episodesId = listOf(),
                    gender = "Male",
                    id = 0,
                    image = "",
                    location = ShortLocationBO("campo", ""),
                    name = "Rick Sanchez",
                    origin = ShortLocationBO("casita", ""),
                    species = "Human",
                    status = "Alive",
                    type = "",
                    url = ""
                )
            ),
            onAction = {}
        )
    }
}