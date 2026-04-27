package com.example.myapplication.ui.common.util

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.net.toUri
import com.example.myapplication.ui.common.model.UiViewModel
import com.example.myapplication.ui.selectItem.model.SelectItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.internal.toImmutableList
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

fun loadSelectFiles(context: Context, viewModel: UiViewModel) {
    checkNotNull(viewModel.imageLoadType) { "Image type is required!" }

    val dir = File(
        context.filesDir,
        viewModel.imageLoadType!!.name
    )

    val fileNames = dir.list()

    if (fileNames == null || fileNames.isEmpty()) {
        return
    }

    val toMutableList = viewModel.loadedSelectItems.toMutableList()

    if (fileNames.count() >= viewModel.lazyLoadedItems) {
        toMutableList.addAll(
            fileNames.drop(viewModel.loadedSelectItems.size)
                .take(viewModel.lazyLoadedItems)
                .map { createSelectItem(it, dir) }
                .toList()
        )
    } else {
        toMutableList.addAll(
            fileNames.map { createSelectItem(it, dir) }
        )
    }

    viewModel.loadedSelectItems = toMutableList.toImmutableList()
}

fun createSelectItem(name: String, dir: File): SelectItem {
    return SelectItem(
        name.split(FILE_SEPARATOR)[0],
        File(dir, name).toUri()
    )
}
