package com.example.myapplication.ui.main.component

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.common.model.UiViewModel
import com.example.myapplication.ui.createItem.component.CreateItemDialog
import com.example.myapplication.ui.drawer.component.ImageDrawer
import com.example.myapplication.ui.selectItem.component.SelectItemScreen
import kotlinx.coroutines.delay

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainWindow(
    openDrawer: () -> Unit,
    viewModel: UiViewModel
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Banner()

        if (viewModel.selectedMapUri != null) {
            ImageDrawer(viewModel)
        }

        if (viewModel.isNeedOpenItemCreationScreen) {
            CreateItemDialog(viewModel)
        }

        if (viewModel.isNeedOpenSelectScreen) {
            SelectItemScreen(viewModel)
        }

        IconButton(
            modifier = Modifier.align(Alignment.TopStart),
            onClick = {
                openDrawer.invoke()
            },
            content = {
                Icon(
                    Icons.Filled.Menu,
                    modifier = Modifier
                        .size(34.dp)
                        .clip(CircleShape)
                        .background(Color.LightGray.copy(alpha = if (viewModel.backgroundIsViewed) 0.4f else 0f)),
                    contentDescription = "Меню"
                )
            }
        )
    }

    LaunchedEffect(viewModel.isNeedHighlightMenu) {
        if (viewModel.isNeedHighlightMenu) {

            repeat(8) {
                viewModel.backgroundIsViewed = !viewModel.backgroundIsViewed

                if (viewModel.backgroundIsViewed) {
                    delay(600)
                } else {
                    delay(200)
                }
            }

            viewModel.backgroundIsViewed = true
            viewModel.isNeedHighlightMenu = false
        }
    }
}