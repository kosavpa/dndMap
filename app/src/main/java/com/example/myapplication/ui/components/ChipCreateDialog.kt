package com.example.myapplication.ui.components

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import coil3.compose.rememberAsyncImagePainter
import com.example.myapplication.ui.model.UiViewModel

@Composable
fun ChipCreateDialog(
    viewModel: UiViewModel
) {
    val painter = rememberAsyncImagePainter(
        model = viewModel.chipUri,
        onError = { result ->
            // result.throwable содержит причину ошибки
            android.util.Log.e("CoilError", "Ошибка загрузки: $result")
            android.util.Log.e("CoilError", "URI: ${viewModel.chipUri}")
        }
    )

    if (viewModel.isLoadChipUriFromGallery) {
        val galleryLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent(),
            onResult = { uri: Uri? ->
                uri?.let { viewModel.setUriChip(uri) }
            }
        )

        LaunchedEffect(Unit) {
            galleryLauncher.launch("image/*")
        }
    }

    if (viewModel.isLoadChipUriFromUri) {
        var tmpInternetUri by remember { mutableStateOf("") }

        AlertDialog(
            onDismissRequest = { viewModel.cancelPickImage() },
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
                        viewModel.setUriChip(tmpInternetUri.takeIf { it.isNotBlank() }?.toUri())
                    }
                ) {
                    Text("Ок", fontSize = 22.sp)
                }

            }
        )
    }

    AlertDialog(
        onDismissRequest = { viewModel.toggleCreateChip() },
        text = {
            Row(Modifier.fillMaxWidth()) {
                Column(
                    Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text("Изображение фишки", fontSize = 22.sp)

                    if (viewModel.chipUri == null) {
                        Row(
                            Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Button(
                                {
                                    viewModel.imageChipFromGallery()
                                }
                            ) { Text("Из галереи", fontSize = 22.sp) }
                            Button(
                                {
                                    viewModel.imageChipFromInternet()
                                }
                            ) { Text("Из интернета", fontSize = 22.sp) }
                        }
                    } else {
                        Canvas(
                            Modifier
                                .size(100.dp)
                                .clip(CircleShape)
                                .background(Color.Red)
                                .border(
                                    BorderStroke(4.dp, Color.Black),
                                    CircleShape
                                )
                        ) {
                            with(painter) {
                                draw(size)
                            }
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(
                {
                    viewModel.toggleCreateChip()
                }
            ) { Text("Ок", fontSize = 22.sp) }
        },
        dismissButton = {
            Button(
                {
                    viewModel.toggleCreateChip()
                }
            ) { Text("Отмена", fontSize = 22.sp) }
        }
    )
}