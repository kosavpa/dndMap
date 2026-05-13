package com.example.myapplication.ui.selectItem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
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
fun ChipToken(
    chip: Chip,
    onDrag: (Offset) -> Unit,
    onRemove: () -> Unit
) {
    val sizeDelta = 5

    var isVisibleControlButtons by remember { mutableStateOf(false) }

    val toggleVisibleControlButton = { isVisibleControlButtons = !isVisibleControlButtons }

    Box(
        modifier = Modifier
            .offset { IntOffset(chip.offset.x.roundToInt(), chip.offset.y.roundToInt()) }
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    change.consume()
                    onDrag(dragAmount)
                }
            }
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            if (isVisibleControlButtons) {

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Row(
                        horizontalArrangement = Arrangement.Center
                    ) {

                        IconButton(onRemove) {
                            Icon(Icons.Filled.Close, "Убрать фишку")
                        }

                        IconButton({ chip.size += sizeDelta }) {
                            Icon(Icons.Filled.Add, "Увеличить фишку")
                        }

                        IconButton({ chip.size -= sizeDelta }) {
                            Icon(Icons.Filled.Build, "Уменьшить фишку")
                        }
                    }

                    Row(
                        horizontalArrangement = Arrangement.Center
                    ) {

                        IconButton(toggleVisibleControlButton) {
                            Icon(Icons.Filled.ArrowDropDown, "Скрыть меню")
                        }

                        IconButton(onRemove) {
                            Icon(Icons.Filled.AccountBox, "ХП +")
                        }

                        IconButton(onRemove) {
                            Icon(Icons.Filled.DateRange, "ХП -")
                        }
                    }
                }
            }

            Image(
                modifier = Modifier
                    .clickable { toggleVisibleControlButton() }
                    .size(chip.size.dp)
                    .clip(CircleShape),
                painter = rememberAsyncImagePainter(chip.uri),
                contentDescription = chip.name,
                contentScale = ContentScale.Crop
            )

            Row(
                modifier = Modifier.padding(8.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(chip.name)

                Spacer(Modifier.width(8.dp))

                Text(chip.hp.toString())
            }
        }
    }
}