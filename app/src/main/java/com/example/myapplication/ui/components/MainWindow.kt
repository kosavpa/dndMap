package com.example.myapplication.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Slider
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.rememberAsyncImagePainter
import com.example.myapplication.ui.model.UiViewModel

@Composable
fun MainWindow(
    openDrawer: () -> Unit,
    viewModel: UiViewModel
) {
    var offsetX by remember { mutableFloatStateOf(0f) }
    var offsetY by remember { mutableFloatStateOf(0f) }

    val transformState = rememberTransformableState { _, panChange, _ ->
        offsetX += panChange.x
        offsetY += panChange.y
    }

    Box {
        if (viewModel.isOpenImagePickerDialog) {
            ImageCreatorDialog(viewModel)
        }

        if (viewModel.isFromGallery) {
            Image(
                painter = rememberAsyncImagePainter(model = viewModel.imageUri),
                contentDescription = "Selected Image",
                modifier = Modifier
                    .fillMaxSize()
                    .transformable(transformState)
                    .graphicsLayer(scaleX = viewModel.scale, scaleY = viewModel.scale, translationX = offsetX, translationY = offsetY),
                contentScale = ContentScale.Crop
            )
        }

        if (viewModel.isFromInternet) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxSize()
                    .transformable(transformState)
                    .graphicsLayer(scaleX = viewModel.scale, scaleY = viewModel.scale, translationX = offsetX, translationY = offsetY),
                model = viewModel.imageUri,
                contentScale = ContentScale.Crop,
                contentDescription = null
            )
        }

        Slider(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(16.dp),
            value = 0f,
            onValueChange = { viewModel.scale = it },
            steps = 20,
            valueRange = 0f..10f
        )

        if (viewModel.showGrid) {
            Grid(transformState, viewModel)
        }

        IconButton(
            onClick = {
                openDrawer.invoke()
            },
            content = {
                Icon(Icons.Filled.Menu, "")
            }
        )
    }
}