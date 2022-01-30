package com.example.sampleappplatform.datamodule.repository.room

import com.example.sampleappplatform.datamodule.model.dto.room.CreateMeetingDto
import com.example.sampleappplatform.datamodule.model.dto.room.JoinMeetingDto
import com.example.sampleappplatform.datamodule.model.entities.request.room.CreateMeeting
import com.example.sampleappplatform.datamodule.model.entities.request.room.JoinMeeting
import com.example.sampleappplatform.datamodule.service.room.RoomService
import kotlinx.coroutines.Deferred
import retrofit2.Response

interface RoomRepository {

    fun sendCreateMeetingAsync(
        createMeeting: CreateMeeting
    ): Deferred<Response<CreateMeetingDto>>

    fun sendJoinMeetingAsync(
        joinMeeting: JoinMeeting
    ): Deferred<Response<JoinMeetingDto>>

}

class RoomRepositoryImpl(private val roomService: RoomService) : RoomRepository {

    override fun sendCreateMeetingAsync(
        createMeeting: CreateMeeting
    ) = roomService.sendCreateMeetingAsync(createMeeting)

    override fun sendJoinMeetingAsync(
        joinMeeting: JoinMeeting
    ) = roomService.sendJoinMeetingAsync(joinMeeting)

}