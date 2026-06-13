package com.example.myapplication.ui.drawer.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.lifecycle.ViewModel

class DrawerViewModel : ViewModel() {

    var scaleControllerBoxIsVisible by mutableStateOf(false)

    var gridSizeControllerBoxIsVisible by mutableStateOf(false)

    var showGrid by mutableStateOf(false)

    private val _startCellSize = 63f

    private val _endCellSize = 189f

    val cellSizeRange = _startCellSize.._endCellSize

    var cellSize by mutableFloatStateOf(_startCellSize)

    var canvasSize by mutableStateOf(Size.Zero)

    var scale by mutableFloatStateOf(1f)

    var offset by mutableStateOf(Offset.Zero)

    var offsetPermitted by mutableStateOf(false)

    fun toggleShowGrid() {
        showGrid = !showGrid
    }

    fun boxSizeControllerIsVisible(): Boolean {
        return scaleControllerBoxIsVisible || gridSizeControllerBoxIsVisible
    }
}