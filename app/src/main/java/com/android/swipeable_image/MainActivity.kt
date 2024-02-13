package com.android.swipeable_image

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.android.swipeable_image.ui.theme.Swipeable_ImageTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalFoundationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val animals = listOf(
            R.drawable.cattt,
            R.drawable.barbie,
            R.drawable.roman,
            R.drawable.images_roman
        )
        setContent {
            Swipeable_ImageTheme {
              val pagerState = rememberPagerState{ animals.size }
                Box(modifier = Modifier.fillMaxSize()){
                    HorizontalPager(
//                        pageCount = animals.size,
                        state = pagerState,
                        key = {animals[it]}
                    ) { index->
                        Image(
                            painter = painterResource(id = animals[index]) ,
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize())

                    }
                }
            }
        }
    }
}


