package com.example.sampleappplatform.datamodule.model.entities.request.room

import com.squareup.moshi.Json

data class JoinMeeting (

    @field:Json(name = "platformLanguage") val platformLanguage: String,
    @field:Json(name = "token") val token: String,
    @field:Json(name = "joinCodeOrLink") val title: String,
    @field:Json(name = "participant") val pmrCode: String?,
    @field:Json(name = "userId") val meetingId: String? = null,
    @field:Json(name = "appId") val appId: String?

)