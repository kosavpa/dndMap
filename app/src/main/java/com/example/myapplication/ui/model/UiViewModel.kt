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
    var loadedSelectItems by mutableStateOf(mutableListOf<SelectItem>())

    var selectedItems by mutableStateOf(mutableListOf<SelectItem>())

    val lazyLoadedItems = 8

    var isNeedOpenSelectScreen by mutableStateOf(false)

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

            finishCreateMapScreen()

            finishCreateChip()

            finishSelectItemsParams()
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

        cancelResolveOrPickImage()
    }

    fun toggleNeedResolveImageDialog() {
        isResolveImageSource = !isResolveImageSource
    }

    fun startCreateChip() {
        isNeedOpenChipCreationScreen = true

        clearImageParams()
    }

    fun finishCreateChip() {
        isNeedOpenChipCreationScreen = false

        clearImageParams()
    }

    fun startCreateMapScreen() {
        isNeedOpenMapCreationScreen = true

        clearImageParams()
    }

    fun finishCreateMapScreen() {
        isNeedOpenMapCreationScreen = false

        clearImageParams()
    }

    fun clearImageParams() {
        imageLoadType = null

        imgLoadUri = null

        imageName = null

        cancelResolveOrPickImage()
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

    fun startSelectMap() {
        isNeedOpenSelectScreen = true

        imageLoadType = ImageType.MAP
    }

    fun selectMap() {
        selectedMapUri = selectedItems.first().uri
    }

    fun startSelectChip() {
        isNeedOpenSelectScreen = true

        imageLoadType = ImageType.CHIP
    }

    fun finishSelectItemsParams() {
        loadedSelectItems.clear()

        imageLoadType = null

        isNeedOpenSelectScreen = false
    }

    fun addSelectItem(item: SelectItem) {
        if (imageLoadType == ImageType.MAP) {
            selectedItems.add(0, item)
        } else {
            selectedItems.add(item)
        }
    }
}