package com.example.myapplication.ui.main.model

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.lifecycle.ViewModel
import com.example.myapplication.ui.common.model.ImageType
import com.example.myapplication.ui.createItem.model.CreateViewModel
import com.example.myapplication.ui.selectItem.model.SelectItem
import com.example.myapplication.ui.selectItem.model.SelectViewModel
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

    var scaleControllerBoxIsVisible by mutableStateOf(false)

    var gridSizeControllerBoxIsVisible by mutableStateOf(false)

    var showGrid by mutableStateOf(false)

    var imageLoadType: ImageType?
        get() {
            if (selectViewModel.isNeedOpenSelectScreen) {
                return selectViewModel.imageLoadType
            }

            if (createViewModel.isNeedOpenItemCreationScreen) {
                return createViewModel.imageLoadType
            }

            return null
        }
        set(value) {
            if (selectViewModel.isNeedOpenSelectScreen) {
                selectViewModel.imageLoadType = value
            }

            if (createViewModel.isNeedOpenItemCreationScreen) {
                createViewModel.imageLoadType = value
            }
        }

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

    val createViewModel = CreateViewModel()

    var isNeedOpenItemCreationScreen: Boolean
        get() {
            return createViewModel.isNeedOpenItemCreationScreen
        }
        set(value) {
            createViewModel.isNeedOpenItemCreationScreen = value
        }

    var imgLoadUri: Uri?
        get() {
            return createViewModel.imgLoadUri
        }
        set(value) {
            createViewModel.imgLoadUri = value
        }

    fun setLoadUri(uri: Uri) {
        createViewModel.setLoadUri(uri)
    }

    var imageName: String?
        get() {
            return createViewModel.imageName
        }
        set(value) {
            createViewModel.imageName = value
        }

    var isPickImage: Boolean
        get() {
            return createViewModel.isPickImage
        }
        set(value) {
            createViewModel.isPickImage = value
        }

    var isResolveImageSource: Boolean
        get() {
            return createViewModel.isResolveImageSource
        }
        set(value) {
            createViewModel.isResolveImageSource = value
        }

    var isFromGallery: Boolean
        get() {
            return createViewModel.isFromGallery
        }
        set(value) {
            createViewModel.isFromGallery = value
        }

    var isFromInternet: Boolean
        get() {
            return createViewModel.isFromInternet
        }
        set(value) {
            createViewModel.isFromInternet = value
        }

    fun toggleNeedResolveImageDialog() {
        createViewModel.toggleNeedResolveImageDialog()
    }

    fun cancelResolveOrPickImage() {
        createViewModel.cancelResolveOrPickImage()
    }

    fun imageFromInternet() {
        createViewModel.imageFromInternet()
    }

    fun imageFromGallery() {
        createViewModel.imageFromGallery()
    }

    fun startCreateItem(type: ImageType) {
        createViewModel.startCreateItem(type)
    }

    fun finishCreateItem() {
        createViewModel.finishCreateItem()
    }
}