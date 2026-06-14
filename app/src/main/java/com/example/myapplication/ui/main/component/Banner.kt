package com.example.myapplication.ui.main.component

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.myapplication.R

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Banner(alfa: Float = 1f) {
    Image(
        contentDescription = "Background Image",
        painter = painterResource(id = R.drawable.back),
        contentScale = ContentScale.Crop,
        modifier = Modifier.fillMaxSize(),
        alpha = alfa
    )
}