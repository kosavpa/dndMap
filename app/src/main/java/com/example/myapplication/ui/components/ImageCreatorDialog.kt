package com.example.myapplication.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp

@Composable
fun ImageCreatorDialog(
    openDialog: (Boolean) -> Unit,
    isFromGallery: (Boolean) -> Unit,
    isFromInternet: (Boolean) -> Unit
) {
    AlertDialog(
        onDismissRequest = { openDialog.invoke(false) },
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
                    isFromInternet.invoke(true)

                    openDialog.invoke(false)
                }
            ) {
                Text("По ссылке", fontSize = 22.sp)
            }
        },
        dismissButton = {
            Button(
                {
                    isFromGallery.invoke(true)

                    openDialog.invoke(false)
                }
            ) {
                Text("Из галлереи", fontSize = 22.sp)
            }
        }
    )
}