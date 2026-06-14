package com.example.myapplication.ui.chip.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly
import coil3.compose.rememberAsyncImagePainter
import com.example.myapplication.R
import com.example.myapplication.ui.chip.model.Chip
import kotlin.math.roundToInt

@Composable
fun ChipToken(
    chip: Chip,
    onDrag: (Offset) -> Unit,
    onRemove: () -> Unit
) {
    val sizeDelta = 5.dp

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
            .background(Color.Black.copy(alpha = 0.2f), CircleShape)
            .wrapContentSize(Alignment.Center)
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

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (isVisibleControlButtons) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    IconButton({ chip.baseSize += sizeDelta }) {
                        Icon(
                            modifier = Modifier.size(32.dp),
                            painter = painterResource(R.drawable.zoom_in),
                            contentDescription = "Увеличить фишку",
                            tint = Color.Unspecified
                        )
                    }
                    IconButton({ chip.baseSize -= sizeDelta }) {
                        Icon(
                            modifier = Modifier.size(32.dp),
                            painter = painterResource(R.drawable.zoom_out),
                            contentDescription = "Уменьшить фишку",
                            tint = Color.Unspecified
                        )
                    }
                }
            }


            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                if (isVisibleControlButtons) {
                    IconButton(onRemove) {
                        Icon(
                            Icons.Filled.Close,
                            modifier = Modifier.size(32.dp),
                            contentDescription = "Убрать фишку",
                            tint = Color.Unspecified
                        )
                    }
                }

                Image(
                    modifier = Modifier
                        .clickable { toggleVisibleControlButton() }
                        .size(chip.size)
                        .clip(CircleShape),
                    painter = rememberAsyncImagePainter(chip.uri),
                    contentDescription = chip.name,
                    contentScale = ContentScale.Crop
                )

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
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    IconButton({ chip.hp += hpDelta }) {
                        Icon(
                            modifier = Modifier.size(32.dp),
                            painter = painterResource(R.drawable.favorite_p),
                            contentDescription = "ХП +",
                            tint = Color.Unspecified
                        )
                    }
                    IconButton({ chip.hp -= hpDelta }) {
                        Icon(
                            modifier = Modifier.size(32.dp),
                            painter = painterResource(R.drawable.favorite_m),
                            contentDescription = "ХП -",
                            tint = Color.Unspecified
                        )
                    }
                }
            }
        }
    }
}