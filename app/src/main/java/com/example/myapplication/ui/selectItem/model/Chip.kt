package com.example.myapplication.ui.selectItem.model

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset


class Chip(
    var name: String,
    val uri: Uri,
    var size: Int,
    var hp: Int = 0
) {
    var offset by mutableStateOf(Offset.Zero)

    fun copy(): Chip {
        val chip = Chip(name, uri, size, hp)

        chip.offset = offset

        return chip
    }
}