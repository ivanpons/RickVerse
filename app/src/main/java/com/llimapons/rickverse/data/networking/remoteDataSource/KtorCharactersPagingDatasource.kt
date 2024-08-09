package com.llimapons.rickverse.data.networking.remoteDataSource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.llimapons.rickverse.data.networking.get
import com.llimapons.rickverse.data.networking.model.CharacterDto
import com.llimapons.rickverse.data.networking.model.PageDto
import com.llimapons.rickverse.domain.util.Result
import io.ktor.client.HttpClient
import java.io.IOException
import javax.inject.Inject

class KtorCharactersPagingDatasource @Inject constructor(
    private val httpClient: HttpClient
): PagingSource<Int, CharacterDto>() {

    override fun getRefreshKey(state: PagingState<Int, CharacterDto>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharacterDto> {
        try {
            val currentLoadingPageKey = params.key ?: 1
            val response = httpClient.get<PageDto<CharacterDto>>(
                route = "/api/character",
                queryParameters = mapOf(
                    "page" to (currentLoadingPageKey).toString()
                )
            )
            when(response){
                is Result.Error -> {
                    return LoadResult.Error(Exception(response.error.name))
                }
                is Result.Success -> {
                    val page = response.data
                    val prevKey = if (page.info?.prev == null) null else currentLoadingPageKey - 1
                    val nextKey = if (page.info?.next == null) null else currentLoadingPageKey + 1

                    return LoadResult.Page(
                    data = page.results ?: emptyList(),
                    prevKey = prevKey,
                    nextKey = nextKey
                    )
                }
            }

        } catch (e: IOException) {
            // IOException for network failures.
            return LoadResult.Error(e)
        }

    }
}