package com.example.myapplication.ui.main.component

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.myapplication.ui.createItem.component.CreateItemDialog
import com.example.myapplication.ui.drawer.component.ImageDrawer
import com.example.myapplication.ui.main.model.UiViewModel
import com.example.myapplication.ui.selectItem.component.SelectItemScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainWindow(
    openDrawer: () -> Unit,
    viewModel: UiViewModel
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Red)
    ) {
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
            onClick = {
                openDrawer.invoke()
            },
            content = {
                Icon(Icons.Filled.Menu, "")
            }
        )
    }
}