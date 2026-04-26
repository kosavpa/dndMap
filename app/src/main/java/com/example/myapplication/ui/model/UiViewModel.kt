package com.example.myapplication.ui.model

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.lifecycle.ViewModel
import okhttp3.internal.immutableListOf
import okhttp3.internal.toImmutableList

class UiViewModel : ViewModel() {

    val selectViewModel = SelectViewModel()

    val lazyLoadedItems: Int
        get() {
            return selectViewModel.lazyLoadedItems
        }

    var loadedSelectItems: List<SelectItem>
        get() {
            return selectViewModel.loadedSelectItems
        }
        set(value) {
            selectViewModel.loadedSelectItems = value
        }

    val isNeedOpenSelectScreen: Boolean
        get() {
            return selectViewModel.isNeedOpenSelectScreen
        }

    var selectedItems by mutableStateOf(immutableListOf<SelectItem>())


    var imageName: String? = null

    var isPickImage by mutableStateOf(false)

    var scaleControllerBoxIsVisible by mutableStateOf(false)

    var gridSizeControllerBoxIsVisible by mutableStateOf(false)

    var showGrid by mutableStateOf(false)

    var isResolveImageSource by mutableStateOf(false)

    var isFromGallery by mutableStateOf(false)

    var isFromInternet by mutableStateOf(false)

    private var imageLoadTypeInner: ImageType? = null

    var imageLoadType: ImageType?
        get() {
            if (selectViewModel.isNeedOpenSelectScreen) {
                return selectViewModel.imageLoadType
            }

            return imageLoadTypeInner
        }
        set(value) {
            if (selectViewModel.isNeedOpenSelectScreen) {
                selectViewModel.imageLoadType = value
            } else {
                imageLoadTypeInner = value
            }
        }


    var imgLoadUri by mutableStateOf<Uri?>(null)

    var selectedMapUri by mutableStateOf<Uri?>(null)

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

    var isNeedOpenChipCreationScreen by mutableStateOf(false)


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
        selectViewModel.startSelectMap()
    }

    fun itemsSelected() {
        if (selectViewModel.imageLoadType == ImageType.MAP) {
            selectedMapUri = selectViewModel.itemsSelected().first().uri

            selectedItems = immutableListOf()
        } else {
            selectedItems = selectViewModel.itemsSelected().toImmutableList()
        }

        finishSelectItemsParams()
    }

    fun startSelectChip() {
        selectViewModel.startSelectChip()
    }

    fun finishSelectItemsParams() {
        selectViewModel.finishSelectItemsParams()
    }

    fun addSelectItem(item: SelectItem) {
        selectViewModel.addSelectItem(item)
    }

    fun removeSelectedItem(item: SelectItem) {
        selectViewModel.removeSelectedItem(item)
    }
}