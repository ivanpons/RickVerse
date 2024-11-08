package com.llimapons.rickverse.designSystem.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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

@Composable
fun <T : Any> PagingGrid(
    elements: StateFlow<PagingData<T>>,
    content: @Composable (T) -> Unit,
    modifier: Modifier = Modifier,
    onKey: ((T) -> Any)? = null,
    columns: GridCells = GridCells.FixedSize(size = 160.dp),
    verticalArrangement: Arrangement.Vertical = Arrangement.spacedBy(8.dp),
    horizontalArrangement: Arrangement.Horizontal = Arrangement.SpaceAround,
    search: String = ""
) {
    val pagingItems: LazyPagingItems<T> = elements.collectAsLazyPagingItems()
    val lazyGridState = rememberLazyGridState()

    LazyVerticalGrid(
        modifier = modifier,
        columns = columns,
        verticalArrangement = verticalArrangement,
        horizontalArrangement = horizontalArrangement,
        state = lazyGridState
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

    LaunchedEffect(search, pagingItems) {
        lazyGridState.scrollToItem(0)
    }
}