package com.example.myapplication.ui.components

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil3.compose.AsyncImage
import coil3.compose.rememberAsyncImagePainter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun MainWindow(
    drawerState: DrawerState,
    scope: CoroutineScope,
    showGrid: Boolean,
    openImageDialog: Boolean,
    openImageDialogChanger: (Boolean) -> Unit,
    galleryUri: Uri?,
    internetUri: String?,
    isFromGallery: (Boolean) -> Unit,
    isFromInternet: (Boolean) -> Unit
) {
    Box {
        if (openImageDialog) {
            ImageCreatorDialog(
                openImageDialogChanger,
                isFromGallery,
                isFromInternet
            )
        }

        galleryUri?.let { uri ->
            Image(
                painter = rememberAsyncImagePainter(model = uri),
                contentDescription = "Selected Image",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }

        internetUri?.let { uri ->
            AsyncImage(
                modifier = Modifier.fillMaxSize(),
                model = uri,
                contentScale = ContentScale.Crop,
                contentDescription = null
            )
        }

        if (showGrid) Grid()

        IconButton(
            onClick = {
                scope.launch { drawerState.open() }
            },
            content = {
                Icon(Icons.Filled.Menu, "")
            }
        )
    }
}