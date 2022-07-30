package com.shahriyor.android_declarative.activity

import android.widget.Space
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.shahriyor.android_declarative.viewmodel.DetailsViewModel
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun DetailsScreen(
    navController: NavController,
    showId: String,
    showName: String,
    showType: String,
) {
    val viewModel = hiltViewModel<DetailsViewModel>()
    viewModel.apiTVShowDetails(showId.toInt())

    DetailsScreenContent(viewModel = viewModel,
        showId = showId,
        showName = showName,
        showType = showType)
}


@Composable
fun DetailsScreenContent(
    viewModel: DetailsViewModel,
    showId: String,
    showName: String,
    showType: String,
) {
    val isLoading by viewModel.isLoading.observeAsState(false)
    val tvShowDetails by viewModel.tvShowDetails.observeAsState()

    Scaffold(backgroundColor = Color.Black) {
        if (isLoading) {
            Column(modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally) {
                CircularProgressIndicator(color = Color.White)

            }
        } else {
            Column {
                Box {
                    Box(modifier = Modifier.height(500.dp)) {
                        val imgUrl = "https://static.episodate.com/images/tv-show/full/$showId.jpg"
                        GlideImage(imageModel = imgUrl, contentScale = ContentScale.Crop) {

                        }
                    }

                    Box(modifier = Modifier.height(500.dp)) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Bottom,
                            horizontalAlignment = Alignment.Start
                        ) {
                            Row(modifier = Modifier
                                .fillMaxWidth()
                                .height(70.dp)
                                .background(Color.Black.copy(alpha = 0.6f)),
                                verticalAlignment = Alignment.CenterVertically) {

                                Spacer(modifier = Modifier.width(15.dp))
                                Column {
                                    Text(text = showName, color = Color.White)
                                    Text(text = showType, color = Color.Yellow)
                                }
                            }

                        }
                    }
                }
            }
        }

    }

}