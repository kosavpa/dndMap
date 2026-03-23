package com.example.myapplication.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.model.UiViewModel

@Composable
fun ImageSourceResolver(
    viewModel: UiViewModel
) {
    AlertDialog(
        onDismissRequest = { viewModel.toggleNeedResolveImageDialog() },
        title = { Text("Выберите место загрузки изображения") },
        text = { Text("Откуда вы хотите загрузить изображение?") },
        confirmButton = {
            Button(
                {
                    viewModel.imageFromInternet()
                }
            ) {
                Text("По ссылке", fontSize = 22.sp)
            }
        },
        dismissButton = {
            Button(
                {
                    viewModel.imageFromGallery()
                }
            ) {
                Text("Из галереи", fontSize = 22.sp)
            }
        }
    )
}