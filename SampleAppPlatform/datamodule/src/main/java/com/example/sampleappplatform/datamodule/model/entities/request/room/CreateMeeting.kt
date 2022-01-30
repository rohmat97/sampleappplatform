package com.example.sampleappplatform.datamodule.model.entities.request.room

import com.squareup.moshi.Json

data class CreateMeeting (

    @field:Json(name = "platformLanguage") val platformLanguage: String,
    @field:Json(name = "token") val token: String,
    @field:Json(name = "title") val title: String,
    @field:Json(name = "pmrCode") val pmrCode: String? = null,
    @field:Json(name = "meetingId") val meetingId: String? = null,
    @field:Json(name = "appId") val appId: String? = null,
    @field:Json(name = "serviceCode") val serviceCode: String,
    @field:Json(name = "isContinue") var isContinue: Boolean? = null

)