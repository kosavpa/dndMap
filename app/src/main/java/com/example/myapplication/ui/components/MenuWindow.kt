package com.example.myapplication.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.myapplication.ui.model.UiViewModel

@Composable
fun MenuWindow(
    closeDrawer: () -> Unit,
    viewModel: UiViewModel
) {
    ConstraintLayout {
        val (button1, button2, button3) = createRefs()

        createVerticalChain(button1, button2, button3)

        TextButton(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(button1) {
                    centerHorizontallyTo(parent)

                    top.linkTo(parent.top)

                },
            onClick = {
                viewModel.needOpenDialog()

                closeDrawer.invoke()
            },
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.LightGray,
                containerColor = Color.Transparent
            )
        ) {
            Text("Добавить фон", fontSize = 22.sp)
        }
        TextButton(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(button2) {
                    centerHorizontallyTo(parent)

                    top.linkTo(parent.top)
                },
            onClick = {
                viewModel.needOpenDialog()
            },
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.LightGray,
                containerColor = Color.Transparent
            )
        ) {
            Text("Добавить фишку", fontSize = 22.sp)
        }
        TextButton(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(button3) {
                    centerHorizontallyTo(parent)

                    top.linkTo(parent.top)
                },
            onClick = {
                viewModel.toggleShowGrid()

                closeDrawer.invoke()
            },
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.LightGray,
                containerColor = Color.Transparent
            )
        ) {
            Text("Включить сетку", fontSize = 22.sp)
        }
    }
}