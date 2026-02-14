package com.example.myapplication.ui.components

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import com.example.myapplication.ui.model.UiViewModel

@Composable
fun ChipCreateDialog(
    viewModel: UiViewModel
) {
    val painter = rememberAsyncImagePainter(
        viewModel.chipUri
    )

    if (viewModel.isLoadChipUriFromGallary) {
        val galleryLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent(),
            onResult = { uri: Uri? ->
                uri?.let { viewModel.chipUri = uri }
            }
        )

        LaunchedEffect(Unit) {
            galleryLauncher.launch("image/*")
        }
    }

    AlertDialog(
        onDismissRequest = { viewModel.toggleCreateChip() },
        text = {
            Row(Modifier.fillMaxWidth()) {
                Text("Изображение фишки")
                Button(
                    {
                        viewModel.isLoadChipUriFromGallary = true
                    }
                ) { Text("Изображение фишки") }
                Canvas(
                    Modifier
                        .size(viewModel.cellSize.dp)
                        .clip(CircleShape)
                ) {
                    with(painter) {
                        draw(size)
                    }
                }
            }
        },
        confirmButton = {
            Button(
                {
                    viewModel.toggleCreateChip()}
            ) {}
        },
        dismissButton = {
            Button(
                {
                    viewModel.toggleCreateChip()}
            ) {}
        }
    )
}