package com.example.myapplication.ui.selectItem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import com.example.myapplication.ui.selectItem.model.Chip
import kotlin.math.roundToInt

@Composable
fun ChipToken(chip: Chip, onDrag: (Offset) -> Unit) {
    Image(
        modifier = Modifier
            .size(64.dp)
            .offset { IntOffset(chip.offset.x.roundToInt(), chip.offset.y.roundToInt()) }
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    change.consume()
                    onDrag(dragAmount)
                }
            }
            .clip(CircleShape),
        painter = rememberAsyncImagePainter(chip.uri),
        contentDescription = chip.name,
        contentScale = ContentScale.Crop
    )
}