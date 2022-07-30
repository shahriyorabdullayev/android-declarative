package com.shahriyor.android_declarative.item

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.shahriyor.android_declarative.model.TVShow
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun ItemTvShow(onItemClick: ((TVShow) -> Unit)?, tvShow: TVShow) {

    Column(
        modifier = Modifier
            .padding(5.dp)
            .background(Color.Black),
        horizontalAlignment = Alignment.Start
    ) {
        Box(modifier = Modifier
            .height(250.dp)
            .clickable {
                if (onItemClick != null)
                    onItemClick(tvShow)
            }
        ) {
            GlideImage(imageModel = tvShow.image_thumbnail_path!!,
                contentScale = ContentScale.FillBounds)
        }
        Spacer(modifier = Modifier.height(5.dp))
        Text(text = tvShow.name, color = Color.White)
        Text(text = tvShow.network!!, color = Color.White)
        Spacer(modifier = Modifier.height(5.dp))

    }
}