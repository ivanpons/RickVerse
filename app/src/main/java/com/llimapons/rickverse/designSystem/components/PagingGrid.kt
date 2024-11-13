package com.llimapons.rickverse.designSystem.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.llimapons.rickverse.presentation.util.ErrorMessage
import com.llimapons.rickverse.presentation.util.LoadingNextPageItem
import com.llimapons.rickverse.presentation.util.PageLoader
import kotlinx.coroutines.flow.StateFlow
import timber.log.Timber

@Composable
fun <T : Any> PagingGrid(
    elements: StateFlow<PagingData<T>>,
    content: @Composable (T) -> Unit,
    modifier: Modifier = Modifier,
    onKey: ((T) -> Any)? = null,
    columns: GridCells = GridCells.FixedSize(size = 160.dp),
    verticalArrangement: Arrangement.Vertical = Arrangement.spacedBy(8.dp),
    horizontalArrangement: Arrangement.Horizontal = Arrangement.SpaceAround
) {
    val pagingItems: LazyPagingItems<T> = elements.collectAsLazyPagingItems()
    val lazyGridState = rememberLazyGridState()

    LazyVerticalGrid(
        modifier = modifier,
        columns = columns,
        verticalArrangement = verticalArrangement,
        horizontalArrangement = horizontalArrangement,
        state = lazyGridState,
    ) {
        items(
            count = pagingItems.itemCount,
            key = {
                if (onKey != null) {
                    pagingItems[it]?.let { element -> onKey(element) } ?: it
                } else {
                    it
                }
            }) { index ->
            val element = pagingItems[index] ?: return@items
            content(element)
        }

        pagingItems.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    item(span = { GridItemSpan(maxLineSpan) }) {
                        PageLoader(
                            modifier = Modifier
                                .fillMaxSize()
                        )
                    }
                }

                loadState.refresh is LoadState.Error -> {
                    val error = pagingItems.loadState.refresh as LoadState.Error
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
                    val error = pagingItems.loadState.append as LoadState.Error
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

    LaunchedEffect(key1 = pagingItems.loadState.refresh) {
        when(pagingItems.loadState.refresh){
            is LoadState.NotLoading -> {
                lazyGridState.scrollToItem(0, scrollOffset = 0)
            }
            else -> {
                //Do nothing
            }
        }
    }

}