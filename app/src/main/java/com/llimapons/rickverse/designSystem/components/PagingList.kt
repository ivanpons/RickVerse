package com.llimapons.rickverse.designSystem.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
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
fun <T : Any> PagingList(
    items: StateFlow<PagingData<T>>,
    content: @Composable (T) -> Unit,
    modifier: Modifier = Modifier,
    onKey: ((T) -> Any)? = null,
    search: String = "",
    verticalArrangement: Arrangement.Vertical = Arrangement.spacedBy(4.dp)
) {
    val pagingItems: LazyPagingItems<T> = items.collectAsLazyPagingItems()
    val lazyListState = rememberLazyListState()

    LazyColumn(
        modifier = modifier,
        verticalArrangement = verticalArrangement,
        state = lazyListState
    ) {
        items(
            count = pagingItems.itemCount,
            key = {
                if (onKey != null) {
                    pagingItems[it]?.let { element -> onKey(element) } ?: it
                } else {
                    it
                }
            }
        ) { index ->
            val item = pagingItems[index] ?: return@items
            content(item)
        }

        pagingItems.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    item() {
                        PageLoader(
                            modifier = Modifier
                                .fillMaxSize()
                        )
                    }
                }

                loadState.refresh is LoadState.Error -> {
                    val error = pagingItems.loadState.refresh as LoadState.Error
                    item() {
                        ErrorMessage(
                            modifier = Modifier.fillMaxSize(),
                            message = error.error.localizedMessage!!,
                            onClickRetry = { retry() })
                    }
                }

                loadState.append is LoadState.Loading -> {
                    item() { LoadingNextPageItem(modifier = Modifier) }
                }

                loadState.append is LoadState.Error -> {
                    val error = pagingItems.loadState.append as LoadState.Error
                    item() {
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
        lazyListState.scrollToItem(0)
    }
}