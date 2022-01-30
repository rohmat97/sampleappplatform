package com.example.sampleappplatform.datamodule.model.dto.auth

import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.example.sampleappplatform.datamodule.model.dto.core.BaseApiDto
import java.io.Serializable

@Keep
class TemporaryTokenDto : BaseApiDto() {
    @field:Json(name = "data") val data: Data? = null

    @Keep
    class Data: Serializable {
        @field:Json(name = "method") val method: String? = null
        @field:Json(name = "accessToken") val accessToken: String? = null
    }
}