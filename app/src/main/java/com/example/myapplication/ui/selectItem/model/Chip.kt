package com.example.myapplication.ui.selectItem.model

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.Dp


class Chip(
    var id: String,
    var name: String,
    var uri: Uri,
    baseSize: Dp,
    private val scaleProvider: () -> Float,
    private val gridSizeProvider: () -> Dp
) {

    var baseSize by mutableStateOf(baseSize)

    val size: Dp
        get() = ((baseSize + (gridSizeProvider() - baseSize)) * scaleProvider())

    var hp by mutableIntStateOf(0)

    var offset by mutableStateOf(Offset.Zero)
}