package com.example.myapplication.ui.model

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel

class UiViewModel : ViewModel() {

    private var _showGrid by mutableStateOf(false)

    val showGrid: Boolean
        get() = _showGrid

    private var _isOpenImagePickerDialog by mutableStateOf(false)

    val isOpenImagePickerDialog: Boolean
        get() = _isOpenImagePickerDialog

    private var _isFromGallery by mutableStateOf(false)

    val isFromGallery: Boolean
        get() = _isFromGallery

    private var _isFromInternet by mutableStateOf(false)

    val isFromInternet: Boolean
        get() = _isFromInternet

    private var _imageUri by mutableStateOf<Uri?>(null)

    val imageUri: Uri?
        get() = _imageUri

    fun galleryUri(uri: Uri?) {
        _imageUri = uri

        _isFromGallery = true

        _isFromInternet = false
    }

    fun internetUri(uri: String) {
        _imageUri = uri.takeIf { it.isNotBlank() }?.toUri()

        _isFromInternet = true

        _isFromGallery = false
    }

    fun toggleShowGrid() {
        _showGrid = !_showGrid
    }

    fun needOpenDialog() {
        _isOpenImagePickerDialog = true
    }

    fun imageFromInternet() {
        _isOpenImagePickerDialog = false

        _isFromGallery = false

        _isFromInternet = true
    }

    fun imageFromGallery() {
        _isOpenImagePickerDialog = false

        _isFromInternet = false

        _isFromGallery = true
    }
}