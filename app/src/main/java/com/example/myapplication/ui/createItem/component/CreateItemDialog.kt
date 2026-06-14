package com.example.myapplication.ui.createItem.component

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.myapplication.ui.common.model.UiViewModel
import com.example.myapplication.ui.common.util.save
import com.example.myapplication.ui.loaderItem.component.ImageSourceResolver
import com.example.myapplication.ui.loaderItem.component.ImgLoader
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CreateItemDialog(
    viewModel: UiViewModel
) {
    val current = LocalContext.current

    val coroutineScope = rememberCoroutineScope()

    if (viewModel.isResolveImageSource) {
        ImageSourceResolver(viewModel)
    }

    if (viewModel.isPickImage) {
        ImgLoader(viewModel)
    }

    var painter: AsyncImagePainter? = null

    if (viewModel.imgLoadUri != null && viewModel.imageLoadType != null) {
        painter = rememberAsyncImagePainter(
            model = viewModel.imgLoadUri,
            onError = {
                Log.e(
                    "CoilError",
                    "URI: ${viewModel.imgLoadUri}, Ошибка загрузки: $it"
                )
            }
        )
    }

    var tmpImgName by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = { viewModel.finishCreateItem() },
        text = {
            Column(
                Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Text("Имя для изображения", fontSize = 22.sp)

                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = tmpImgName,
                    onValueChange = { tmpImgName = it }
                )

                Text("Изображение", fontSize = 22.sp)

                if (viewModel.imgLoadUri == null) {
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
                        ) { Text("Загрузить изображение", fontSize = 22.sp) }
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
                                .clip(RoundedCornerShape(16.dp))
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
        },
        confirmButton = {
            Button(
                {
                    if (
                        viewModel.imgLoadUri != null
                        && viewModel.imageName != null
                        && viewModel.imageName!!.isNotBlank()
                    ) {
                        coroutineScope.launch {
                            save(current, viewModel)

                            viewModel.finishCreateItem()
                        }
                    }
                }
            ) { Text("Ок", fontSize = 22.sp) }
        },
        dismissButton = {
            Button(
                {
                    viewModel.finishCreateItem()
                }
            ) { Text("Отмена", fontSize = 22.sp) }
        }
    )
}