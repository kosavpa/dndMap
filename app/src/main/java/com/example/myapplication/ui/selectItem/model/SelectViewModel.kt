package com.example.myapplication.ui.selectItem.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.myapplication.ui.common.model.ImageType
import okhttp3.internal.immutableListOf

class SelectViewModel : ViewModel() {
    var loadedSelectItems by mutableStateOf(immutableListOf<SelectItem>())

    val lazyLoadedItems = 8

    var isNeedOpenSelectScreen by mutableStateOf(false)

    var imageLoadType: ImageType? = null

    fun startSelectMap() {
        isNeedOpenSelectScreen = true

        imageLoadType = ImageType.MAP
    }

    fun itemsSelected(): MutableList<SelectItem> {
        val selected = loadedSelectItems
            .filter { it.isSelected }
            .toMutableList()

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
    }
}