package com.example.sampleappplatform.datamodule.model.entities.request.auth

import com.squareup.moshi.Json

data class TemporaryToken(
    @field:Json(name = "linkOrCodeRoom") val linkOrCodeRoom: String
)