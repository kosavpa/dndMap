package com.example.myapplication.ui.util

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.net.toUri
import com.example.myapplication.ui.model.Chip
import com.example.myapplication.ui.model.ImageType
import com.example.myapplication.ui.model.Map
import com.example.myapplication.ui.model.UiViewModel
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

fun loadMapFile(offset: Int, count: Int, context: Context, viewModel: UiViewModel) {
    val dir = File(
        context.filesDir,
        ImageType.MAP.name
    )

    val fileNames = dir.list()

    if (fileNames == null || fileNames.isEmpty()) {
        return
    }

    if (fileNames.count() >= offset) {
        val tmpMapList = mutableListOf<Map>()

        tmpMapList.addAll(viewModel.loadedMaps)
        tmpMapList.addAll(
            fileNames.drop(offset)
                .take(count)
                .map { createMap(it, dir) }
                .toList()
        )

        viewModel.loadedMaps = tmpMapList.toImmutableList()
    } else {
        viewModel.loadedMaps = fileNames.map { createMap(it, dir) }.toImmutableList()
    }
}

fun loadChipFile(offset: Int, count: Int, context: Context, viewModel: UiViewModel) {
    val dir = File(
        context.filesDir,
        ImageType.CHIP.name
    )

    val fileNames = dir.list()

    if (fileNames == null || fileNames.isEmpty()) {
        return
    }

    if (fileNames.count() >= offset) {
        val tmpMapList = mutableListOf<Chip>()

        tmpMapList.addAll(viewModel.loadedChips)
        tmpMapList.addAll(
            fileNames.drop(offset)
                .take(count)
                .map { createChip(it, dir) }
                .toList()
        )

        viewModel.loadedChips = tmpMapList.toImmutableList()
    } else {
        viewModel.loadedChips = fileNames.map { createChip(it, dir) }.toImmutableList()
    }
}

fun createChip(name: String, dir: File): Chip {
    return Chip(name.split(FILE_SEPARATOR)[0], File(dir, name).toUri())
}

fun createMap(name: String, dir: File): Map {
    return Map(name.split(FILE_SEPARATOR)[0], File(dir, name).toUri())
}
