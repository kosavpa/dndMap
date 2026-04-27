package com.example.myapplication.ui.selectItem.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.myapplication.ui.common.model.ImageType
import okhttp3.internal.immutableListOf

class SelectViewModel : ViewModel() {
    var loadedSelectItems by mutableStateOf(immutableListOf<SelectItem>())

    var itemsToSelect = mutableListOf<SelectItem>()

    val lazyLoadedItems = 8

    var isNeedOpenSelectScreen by mutableStateOf(false)

    var imageLoadType: ImageType? = null

    fun startSelectMap() {
        isNeedOpenSelectScreen = true

        imageLoadType = ImageType.MAP
    }

    fun itemsSelected(): MutableList<SelectItem> {
        val selected = itemsToSelect.toMutableList()

        finishSelectItemsParams()

        return selected
    }

    fun startSelectChip() {
        isNeedOpenSelectScreen = true

        imageLoadType = ImageType.CHIP
    }

    fun finishSelectItemsParams() {
        loadedSelectItems = immutableListOf()

        imageLoadType = null

        isNeedOpenSelectScreen = false

        itemsToSelect.clear()
    }

    fun addSelectItem(item: SelectItem) {
        if (imageLoadType == ImageType.MAP) {
            itemsToSelect.clear()
        }

        itemsToSelect.add(item)
    }

    fun removeSelectedItem(item: SelectItem) {
        itemsToSelect.remove(item)
    }
}