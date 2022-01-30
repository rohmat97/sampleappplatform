package com.example.sampleappplatform.datamodule.model.entities.request.auth

import com.squareup.moshi.Json

data class GenerateToken(
    @field:Json(name = "appId") val appId: String,
    @field:Json(name = "appKey") val appKey: String,
    @field:Json(name = "accessTokenExpired") val accessTokenExpired: String
)