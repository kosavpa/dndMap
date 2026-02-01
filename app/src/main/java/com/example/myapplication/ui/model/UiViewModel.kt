package com.example.myapplication.ui.model

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.lifecycle.ViewModel

class UiViewModel : ViewModel() {
    var showGrid by mutableStateOf(false)
        private set

    var isOpenImagePickerDialog by mutableStateOf(false)
        private set

    var isFromGallery by mutableStateOf(false)
        private set

    var isFromInternet by mutableStateOf(false)
        private set

    var imageUri by mutableStateOf<Uri?>(null)
        private set

    private val _startCellSize = 63f

    private val _endCellSize = 189f

    val cellSizeRange = _startCellSize.._endCellSize

    var cellSize by mutableFloatStateOf(_startCellSize)

    var canvasSize by mutableStateOf(Size.Zero)

    var scale by mutableFloatStateOf(1f)

    var offset by mutableStateOf(Offset.Zero)

    fun setUri(uri: Uri?) {
        imageUri = uri

        isFromGallery = false

        isFromInternet = false
    }

    fun toggleShowGrid() {
        showGrid = !showGrid
    }

    fun needOpenDialog() {
        isOpenImagePickerDialog = true
    }

    fun imageFromInternet() {
        isOpenImagePickerDialog = false

        isFromGallery = false

        isFromInternet = true
    }

    fun imageFromGallery() {
        isOpenImagePickerDialog = false

        isFromInternet = false

        isFromGallery = true
    }
}