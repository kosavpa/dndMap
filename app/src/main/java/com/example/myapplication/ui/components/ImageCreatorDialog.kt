package com.example.myapplication.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.model.UiViewModel

@Composable
fun ImageCreatorDialog(
    viewModel: UiViewModel
) {
    AlertDialog(
        onDismissRequest = { viewModel.needOpenDialog() },
        title = { Text(text = "Откуда вы хотите загрузить изображение?") },
        text = {
            Text(
                """
                    Выбиреите место загрузки изображения,
                    помните что изображение должно быть подходящих размеров,
                    иначе оно будет выглядить не качественно?
                """.trimIndent()
            )
        },
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
                Text("Из галлереи", fontSize = 22.sp)
            }
        }
    )
}