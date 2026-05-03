package com.example.myapplication.ui.selectItem.model

import android.net.Uri
import androidx.compose.ui.geometry.Offset


class Chip(val name: String, val uri: Uri, var offset: Offset = Offset.Zero) {
    fun copy(): Chip {
        return Chip(name, uri, offset)
    }
}