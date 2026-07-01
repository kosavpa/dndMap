package com.example.myapplication

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.tv.material3.ExperimentalTvMaterial3Api
import com.example.myapplication.ui.common.model.UiViewModel
import com.example.myapplication.ui.main.component.MainWindow
import com.example.myapplication.ui.main.component.MenuWindow
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    @OptIn(ExperimentalTvMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            DrawMainWindow()
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun DrawMainWindow(viewModel: UiViewModel = viewModel<UiViewModel>()) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)

    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        modifier = Modifier.fillMaxSize(),
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
}
