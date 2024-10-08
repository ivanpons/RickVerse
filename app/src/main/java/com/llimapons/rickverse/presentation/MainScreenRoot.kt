package com.llimapons.rickverse.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.llimapons.rickverse.designSystem.components.BottomBarItem
import com.llimapons.rickverse.designSystem.components.RickVerseBottomBar
import com.llimapons.rickverse.presentation.characterInfo.CharacterInfoScreenRoot
import com.llimapons.rickverse.presentation.characters.CharactersScreenRoot
import com.llimapons.rickverse.presentation.episodeInfo.EpisodeInfoScreenRoot
import com.llimapons.rickverse.presentation.episodes.EpisodesScreenRoot
import com.llimapons.rickverse.presentation.locationInfo.LocationInfoScreenRoot
import com.llimapons.rickverse.presentation.locations.LocationsScreenRoot
import com.llimapons.rickverse.presentation.search.SearchScreenRoot
import kotlinx.serialization.Serializable

@Composable
fun MainScreenRoot(
    navController: NavHostController,
) {
    val nacBackStackEntry = navController.currentBackStackEntryAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            RickVerseBottomBar(
                items = rickVerseBarItems,
                currentRoute = nacBackStackEntry,
                onItemClick = {
                    navController.navigate(it.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = innerPadding.calculateBottomPadding()),
            contentAlignment = Alignment.Center
        ) {
            NavHost(navController = navController, startDestination = "characters") {
                composable("characters") {
                    CharacterNavHost()
                }
                composable("search") {
                    SearchNavHost()
                }
                composable("locations") {
                    LocationNavHost()
                }
                composable("episodes") {
                    EpisodeNavHost()
                }
            }

        }
    }
}

private val rickVerseBarItems = listOf(
    BottomBarItem(
        title = "Characters",
        iconSelected = Icons.Default.Person,
        iconUnselected = Icons.Outlined.Person,
        route = "characters"
    ),
    BottomBarItem(
        title = "Search",
        iconSelected = Icons.Default.Search,
        iconUnselected = Icons.Outlined.Search,
        route = "search"
    ),
    BottomBarItem(
        title = "Locations",
        iconSelected = Icons.Default.LocationOn,
        iconUnselected = Icons.Outlined.LocationOn,
        route = "locations"
    ),
    BottomBarItem(
        title = "Episodes",
        iconSelected = Icons.Default.CalendarToday,
        iconUnselected = Icons.Outlined.CalendarToday,
        route = "episodes"
    )
)

@Composable
fun CharacterNavHost() {
    val characterNavHostController = rememberNavController()
    NavHost(navController = characterNavHostController, startDestination = "all_characters") {
        composable("all_characters") {
            CharactersScreenRoot(
                onCharacterClicked = { character ->
                    characterNavHostController.navigate(
                        CharacterInfo(
                            characterId = character.id
                        )
                    )
                }
            )
        }
        locationInfoNavigation(characterNavHostController)
        characterInfoNavigation(characterNavHostController)
        episodeInfoNavigation(characterNavHostController)
    }
}

@Composable
fun SearchNavHost() {
    val searchNavHostController = rememberNavController()
    NavHost(navController = searchNavHostController, startDestination = "search") {
        composable("search") {
            SearchScreenRoot(
                onCharacterClicked = { character ->
                    searchNavHostController.navigate(
                        CharacterInfo(
                            characterId = character.id
                        )
                    )
                }
            )
        }
        locationInfoNavigation(searchNavHostController)
        characterInfoNavigation(searchNavHostController)
        episodeInfoNavigation(searchNavHostController)
    }
}

@Composable
fun LocationNavHost() {
    val locationNavHostController = rememberNavController()
    NavHost(navController = locationNavHostController, startDestination = "all_locations") {
        composable("all_locations") {
            LocationsScreenRoot(
                onLocationClicked = { location ->
                    locationNavHostController.navigate(
                        LocationInfo(
                            locationId = location.id
                        )
                    )
                }
            )
        }
        locationInfoNavigation(locationNavHostController)
        characterInfoNavigation(locationNavHostController)
        episodeInfoNavigation(locationNavHostController)
    }
}

@Composable
fun EpisodeNavHost() {
    val episodeNavHostController = rememberNavController()
    NavHost(navController = episodeNavHostController, startDestination = "all_episodes") {
        composable("all_episodes") {
            EpisodesScreenRoot(
                onEpisodeClicked = {
                    episodeNavHostController.navigate(
                        EpisodeInfo(
                            episodeId = it.id
                        )
                    )
                }
            )
        }

        locationInfoNavigation(episodeNavHostController)
        characterInfoNavigation(episodeNavHostController)
        episodeInfoNavigation(episodeNavHostController)
    }
}

private fun NavGraphBuilder.episodeInfoNavigation(characterNavHostController: NavHostController) {
    composable<EpisodeInfo> {
        val args = it.toRoute<EpisodeInfo>()
        EpisodeInfoScreenRoot(
            episodeId = args.episodeId,
            onBackClicked = {
                characterNavHostController.popBackStack()
            },
            onCharacterClicked = { character ->
                characterNavHostController.navigate(
                    CharacterInfo(
                        characterId = character.id
                    )
                )
            }
        )
    }
}

private fun NavGraphBuilder.characterInfoNavigation(characterNavHostController: NavHostController) {
    composable<CharacterInfo> {
        val args = it.toRoute<CharacterInfo>()
        CharacterInfoScreenRoot(
            characterId = args.characterId,
            onBackClicked = {
                characterNavHostController.popBackStack()
            },
            onLocationClicked = { locationId ->
                try {
                    val locationInt = locationId.toInt()
                    characterNavHostController.navigate(
                        LocationInfo(
                            locationId = locationInt
                        )
                    )
                } catch (t: Throwable) {
                }
            },
            onEpisodeClicked = { episodeId ->
                try {
                    val episodeInt = episodeId.toInt()
                    characterNavHostController.navigate(
                        EpisodeInfo(
                            episodeId = episodeInt
                        )
                    )
                } catch (t: Throwable) {
                }
            }
        )
    }
}

private fun NavGraphBuilder.locationInfoNavigation(characterNavHostController: NavHostController) {
    composable<LocationInfo> {
        val args = it.toRoute<LocationInfo>()
        LocationInfoScreenRoot(
            locationId = args.locationId,
            onBackClicked = {
                characterNavHostController.popBackStack()
            },
            onCharacterClicked = { character ->
                characterNavHostController.navigate(
                    CharacterInfo(
                        characterId = character.id
                    )
                )
            }
        )
    }
}


@Serializable
data class CharacterInfo(
    val characterId: Int,
)

@Serializable
data class LocationInfo(
    val locationId: Int,
)

@Serializable
data class EpisodeInfo(
    val episodeId: Int,
)