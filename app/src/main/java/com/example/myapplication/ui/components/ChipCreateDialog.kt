package com.example.myapplication.ui.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import com.example.myapplication.ui.model.ImageType
import com.example.myapplication.ui.model.UiViewModel
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ChipCreateDialog(
    viewModel: UiViewModel
) {
    val current = LocalContext.current

    val coroutineScope = rememberCoroutineScope()

    if (viewModel.isResolveImageSource) {
        ImageSourceResolver(viewModel)
    }

    if (viewModel.isPickImage) {
        ImgLoader(viewModel, ImageType.CHIP)
    }

    var painter: AsyncImagePainter? = null

    if (viewModel.imgLoadUri != null && viewModel.imageLoadType == ImageType.CHIP) {
        painter = rememberAsyncImagePainter(
            model = viewModel.imgLoadUri,
            onError = {
                android.util.Log.e(
                    "CoilError",
                    "URI: ${viewModel.imgLoadUri}, Ошибка загрузки: $it"
                )
            }
        )
    }

    var tmpImgName by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = { viewModel.toggleCreateChip() },
        text = {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Column(
                    Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text("Имя фишки", fontSize = 22.sp)

                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = tmpImgName,
                        onValueChange = { tmpImgName = it }
                    )

                    Text("Изображение фишки", fontSize = 22.sp)

                    if (viewModel.imgLoadUri == null || viewModel.imageLoadType != ImageType.CHIP) {
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .padding(0.dp, 20.dp),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Button(
                                enabled = tmpImgName.isNotBlank(),
                                onClick = {
                                    viewModel.imageName = tmpImgName

                                    viewModel.toggleNeedResolveImageDialog()
                                }
                            ) { Text("Загрузить изображение фишки", fontSize = 22.sp) }
                        }
                    } else {
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .padding(0.dp, 20.dp),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Canvas(
                                Modifier
                                    .size(100.dp)
                                    .clip(CircleShape)
                                    .background(Color.Red)
                                    .border(
                                        BorderStroke(4.dp, Color.Black),
                                        CircleShape
                                    )
                            ) {
                                if (painter != null) {
                                    with(painter) {
                                        draw(size)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(
                {
                    if (
                        viewModel.imgLoadUri != null
                        && viewModel.imageLoadType == ImageType.CHIP
                        && viewModel.imageName != null
                        && viewModel.imageName!!.isNotBlank()
                    ) {
                        coroutineScope.launch {
                            save(current, viewModel)

                            viewModel.toggleCreateChip()
                        }
                    }
                }
            ) { Text("Ок", fontSize = 22.sp) }
        },
        dismissButton = {
            Button(
                {
                    viewModel.toggleCreateChip()
                }
            ) { Text("Отмена", fontSize = 22.sp) }
        }
    )
}