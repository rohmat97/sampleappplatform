package com.example.sampleappplatform.datamodule.model.dto.room

import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.example.sampleappplatform.datamodule.model.dto.core.BaseApiDto

@Keep
class JoinMeetingDto : BaseApiDto() {
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
        @field:Json(name = "role")
        val role: String? = null

        @field:Json(name = "linkOrCodeRoom")
        val linkOrCodeRoom: String? = null

        @field:Json(name = "specialUser")
        val specialUser: Boolean? = null
    }

    @Keep
    class DetailMeetingData {
        @field:Json(name = "meetingCode")
        val meetingCode: String? = null

        @field:Json(name = "meetingLink")
        val meetingLink: String? = null

        @field:Json(name = "meetingCoreLink")
        val meetingCoreLink: String? = null

        @field:Json(name = "meetingTitle")
        val meetingTitle: String? = null

        @field:Json(name = "participant")
        val participant: String? = null

        @field:Json(name = "serviceCode")
        val serviceCode: String? = null

        @field:Json(name = "createdDate")
        val createdDate: String? = null

        @field:Json(name = "isHost")
        val isHost: Boolean? = null

        @field:Json(name = "roomType")
        val roomType: String? = null

        @field:Json(name = "sdkData")
        val sdkData: String? = null

        @field:Json(name = "expiredDateUnixTime")
        val expiredDateUnixTime: Long? = null

    }


}