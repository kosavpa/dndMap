package com.example.myapplication.ui.components

import android.content.Context
import com.example.myapplication.ui.model.UiViewModel

fun save(viewModel: UiViewModel, context: Context) {
    viewModel.imageUri?.let { uri ->
        context.contentResolver.openInputStream(uri)?.use { `is` ->
            context.openFileOutput(
                uri.toString().substringAfterLast('/'),
                Context.MODE_PRIVATE
            ).use {
                it.write(`is`.readBytes())
            }
        }
    }
}