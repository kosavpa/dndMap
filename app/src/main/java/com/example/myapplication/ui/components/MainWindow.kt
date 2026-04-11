package com.example.myapplication.ui.components

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
import com.example.myapplication.ui.model.UiViewModel

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

        if (viewModel.isNeedOpenChipCreationScreen) {
            ChipCreateDialog(viewModel)
        }

        if (viewModel.isNeedOpenMapCreationScreen) {
            MapCreateDialog(viewModel)
        }

        if (viewModel.isNeedOpenSelectMapScreen) {
            SelectMapScreen(viewModel)
        }

        if (viewModel.isNeedOpenSelectChipScreen) {
            SelectChipScreen(viewModel)
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