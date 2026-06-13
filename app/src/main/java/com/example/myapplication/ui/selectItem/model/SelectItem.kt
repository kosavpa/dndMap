package com.example.myapplication.ui.selectItem.model

import android.net.Uri
import com.example.myapplication.ui.selectItem.enums.AdditionalIfoName


class SelectItem(val name:String, val id: String, val uri: Uri) {
    val additionalInfo = mutableMapOf<AdditionalIfoName, Any>()

    fun addAdditionalInfo(name: AdditionalIfoName, value: Any) {
        additionalInfo[name] = value
    }
}