package com.example.myapplication.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import com.example.myapplication.ui.model.UiViewModel

@Composable
fun ChipCreateDialog(
    viewModel: UiViewModel
) {
    AlertDialog(
        onDismissRequest = { viewModel.toggleCreateChip() },
        text = {

        },
        confirmButton = {
            Button(
                {}
            ) {}
        },
        dismissButton = {
            Button(
                {}
            ) {}
        }
    )
}