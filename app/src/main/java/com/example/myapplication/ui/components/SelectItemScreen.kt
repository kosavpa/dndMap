package com.example.myapplication.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import coil3.compose.rememberAsyncImagePainter
import com.example.myapplication.ui.model.ImageType
import com.example.myapplication.ui.model.UiViewModel
import com.example.myapplication.ui.util.loadSelectFiles

@Composable
fun SelectItemScreen(
    viewModel: UiViewModel
) {
    val current = LocalContext.current

    val listState = rememberLazyListState()

    var trackHeightPx by remember { mutableIntStateOf(0) }

    val scrollProgress by remember {
        derivedStateOf {
            val firstVisibleItem = listState.firstVisibleItemIndex

            val totalItems = listState.layoutInfo.totalItemsCount

            if (totalItems > 1) {
                (firstVisibleItem.toFloat() / (totalItems - 1)).coerceIn(0f, 1f)
            } else {
                0f
            }
        }
    }

    val shouldLoadMore = remember {
        derivedStateOf {
            val lastVisibleItem = listState.layoutInfo
                .visibleItemsInfo
                .lastOrNull()

            lastVisibleItem?.index != 0
                    && lastVisibleItem?.index == listState.layoutInfo.totalItemsCount - 1
        }
    }

    LaunchedEffect(shouldLoadMore.value) {
        if (shouldLoadMore.value || viewModel.loadedSelectItems.isEmpty()) {
            loadSelectFiles(current, viewModel)
        }
    }

    val itemToSelectedMap = viewModel.loadedSelectItems
        .associateWith { remember { mutableStateOf(false) } }

    AlertDialog(
        modifier = Modifier
            .width(600.dp)
            .height(460.dp),
        properties = DialogProperties(usePlatformDefaultWidth = false),
        onDismissRequest = { viewModel.finishSelectItemsParams() },
        text = {
            Box(modifier = Modifier.fillMaxSize()) {
                LazyColumn(state = listState) {
                    items(viewModel.loadedSelectItems) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    if (itemToSelectedMap[it]!!.value) Color.Red else Color.Transparent,
                                    shape = RoundedCornerShape(20.dp)
                                )
                                .padding(4.dp)
                                .clickable {
                                    if (viewModel.imageLoadType == ImageType.MAP) {
                                        itemToSelectedMap.forEach { (item, state) ->
                                            state.value = item == it
                                        }
                                    } else {
                                        itemToSelectedMap[it]!!.value =
                                            !itemToSelectedMap[it]!!.value
                                    }

                                    if (itemToSelectedMap[it]!!.value) {
                                        viewModel.addSelectItem(it)
                                    } else {
                                        viewModel.removeSelectedItem(it)
                                    }
                                },
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Image(
                                painter = rememberAsyncImagePainter(model = it.uri),
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(100.dp)
                                    .clip(RoundedCornerShape(16.dp))
                            )
                            Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                                Text(
                                    modifier = Modifier.padding(4.dp),
                                    text = it.name,
                                    fontSize = 24.sp
                                )
                            }
                        }
                    }
                }

                Box(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .fillMaxHeight()
                        .width(6.dp)
                        .padding(vertical = 12.dp)
                        .onGloballyPositioned {
                            trackHeightPx = it.size.height
                        }
                ) {
                    val thumbHeightPx = with(LocalDensity.current) { 40.dp.toPx() }

                    val offsetY = ((trackHeightPx - thumbHeightPx) * scrollProgress).toInt()

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp)
                            .offset { IntOffset(x = 0, y = offsetY) }
                            .background(Color.Gray, RoundedCornerShape(3.dp))
                    )
                }
            }
        },
        confirmButton = {
            Button(
                {
                    viewModel.itemsSelected()
                }
            ) { Text("Ок", fontSize = 22.sp) }
        },
        dismissButton = {
            Button(
                {
                    viewModel.finishSelectItemsParams()
                }
            ) { Text("Отмена", fontSize = 22.sp) }
        }
    )
}