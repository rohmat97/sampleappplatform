package com.example.sampleappplatform.datamodule.model.dto.room

import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.example.sampleappplatform.datamodule.model.dto.core.BaseApiDto

@Keep
class CreateMeetingDto : BaseApiDto() {
    @field:Json(name = "data")
    val data: Data? = null

    @Keep
    class Data {
        @field:Json(name = "meetingData")
        val meetingData: MeetingData? = null

        @field:Json(name = "detailMeetingData")
        val detailMeetingData: DetailMeetingData? = null
    }

    @Keep
    class MeetingData {
        @field:Json(name = "name")
        val name: String? = null

        @field:Json(name = "joinLink")
        val joinLink: String? = null
    }

    @Keep
    class DetailMeetingData {
        @field:Json(name = "serviceName")
        val serviceName: String? = null

        @field:Json(name = "serviceCode")
        val serviceCode: String? = null

        @field:Json(name = "meetingCode")
        val meetingCode: String? = null

        @field:Json(name = "meetingLink")
        val meetingLink: String? = null

        @field:Json(name = "meetingCoreLink")
        val meetingCoreLink: String? = null

        @field:Json(name = "meetingTitle")
        val meetingTitle: String? = null

        @field:Json(name = "serviceImageUrl")
        val serviceImageUrl: String? = null

        @field:Json(name = "createdDate")
        val createdDate: String? = null

        @field:Json(name = "expiredDateUnixTime")
        val expiredDateUnixTime: Long? = null

        @field:Json(name = "isHost")
        val isHost: Boolean? = null
    }

}