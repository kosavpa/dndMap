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
    var imageName: String? = null
    var isPickImage by mutableStateOf(false)

    var scaleControllerBoxIsVisible by mutableStateOf(false)

    var gridSizeControllerBoxIsVisible by mutableStateOf(false)

    var showGrid by mutableStateOf(false)
        private set

    var isResolveImageSource by mutableStateOf(false)
        private set

    var isFromGallery by mutableStateOf(false)
        private set

    var isFromInternet by mutableStateOf(false)
        private set

    var imageLoadType: ImageType? = null
        private set

    var imgLoadUri by mutableStateOf<Uri?>(null)
        private set

    var selectedMapUri: Uri?
        get() = innerSelectedMapUri
        set(value) {
            innerSelectedMapUri = value

            toggleCreateMap()

            toggleCreateChip()
        }

    private var innerSelectedMapUri by mutableStateOf<Uri?>(null)

    private val _startCellSize = 63f

    private val _endCellSize = 189f

    val cellSizeRange = _startCellSize.._endCellSize

    var cellSize by mutableFloatStateOf(_startCellSize)

    var canvasSize by mutableStateOf(Size.Zero)

    var scale by mutableFloatStateOf(1f)

    var offset by mutableStateOf(Offset.Zero)

    fun toggleShowGrid() {
        showGrid = !showGrid
    }

    var isNeedOpenMapCreationScreen by mutableStateOf(false)
        private set

    var isNeedOpenChipCreationScreen by mutableStateOf(false)
        private set


    fun setLoadUri(imgType: ImageType?, uri: Uri?) {
        imgLoadUri = uri

        imageLoadType = imgType

        clearLoadParams()

        cancelResolveOrPickImage()
    }

    fun clearLoadParams() {
        isFromGallery = false

        isFromInternet = false
    }

    fun toggleNeedResolveImageDialog() {
        isResolveImageSource = !isResolveImageSource
    }

    fun toggleCreateChip() {
        isNeedOpenChipCreationScreen = !isNeedOpenChipCreationScreen

        imageLoadType = null

        imgLoadUri = null

        imageName = null

        cancelResolveOrPickImage()

        clearLoadParams()
    }

    fun toggleCreateMap() {
        isNeedOpenMapCreationScreen = !isNeedOpenMapCreationScreen

        imageLoadType = null

        imgLoadUri = null

        imageName = null

        cancelResolveOrPickImage()

        clearLoadParams()
    }

    fun cancelResolveOrPickImage() {
        isResolveImageSource = false

        isFromGallery = false

        isFromInternet = false

        isPickImage = false
    }

    fun imageFromInternet() {
        isResolveImageSource = false

        isFromGallery = false

        isFromInternet = true

        isPickImage = true
    }

    fun imageFromGallery() {
        isResolveImageSource = false

        isFromInternet = false

        isFromGallery = true

        isPickImage = true
    }

    fun boxSizeControllerIsVisible(): Boolean {
        return scaleControllerBoxIsVisible || gridSizeControllerBoxIsVisible
    }
}