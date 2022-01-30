package com.example.sampleappplatform.datamodule.model.dto.core

import androidx.annotation.Keep
import com.squareup.moshi.Json

@Keep
class MessageApiDto : BaseApiDto() {

    @field:Json(name = "data")
    val data: Data? = null

    @Keep
    inner class Data {
        @field:Json(name = "title")
        val title: String? = null

        @field:Json(name = "mess")
        val mess: String? = null
    }

}