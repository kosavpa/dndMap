package com.example.myapplication.ui.selectItem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly
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

    val hpDelta = 1

    var isVisibleControlButtons by remember { mutableStateOf(false) }

    var isVisibleHpChanger by remember { mutableStateOf(false) }

    val toggleVisibleControlButton = { isVisibleControlButtons = !isVisibleControlButtons }

    var tmpHp by remember { mutableStateOf("") }


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
        if (isVisibleHpChanger) {
            AlertDialog(
                onDismissRequest = { isVisibleHpChanger = false },
                title = { Text(text = "Изменение ХП") },
                text = {
                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = tmpHp,
                        onValueChange = { tmpHp = it },
                    )
                },
                confirmButton = {
                    Button(
                        {
                            if (
                                tmpHp.isNotBlank()
                                && tmpHp.isDigitsOnly()
                                && tmpHp.toInt() != chip.hp
                            ) {
                                chip.hp = tmpHp.toInt()
                            }

                            isVisibleHpChanger = false
                        }
                    ) {
                        Text("Ok", fontSize = 22.sp)
                    }
                },
                dismissButton = {
                    Button(
                        {
                            isVisibleHpChanger = false
                        }
                    ) {
                        Text("Отмена", fontSize = 22.sp)
                    }
                }
            )
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (isVisibleControlButtons) {
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
                Text(
                    chip.name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    fontStyle = FontStyle.Normal
                )

                Spacer(Modifier.width(8.dp))

                Text(
                    chip.hp.toString(),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    fontStyle = FontStyle.Normal,
                    color = Color.Red,
                    modifier = Modifier.clickable {
                        isVisibleHpChanger = true
                    }
                )
            }
            if (isVisibleControlButtons) {
                Row(
                    horizontalArrangement = Arrangement.Center
                ) {
                    IconButton(toggleVisibleControlButton) {
                        Icon(Icons.Filled.ArrowDropDown, "Скрыть меню")
                    }
                    IconButton({ chip.hp += hpDelta }) {
                        Icon(Icons.Filled.AccountBox, "ХП +")
                    }
                    IconButton({ chip.hp -= hpDelta }) {
                        Icon(Icons.Filled.DateRange, "ХП -")
                    }
                }
            }
        }
    }
}