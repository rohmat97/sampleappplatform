package com.example.sampleappplatform.datamodule.model.dto.core

import androidx.annotation.Keep
import com.squareup.moshi.Json
import java.io.Serializable

@Keep
open class BaseApiDto : Serializable {
    @field:Json(name = "code")
    val code: Int? = null

    @field:Json(name = "success")
    val success: Boolean? = null

    @field:Json(name = "message")
    val message: String? = null
}