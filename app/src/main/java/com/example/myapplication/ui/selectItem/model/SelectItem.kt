package com.example.myapplication.ui.selectItem.model

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.myapplication.ui.chip.enums.AdditionalIfoName


class SelectItem(
    name: String,
    var id: String,
    var uri: Uri
) {
    var name by mutableStateOf(name)

    var count by mutableIntStateOf(1)

    var isSelected by mutableStateOf(false)

    val additionalInfo = mutableMapOf<AdditionalIfoName, Any>()

    fun addAdditionalInfo(name: AdditionalIfoName, value: Any) {
        additionalInfo[name] = value
    }
}