package com.example.myapplication.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil3.compose.AsyncImage
import coil3.compose.rememberAsyncImagePainter
import com.example.myapplication.ui.model.UiViewModel

@Composable
fun MainWindow(
    openDrawer: () -> Unit,
    viewModel: UiViewModel
) {
    Box {
        if (viewModel.isOpenImagePickerDialog) {
            ImageCreatorDialog(viewModel)
        }

        if (viewModel.isFromGallery) {
            Image(
                painter = rememberAsyncImagePainter(model = viewModel.imageUri),
                contentDescription = "Selected Image",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }

        if (viewModel.isFromInternet) {
            AsyncImage(
                modifier = Modifier.fillMaxSize(),
                model = viewModel.imageUri,
                contentScale = ContentScale.Crop,
                contentDescription = null
            )
        }

        if (viewModel.showGrid) {
            Grid()
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