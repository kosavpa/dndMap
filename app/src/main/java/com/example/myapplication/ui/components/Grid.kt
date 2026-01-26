package com.example.myapplication.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.TransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Slider
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.model.UiViewModel
import kotlin.math.floor

@Composable
fun Grid(rememberTransformableState: TransformableState, viewModel: UiViewModel) {
    val startSize = 63f

    var cellSize by remember { mutableFloatStateOf(startSize) }

    Box(modifier = Modifier.fillMaxSize()) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer(scaleX = viewModel.scale, scaleY = viewModel.scale)
                .transformable(state = rememberTransformableState)
        ) {
            val width = size.width
            val height = size.height

            val dpCellSize = cellSize.dp.toPx()

            drawVerticalLines(dpCellSize, width, height)

            drawHorizontalLines(dpCellSize, width, height)
        }

        Slider(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(16.dp),
            value = cellSize,
            onValueChange = { cellSize = it },
            valueRange = startSize..189f
        )
    }
}

private fun DrawScope.drawVerticalLines(dpCellSize: Float, width: Float, height: Float) {
    val countX = floor(width / dpCellSize).toInt()

    val leftoverX = width - dpCellSize * countX

    val stepX = dpCellSize + leftoverX / countX

    var x = 0f

    while (x <= width) {
        drawLine(
            color = Color.Black,
            start = Offset(x, 0f),
            end = Offset(x, height),
            strokeWidth = 1.5.dp.toPx()
        )

        x += stepX
    }
}

private fun DrawScope.drawHorizontalLines(dpCellSize: Float, width: Float, height: Float) {
    val countY = floor(height / dpCellSize).toInt()

    val leftoverY = height - dpCellSize * countY

    val stepY = dpCellSize + leftoverY / countY

    var y = 0f

    while (y <= height) {
        drawLine(
            color = Color.Black,
            start = Offset(0f, y),
            end = Offset(width, y),
            strokeWidth = 1.5.dp.toPx()
        )

        y += stepY
    }
}