package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.tv.material3.ExperimentalTvMaterial3Api
import com.example.myapplication.ui.components.ImageUriPicker
import com.example.myapplication.ui.components.MainWindow
import com.example.myapplication.ui.components.MenuWindow
import com.example.myapplication.ui.model.UiViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalTvMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            DrawMainWindow()
        }
    }
}

@Composable
private fun DrawMainWindow(viewModel: UiViewModel = viewModel<UiViewModel>()) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)

    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            MenuWindow(
                { scope.launch { drawerState.close() } },
                viewModel
            )
        },
        scrimColor = Color.DarkGray,
        content = {
            MainWindow(
                { scope.launch { drawerState.open() } },
                viewModel
            )
        }
    )

    ImageUriPicker(viewModel)
}
