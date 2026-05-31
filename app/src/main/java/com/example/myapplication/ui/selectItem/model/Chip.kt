package com.example.myapplication.ui.selectItem.model

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset


class Chip {
    var name: String

    val uri: Uri

    var size by mutableIntStateOf(0)

    var hp by mutableIntStateOf(0)

    var offset by mutableStateOf(Offset.Zero)

    constructor(name: String, uri: Uri, size: Int) {
        this.name = name
        this.uri = uri
        this.size = size
        this.hp = 0
    }
}