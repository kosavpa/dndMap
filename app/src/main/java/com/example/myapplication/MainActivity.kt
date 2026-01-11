package com.example.myapplication

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.tv.material3.ExperimentalTvMaterial3Api
import com.example.myapplication.ui.components.MainWindow
import com.example.myapplication.ui.components.MenuWindow

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
private fun DrawMainWindow() {
    val drawerState = rememberDrawerState(DrawerValue.Closed)

    var showGrid by remember { mutableStateOf(false) }

    var openImagePickerDialog by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()

    var isFromGallery by remember { mutableStateOf(false) }

    var isFromInternet by remember { mutableStateOf(false) }

    var galleryUri by remember { mutableStateOf<Uri?>(null) }

    var internetUri by remember { mutableStateOf<String?>(null) }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            MenuWindow(
                drawerState,
                scope,
                showGrid,
                { showGrid = it },
                {
                    openImagePickerDialog = it
                }
            )
        },
        scrimColor = Color.DarkGray,
        content = {
            MainWindow(
                drawerState,
                scope,
                showGrid,
                openImagePickerDialog,
                { openImagePickerDialog = it },
                galleryUri,
                internetUri,
                { isFromGallery = it },
                { isFromInternet = it }
            )
        }
    )

    if (isFromGallery) {
        val galleryLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent(),
            onResult = { uri: Uri? ->
                uri?.let { galleryUri = uri }
            }
        )

        LaunchedEffect(Unit) {
            galleryLauncher.launch("image/*")
        }
    }

    if (isFromInternet) {
        var tmpInternetUri by remember { mutableStateOf<String?>(null) }

        AlertDialog(
            onDismissRequest = { },
            title = { Text(text = "Введите ссылку на изображение") },
            text = {
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = "...",
                    onValueChange = { tmpInternetUri = it }
                )
            },
            confirmButton = {
                Button(
                    {
                        internetUri = tmpInternetUri

                        isFromInternet = false
                    }
                ) {
                    Text("Ok", fontSize = 22.sp)
                }

            }
        )
    }
}
