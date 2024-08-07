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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.llimapons.rickverse.designSystem.components.BottomBarItem
import com.llimapons.rickverse.designSystem.components.RickVerseBottomBar

@Composable
fun MainScreenRoot(
    navController: NavHostController
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
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ){
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
fun CharacterNavHost(){
    val characterNavHostController = rememberNavController()
    NavHost(navController = characterNavHostController, startDestination = "all_characters"){
        composable("all_characters"){
            Box(modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center){
                Text(text = "all_characters")
            }
        }
        composable("character_details"){

        }
    }
}

@Composable
fun SearchNavHost(){
    val searchNavHostController = rememberNavController()
    NavHost(navController = searchNavHostController, startDestination = "search"){
        composable("search"){
            Box(modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center){
                Text(text = "search")
            }
        }
        composable("character_details"){

        }
    }
}

@Composable
fun LocationNavHost(){
    val locationNavHostController = rememberNavController()
    NavHost(navController = locationNavHostController, startDestination = "all_locations"){
        composable("all_locations"){
            Box(modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center){
                Text(text = "all_locations")
            }
        }
        composable("location_details"){

        }
    }
}

@Composable
fun EpisodeNavHost(){
    val episodeNavHostController = rememberNavController()
    NavHost(navController = episodeNavHostController, startDestination = "all_episodes"){
        composable("all_episodes"){
            Box(modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center){
                Text(text = "all_episodes")
            }
        }
        composable("episode_details"){

        }
    }
}