package com.llimapons.rickverse.designSystem.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.llimapons.rickverse.domain.model.CharacterBO
import com.llimapons.rickverse.presentation.util.ErrorMessage
import com.llimapons.rickverse.presentation.util.LoadingNextPageItem
import com.llimapons.rickverse.presentation.util.PageLoader
import kotlinx.coroutines.flow.StateFlow

@Composable
fun CharacterGrid(
    characters: StateFlow<PagingData<CharacterBO>>,
    onCharacterClicked: (CharacterBO) -> Unit
) {
    val charactersPagingItems: LazyPagingItems<CharacterBO> = characters.collectAsLazyPagingItems()
    val lazyGridState = rememberLazyGridState()

    LazyVerticalGrid(
        modifier = Modifier.padding(vertical = 16.dp),
        columns = GridCells.FixedSize(size = 160.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        state = lazyGridState
    ) {
       items(charactersPagingItems.itemCount){ index ->
           val character = charactersPagingItems[index] ?: return@items
           CharacterItem(
               character = character,
               onItemClick = {
                   onCharacterClicked(character)
               },
               size = DpSize(120.dp, 120.dp)
           )
       }

        charactersPagingItems.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    item(span = { GridItemSpan(maxLineSpan) }) { PageLoader(
                        modifier = Modifier
                            .fillMaxSize()
                    ) }
                }

                loadState.refresh is LoadState.Error -> {
                    val error = charactersPagingItems.loadState.refresh as LoadState.Error
                    item(span = { GridItemSpan(maxLineSpan) }) {
                        ErrorMessage(
                            modifier = Modifier.fillMaxSize(),
                            message = error.error.localizedMessage!!,
                            onClickRetry = { retry() })
                    }
                }

                loadState.append is LoadState.Loading -> {
                    item(span = { GridItemSpan(maxLineSpan) }) { LoadingNextPageItem(modifier = Modifier) }
                }

                loadState.append is LoadState.Error -> {
                    val error = charactersPagingItems.loadState.append as LoadState.Error
                    item(span = { GridItemSpan(maxLineSpan) }) {
                        ErrorMessage(
                            modifier = Modifier,
                            message = error.error.localizedMessage!!,
                            onClickRetry = { retry() })
                    }
                }
            }
        }
    }

    LaunchedEffect(charactersPagingItems) {
        snapshotFlow { charactersPagingItems.itemCount }.collect { count ->
            if (count in 1..30) {
                lazyGridState.scrollToItem(0)
            }
        }
    }
}