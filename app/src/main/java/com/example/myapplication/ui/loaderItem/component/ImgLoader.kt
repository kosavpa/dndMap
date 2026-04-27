package com.example.myapplication.ui.loaderItem.component

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import com.example.myapplication.ui.common.model.UiViewModel

@Composable
fun ImgLoader(viewModel: UiViewModel) {
    if (viewModel.isFromGallery) {
        val galleryLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent(),
            onResult = { uri: Uri? ->
                uri?.let {
                    handleUri(viewModel, uri)
                }
            }
        )

        LaunchedEffect(Unit) {
            galleryLauncher.launch("image/*")
        }
    }

    if (viewModel.isFromInternet) {
        var tmpInternetUri by remember { mutableStateOf("") }

        AlertDialog(
            onDismissRequest = { viewModel.cancelResolveOrPickImage() },
            title = { Text(text = "Введите ссылку на изображение") },
            text = {
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = tmpInternetUri,
                    onValueChange = { tmpInternetUri = it }
                )
            },
            confirmButton = {
                Button(
                    {
                        if (tmpInternetUri.isNotBlank()) {
                            handleUri(viewModel, tmpInternetUri.toUri())
                        }
                    }
                ) {
                    Text("Ok", fontSize = 22.sp)
                }
            }
        )
    }
}

fun handleUri(viewModel: UiViewModel, uri: Uri) {
    viewModel.setLoadUri(uri)
}
