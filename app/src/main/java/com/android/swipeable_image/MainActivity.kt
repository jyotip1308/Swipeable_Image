package com.android.swipeable_image

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.android.swipeable_image.ui.theme.Swipeable_ImageTheme
import kotlinx.coroutines.launch
import kotlin.math.max
import kotlin.math.min

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
              val scope = rememberCoroutineScope()

                Box(modifier = Modifier.fillMaxSize()){
                    HorizontalPager(
//                        pageCount = animals.size,
                        state = pagerState,
                        key = {animals[it]},
                        pageSize = PageSize.Fill

                    ) { index->
                        Image(painter = painterResource(id = animals[index]),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize())
                    }

                    Box(modifier = Modifier
                        .offset(y = -(16).dp)
                        .fillMaxWidth(0.3f)
                        .clip(RoundedCornerShape(100))
                        .background(Color.White)
                        .padding(8.dp)
                        .align(Alignment.BottomCenter)
                    ){
                        IconButton(
                            onClick = {
                                scope.launch {
                                    pagerState.animateScrollToPage(
                                        pagerState.currentPage - 1
                                    )
                                }

                            },
                            modifier = Modifier.align(Alignment.CenterStart)) {
                            Icon(imageVector = Icons.Default.KeyboardArrowLeft,
                                contentDescription = "Go back"
                            )
                        }
                        IconButton(onClick =
                        {
                            scope.launch {
                                pagerState.animateScrollToPage(
                                    pagerState.currentPage + 1
                                )
                            }
                        },
                            modifier = Modifier.align(Alignment.CenterEnd)) {
                            Icon(imageVector = Icons.Default.KeyboardArrowRight,
                                contentDescription = "Go forward"
                            )
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun ZoomableImage(
    imageResource: Int,
    modifier: Modifier = Modifier
) {
    var isSwipingEnabled by remember { mutableStateOf(false) } // State variable to track swiping state

    var scale by remember {
        mutableStateOf(1f)
    }

    var offset by remember {
        mutableStateOf(Offset.Zero)
    }

    BoxWithConstraints (
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(894f / 893f)
    ){

        val  state = rememberTransformableState { zoomChange, panChange, rotationChange ->

            if (isSwipingEnabled){
                scale = (scale * zoomChange).coerceIn(1f, 5f)


                val extraWidth = (scale - 1) * constraints.maxWidth
                val extraHeight = (scale - 1) * constraints.maxHeight

                val maxX = extraWidth / 2
                val maxY = extraHeight / 2

                offset = Offset(
                    x = (offset.x + panChange.x).coerceIn(-maxX, maxX) ,
                    y = (offset.y + panChange.y).coerceIn(-maxY, maxY)
                )
            }


        }

        Image(
            painter = painterResource(imageResource),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .graphicsLayer {
                    scaleX = scale
                    scaleY = scale
//                                rotationZ = rotation
                    translationX = offset.x
                    translationY = offset.y
                }
                .transformable(state)
        )
    }
}


