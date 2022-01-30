package com.example.sampleappplatform.datamodule.service.room

import com.example.sampleappplatform.datamodule.model.dto.room.CreateMeetingDto
import com.example.sampleappplatform.datamodule.model.dto.room.JoinMeetingDto
import com.example.sampleappplatform.datamodule.model.entities.request.room.CreateMeeting
import com.example.sampleappplatform.datamodule.model.entities.request.room.JoinMeeting
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RoomService {

    @POST("api/room-management/create-meeting")
    fun sendCreateMeetingAsync(
        @Body createMeeting: CreateMeeting
    ): Deferred<Response<CreateMeetingDto>>

    @POST("api/room-management/join-meeting")
    fun sendJoinMeetingAsync(
        @Body joinMeeting: JoinMeeting
    ): Deferred<Response<JoinMeetingDto>>

}