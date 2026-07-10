package com.example.myapplication.ui.main.component

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.myapplication.ui.common.model.ImageType
import com.example.myapplication.ui.common.model.UiViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MenuWindow(
    closeDrawer: () -> Unit,
    viewModel: UiViewModel
) {
    ConstraintLayout(Modifier.fillMaxSize()) {
        Banner(0.1f)

        val (button1, button2, button3, button4, button5) = createRefs()

        createVerticalChain(button1, button2, button3, button4, button5)

        TextButton(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(button1) {
                    centerHorizontallyTo(parent)

                    top.linkTo(parent.top)

                },
            onClick = {
                viewModel.startSelectMap()

                closeDrawer.invoke()
            },
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.White,
                containerColor = Color.Transparent
            )
        ) {
            Text("Выбрать карту", fontSize = 22.sp)
        }

        TextButton(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(button2) {
                    centerHorizontallyTo(parent)

                    top.linkTo(parent.top)

                },
            onClick = {
                viewModel.startSelectChip()

                closeDrawer.invoke()
            },
            enabled = viewModel.selectedMapUri != null,
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.White,
                containerColor = Color.Transparent,
                disabledContentColor = Color.White.copy(alpha = 0.5f),
                disabledContainerColor = Color.Transparent
            )
        ) {
            Text("Выбрать фишку", fontSize = 22.sp)
        }

        TextButton(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(button3) {
                    centerHorizontallyTo(parent)

                    top.linkTo(parent.top)

                },
            onClick = {
                viewModel.startCreateItem(ImageType.MAP)

                closeDrawer.invoke()
            },
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.White,
                containerColor = Color.Transparent
            )
        ) {
            Text("Создать карту", fontSize = 22.sp)
        }

        TextButton(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(button4) {
                    centerHorizontallyTo(parent)

                    top.linkTo(parent.top)
                },
            onClick = {
                viewModel.startCreateItem(ImageType.CHIP)

                closeDrawer.invoke()
            },
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.White,
                containerColor = Color.Transparent
            )
        ) {
            Text("Создать фишку", fontSize = 22.sp)
        }

        TextButton(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(button5) {
                    centerHorizontallyTo(parent)

                    top.linkTo(parent.top)
                },
            onClick = {
                viewModel.toggleShowGrid()

                closeDrawer.invoke()
            },
            enabled = viewModel.selectedMapUri != null,
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.White,
                containerColor = Color.Transparent,
                disabledContentColor = Color.White.copy(alpha = 0.5f),
                disabledContainerColor = Color.Transparent
            )
        ) {
            Text("Включить сетку", fontSize = 22.sp)
        }
    }
}