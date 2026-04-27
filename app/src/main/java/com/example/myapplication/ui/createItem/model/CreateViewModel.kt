package com.example.myapplication.ui.createItem.model

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.myapplication.ui.common.model.ImageType

class CreateViewModel : ViewModel() {

    var imageLoadType: ImageType? = null

    var isNeedOpenItemCreationScreen by mutableStateOf(false)

    var imgLoadUri by mutableStateOf<Uri?>(null)

    fun setLoadUri(uri: Uri?) {
        imgLoadUri = uri

        cancelResolveOrPickImage()
    }

    var imageName: String? = null

    var isPickImage by mutableStateOf(false)

    var isResolveImageSource by mutableStateOf(false)

    var isFromGallery by mutableStateOf(false)

    var isFromInternet by mutableStateOf(false)

    fun toggleNeedResolveImageDialog() {
        isResolveImageSource = !isResolveImageSource
    }

    fun imageFromInternet() {
        isResolveImageSource = false

        isFromGallery = false

        isFromInternet = true

        isPickImage = true
    }

    fun imageFromGallery() {
        isResolveImageSource = false

        isFromInternet = false

        isFromGallery = true

        isPickImage = true
    }

    fun startCreateItem(type: ImageType) {
        clearImageParams()

        isNeedOpenItemCreationScreen = true

        imageLoadType = type
    }

    fun finishCreateItem() {
        isNeedOpenItemCreationScreen = false

        clearImageParams()
    }

    fun clearImageParams() {
        imageLoadType = null

        imgLoadUri = null

        imageName = null

        cancelResolveOrPickImage()
    }

    fun cancelResolveOrPickImage() {
        isResolveImageSource = false

        isFromGallery = false

        isFromInternet = false

        isPickImage = false
    }
}