package com.example.myapplication.ui.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.model.ImageType
import com.example.myapplication.ui.model.UiViewModel
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MapCreateDialog(
    viewModel: UiViewModel
) {
    val current = LocalContext.current

    val coroutineScope = rememberCoroutineScope()

    if (viewModel.isResolveImageSource) {
        ImageSourceResolver(viewModel)
    }

    if (viewModel.isPickImage) {
        ImgLoader(viewModel, ImageType.MAP)
    }

    var tmpImgName by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = { viewModel.toggleCreateMap() },
        text = {
            Column(
                Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                if (viewModel.imgLoadUri == null) {
                    Text("Имя карты", fontSize = 22.sp)

                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = tmpImgName,
                        onValueChange = { tmpImgName = it }
                    )

                    Button(
                        enabled = tmpImgName.isNotBlank(),
                        onClick = {
                            viewModel.imageName = tmpImgName

                            viewModel.toggleNeedResolveImageDialog()
                        }
                    ) { Text("Загрузить изображение карты", fontSize = 22.sp) }
                } else {
                    Text(
                        "Карта ${viewModel.imageName} загружена, нажмите 'Ок' для сохранения",
                        fontSize = 22.sp
                    )
                }
            }
        },
        confirmButton = {
            Button(
                {
                    if (
                        viewModel.imgLoadUri != null
                        && viewModel.imageLoadType == ImageType.MAP
                        && viewModel.imageName != null
                        && viewModel.imageName!!.isNotBlank()
                    ) {
                        coroutineScope.launch {
                            save(current, viewModel)

                            viewModel.toggleCreateMap()
                        }
                    }

                }
            ) { Text("Ок", fontSize = 22.sp) }
        },
        dismissButton = {
            Button(
                {
                    viewModel.toggleCreateMap()
                }
            ) { Text("Отмена", fontSize = 22.sp) }
        }
    )
}