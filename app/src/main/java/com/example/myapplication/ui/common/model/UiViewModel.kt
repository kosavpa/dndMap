package com.example.myapplication.ui.common.model

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.lifecycle.ViewModel
import com.example.myapplication.ui.createItem.model.CreateViewModel
import com.example.myapplication.ui.drawer.model.DrawerViewModel
import com.example.myapplication.ui.selectItem.enums.AdditionalIfoName
import com.example.myapplication.ui.selectItem.model.Chip
import com.example.myapplication.ui.selectItem.model.SelectItem
import com.example.myapplication.ui.selectItem.model.SelectViewModel
import okhttp3.internal.immutableListOf
import okhttp3.internal.toImmutableList

class UiViewModel : ViewModel() {

    var drawerViewModel = DrawerViewModel()

    val cellSizeRange: ClosedFloatingPointRange<Float>
        get() {
            return drawerViewModel.cellSizeRange
        }

    var cellSize: Float
        get() {
            return drawerViewModel.cellSize
        }
        set(value) {
            drawerViewModel.cellSize = value
        }

    var canvasSize: Size
        get() {
            return drawerViewModel.canvasSize
        }
        set(value) {
            drawerViewModel.canvasSize = value
        }

    var scale: Float
        get() {
            return drawerViewModel.scale
        }
        set(value) {
            drawerViewModel.scale = value
        }

    var offset: Offset
        get() {
            return drawerViewModel.offset
        }
        set(value) {
            drawerViewModel.offset = value
        }

    fun toggleShowGrid() {
        drawerViewModel.toggleShowGrid()
    }

    fun boxSizeControllerIsVisible(): Boolean {
        return drawerViewModel.boxSizeControllerIsVisible()
    }

    var scaleControllerBoxIsVisible: Boolean
        get() {
            return drawerViewModel.scaleControllerBoxIsVisible
        }
        set(value) {
            drawerViewModel.scaleControllerBoxIsVisible = value
        }

    var gridSizeControllerBoxIsVisible: Boolean
        get() {
            return drawerViewModel.gridSizeControllerBoxIsVisible
        }
        set(value) {
            drawerViewModel.gridSizeControllerBoxIsVisible = value
        }

    var showGrid: Boolean
        get() {
            return drawerViewModel.showGrid
        }
        set(value) {
            drawerViewModel.showGrid = value
        }

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

    var chips by mutableStateOf(immutableListOf<Chip>())

    fun removeChip(chip: Chip) {
        val toImmutableList = chips.toMutableList()

        toImmutableList.remove(chip)

        chips = toImmutableList.toImmutableList()
    }

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

    fun startSelectMap() {
        selectViewModel.startSelectMap()
    }

    fun itemsSelected() {
        if (selectViewModel.imageLoadType == ImageType.MAP) {
            selectedMapUri = selectViewModel.itemsSelected().first().uri

            chips = immutableListOf()
        } else {
            chips = selectViewModel.itemsSelected()
                .map {
                    val chip = Chip(it.name, it.uri, size = cellSize.toInt())

                    it.additionalInfo.forEach { (name, any) ->
                        when (name) {
                            AdditionalIfoName.NAME -> chip.name = any as String
                            AdditionalIfoName.HP -> chip.hp = any as Int
                        }
                    }

                    chip
                }
                .toImmutableList()
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

    fun deleteFromLoad(deleted: SelectItem) {
        loadedSelectItems = loadedSelectItems.filter { it.uri != deleted.uri }.toImmutableList()
    }
}