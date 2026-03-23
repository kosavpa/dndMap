package com.example.myapplication.ui.components

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.myapplication.ui.model.UiViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.InputStream
import java.net.URL
import java.nio.file.Files
import java.nio.file.Path
import java.util.*
import kotlin.io.path.exists

@RequiresApi(Build.VERSION_CODES.O)
suspend fun save(context: Context, viewModel: UiViewModel) {
    checkNotNull(viewModel.imageLoadType) { "Image type is required!" }
    checkNotNull(viewModel.imgLoadUri) { "Image uri is required!" }
    checkNotNull(viewModel.imageName) { "Image name is required!" }

    val dir = File(context.filesDir, viewModel.imageLoadType!!.name)

    if (!dir.exists()) {
        dir.mkdirs()
    }

    var imagePath = dir.toPath().resolve(viewModel.imageName)

    if (imagePath.exists()) {
        imagePath = dir.toPath().resolve("$viewModel.imageName$FILE_SEPARATOR${UUID.randomUUID()}")
    }

    withContext(Dispatchers.IO) {
        if (viewModel.isFromGallery) {
            context.contentResolver.openInputStream(viewModel.imgLoadUri!!)?.use { input ->
                copyFromIS(input, imagePath)
            }
        } else {
            URL(viewModel.imgLoadUri.toString()).openStream()?.use { input ->
                copyFromIS(input, imagePath)
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun copyFromIS(input: InputStream, imagePath: Path) {
    Files.copy(input, imagePath)
}

private var FILE_SEPARATOR = "?$?"