package com.shahriyor.android_declarative.activity.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.shahriyor.android_declarative.item.ItemTvShow
import com.shahriyor.android_declarative.model.TVShow
import com.shahriyor.android_declarative.viewmodel.MainViewModel
import com.shahriyor.android_declarative.viewmodel.MainViewModel.Companion.isLoading
import kotlinx.coroutines.flow.Flow

@Composable
fun MainScreen(navController: NavController) {

    val viewModel = hiltViewModel<MainViewModel>()
    val isLoading by isLoading.observeAsState(false)
    val tvShows = viewModel.movie

    MainScreenContent(onItemClick = {
        //Savae TvShow locally
        viewModel.insertTVShowToDB(it)
        //Open Details Screen
        navController.navigate("details" + "/${it.id}" + "/${it.name}" + "/${it.network}")

    }, isLoading = isLoading, tvShow = tvShows)

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainScreenContent(onItemClick: ((TVShow) -> Unit)?, isLoading: Boolean, tvShow: Flow<PagingData<TVShow>>) {
    Scaffold(
        backgroundColor = Color.Black,
        topBar = {
            TopAppBar(
                backgroundColor = Color.Black,
                title = { Text(text = "TV Show", color = Color.White) },
            )
        }
    ) {
        if (isLoading) {
            Column(modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally) {
                CircularProgressIndicator(color = Color.White)
            }
        } else {

            val tvShowList: LazyPagingItems<TVShow> = tvShow.collectAsLazyPagingItems()

            LazyVerticalGrid(cells = GridCells.Fixed(2), modifier = Modifier.padding(5.dp)) {
                items(tvShowList.itemCount){
                    val tvShow = tvShowList[it]
                    ItemTvShow(onItemClick, tvShow!!)
                }
            }
        }
    }

}