package com.example.myapplication.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Slider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import com.example.myapplication.ui.model.UiViewModel
import kotlin.math.floor

@Composable
fun MainWindow(
    openDrawer: () -> Unit,
    viewModel: UiViewModel
) {
    if (viewModel.isOpenImagePickerDialog) {
        ImageCreatorDialog(viewModel)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Red)
    ) {
        viewModel.imageUri?.let {
            val painter = rememberAsyncImagePainter(
                viewModel.imageUri,
            )

            Box(Modifier.fillMaxSize()) {
                Canvas(
                    Modifier
                        .fillMaxSize()
                        .pointerInput(Unit) {
                            detectTransformGestures(
                                true,
                                { _, pan, _, _ ->
                                    viewModel.offset += pan
                                }
                            )
                        }
                ) {
                    viewModel.canvasSize = size

                    with(painter) {
                        withTransform(
                            {
                                scale(scaleX = viewModel.scale, scaleY = viewModel.scale)
                                translate(viewModel.offset.x, viewModel.offset.y)
                            }
                        ) {
                            draw(size)

                            if (viewModel.showGrid) {
                                drawVerticalLines(viewModel)

                                drawHorizontalLines(viewModel)
                            }
                        }
                    }
                }

                if (viewModel.showGrid) {
                    Slider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(32.dp),
                        value = viewModel.cellSize,
                        onValueChange = { viewModel.cellSize = it },
                        valueRange = viewModel.cellSizeRange
                    )
                }
            }
        }

        Slider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp)
                .align(Alignment.BottomCenter),
            value = 1f,
            onValueChange = { viewModel.scale = it },
            steps = 100,
            valueRange = 1f..2f
        )

        IconButton(
            onClick = {
                openDrawer.invoke()
            },
            content = {
                Icon(Icons.Filled.Menu, "")
            }
        )

    }
}

private fun DrawScope.drawVerticalLines(viewModel: UiViewModel) {
    val countX = floor(viewModel.canvasSize.width / viewModel.cellSize).toInt()

    val leftoverX = viewModel.canvasSize.width - viewModel.cellSize * countX

    val stepX = viewModel.cellSize + leftoverX / countX

    var x = 0f

    while (x <= viewModel.canvasSize.width) {
        drawLine(
            color = Color.Black,
            start = Offset(x, 0f),
            end = Offset(x, viewModel.canvasSize.height),
            strokeWidth = 1.5.dp.toPx()
        )

        x += stepX
    }
}

private fun DrawScope.drawHorizontalLines(viewModel: UiViewModel) {
    val countY = floor(viewModel.canvasSize.height / viewModel.cellSize).toInt()

    val leftoverY = viewModel.canvasSize.height - viewModel.cellSize * countY

    val stepY = viewModel.cellSize + leftoverY / countY

    var y = 0f

    while (y <= viewModel.canvasSize.height) {
        drawLine(
            color = Color.Black,
            start = Offset(0f, y),
            end = Offset(viewModel.canvasSize.width, y),
            strokeWidth = 1.5.dp.toPx()
        )

        y += stepY
    }
}