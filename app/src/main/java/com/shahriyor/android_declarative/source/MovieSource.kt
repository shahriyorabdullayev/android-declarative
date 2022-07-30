package com.shahriyor.android_declarative.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.shahriyor.android_declarative.model.TVShow
import com.shahriyor.android_declarative.repository.TVShowRepository
import com.shahriyor.android_declarative.viewmodel.MainViewModel.Companion.isLoading
import retrofit2.HttpException
import java.io.IOException

class MovieSource(
    private val repository: TVShowRepository,
) : PagingSource<Int, TVShow>() {
    override fun getRefreshKey(state: PagingState<Int, TVShow>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TVShow> {
        return try {
            val nextPage = params.key ?: 1
            val movieList = repository.apiTVShowPopular(page = nextPage)
            LoadResult.Page(
                data = movieList.body()!!.tv_shows,
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = if (movieList.body()!!.tv_shows.isEmpty()) null else movieList.body()!!.page + 1
            )

        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }
}