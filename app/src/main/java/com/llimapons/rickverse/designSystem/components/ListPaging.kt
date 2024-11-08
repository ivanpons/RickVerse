package com.llimapons.rickverse.designSystem.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.llimapons.rickverse.domain.model.EpisodeBO
import com.llimapons.rickverse.domain.model.LocationBO
import com.llimapons.rickverse.presentation.util.ErrorMessage
import com.llimapons.rickverse.presentation.util.LoadingNextPageItem
import com.llimapons.rickverse.presentation.util.PageLoader
import kotlinx.coroutines.flow.StateFlow

@Composable
fun <T : Any> ListTextPaging(
    items: StateFlow<PagingData<T>>,
    modifier: Modifier = Modifier,
    onItemClicked: (T) -> Unit = {}
) {
    val pagingItemsPagingItems: LazyPagingItems<T> = items.collectAsLazyPagingItems()
    val lazyListState = rememberLazyListState()

    LazyColumn(
        modifier = modifier.padding(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        state = lazyListState
    ) {
        items(pagingItemsPagingItems.itemCount) { index ->
            val item = pagingItemsPagingItems[index] ?: return@items
            val text = when (item) {
                is LocationBO -> item.name
                is EpisodeBO -> item.episode + " - " + item.name
                else -> index.toString()
            }
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .clickable {
                        onItemClicked(item)
                    }
            ) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = text)
                Spacer(modifier = Modifier.height(8.dp))
                HorizontalDivider()
            }
        }

        pagingItemsPagingItems.apply {
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
                    val error = pagingItemsPagingItems.loadState.refresh as LoadState.Error
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
                    val error = pagingItemsPagingItems.loadState.append as LoadState.Error
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

    LaunchedEffect(pagingItemsPagingItems) {
        snapshotFlow { pagingItemsPagingItems.itemCount }.collect { count ->
            if (count in 1..30) {
                lazyListState.scrollToItem(0)
            }
        }
    }
}