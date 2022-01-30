package com.example.sampleappplatform.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.sampleappplatform.datamodule.model.dto.auth.GenerateTokenDto
import com.example.sampleappplatform.datamodule.model.dto.auth.TemporaryTokenDto
import com.example.sampleappplatform.datamodule.model.dto.core.BaseApiDto
import com.example.sampleappplatform.datamodule.model.dto.room.CreateMeetingDto
import com.example.sampleappplatform.datamodule.model.dto.room.JoinMeetingDto
import com.example.sampleappplatform.datamodule.model.entities.request.auth.GenerateToken
import com.example.sampleappplatform.datamodule.model.entities.request.auth.TemporaryToken
import com.example.sampleappplatform.datamodule.model.entities.request.room.CreateMeeting
import com.example.sampleappplatform.datamodule.model.entities.request.room.JoinMeeting
import com.example.sampleappplatform.datamodule.repository.auth.AuthRepository
import com.example.sampleappplatform.datamodule.repository.room.RoomRepository
import com.example.sampleappplatform.framework.core.base.BaseViewModel
import com.example.sampleappplatform.framework.core.common.NetworkState
import com.google.gson.Gson
import kotlinx.coroutines.launch
import java.lang.Exception

class MainViewModel(
    private val authRepository: AuthRepository,
    private val roomRepository: RoomRepository
) : BaseViewModel() {

    var token = MutableLiveData<GenerateTokenDto>()
    var temporaryToken = MutableLiveData<TemporaryTokenDto>()
    var createMeetingData = MutableLiveData<CreateMeetingDto>()
    var joinMeetingData = MutableLiveData<JoinMeetingDto>()
    val showLoadingView = MutableLiveData(false)

    fun sendGenerateJWTToken(data: GenerateToken) {
        networkState.value = NetworkState.LOADING
        viewModelScope.launch {
            val request = authRepository.sendGenerateJWTTokenAsync(
                data
            )
            try {
                val response = request.await()
                if (response.isSuccessful) {
                    networkState.value = NetworkState.LOADED
                    response.body()?.let { token.value = it }
                } else {
                    val body = response.errorBody()
                    val baseApiDto: BaseApiDto = Gson().fromJson(
                        body?.charStream(),
                        BaseApiDto::class.java
                    )
                    if (baseApiDto.message != null && !baseApiDto.message?.trim()?.isEmpty()!!) {
                        networkState.value = NetworkState.error(baseApiDto.message)
                    } else {
                        networkState.value = NetworkState.unknownError()
                    }
                }
            } catch (e: Exception) {
                networkState.value = NetworkState.Companion.error(e)
            }
        }
    }

    fun sendGenerateTemporaryToken(data: TemporaryToken) {
        networkState.value = NetworkState.LOADING
        viewModelScope.launch {
            val request = authRepository.sendGenerateTemporaryTokenAsync(
                    data
            )
            try {
                val response = request.await()
                if (response.isSuccessful) {
                    networkState.value = NetworkState.LOADED
                    response.body()?.let { temporaryToken.value = it }
                } else {
                    val body = response.errorBody()
                    val baseApiDto: BaseApiDto = Gson().fromJson(
                            body?.charStream(),
                            BaseApiDto::class.java
                    )
                    if (baseApiDto.message != null && !baseApiDto.message?.trim()?.isEmpty()!!) {
                        networkState.value = NetworkState.error(baseApiDto.message)
                    } else {
                        networkState.value = NetworkState.unknownError()
                    }
                }
            } catch (e: Exception) {
                networkState.value = NetworkState.Companion.error(e)
            }
        }
    }

    fun getCreateMeeting() = createMeetingData

    fun sendCreateMeetingFromApi(createMeeting: CreateMeeting) {
        networkState.value = NetworkState.LOADING
        viewModelScope.launch {
            val request = roomRepository.sendCreateMeetingAsync(
                createMeeting
            )
            try {
                val response = request.await()
                if (response.isSuccessful) {
                    networkState.value = NetworkState.LOADED
                    createMeetingData.value = response.body()
                } else {
                    val body = response.errorBody()
                    val baseApiDto: BaseApiDto = Gson().fromJson(
                        body?.charStream(),
                        BaseApiDto::class.java
                    )
                    if (baseApiDto.message != null && !baseApiDto.message?.trim()?.isEmpty()!!) {
                        networkState.value = NetworkState.error(baseApiDto.message)
                    } else {
                        networkState.value = NetworkState.unknownError()
                    }
                }
            } catch (e: Exception) {
                networkState.value = NetworkState.Companion.error(e)
            }
        }
    }

    fun getJoinMeeting() = joinMeetingData

    fun sendJoinMeetingFromApi(joinMeeting: JoinMeeting) {
        networkState.value = NetworkState.LOADING
        viewModelScope.launch {
            val request = roomRepository.sendJoinMeetingAsync(
                joinMeeting
            )
            try {
                val response = request.await()
                if (response.isSuccessful) {
                    networkState.value = NetworkState.LOADED
                    joinMeetingData.value = response.body()
                } else {
                    val body = response.errorBody()
                    val baseApiDto: BaseApiDto = Gson().fromJson(
                        body?.charStream(),
                        BaseApiDto::class.java
                    )

                    if (baseApiDto.message != null && !baseApiDto.message?.trim()?.isEmpty()!!) {
                        networkState.value = NetworkState.error(baseApiDto.message)
                    } else {
                        networkState.value = NetworkState.unknownError()
                    }
                }
            } catch (e: Exception) {
                networkState.value = NetworkState.error(e)
            }
        }
    }

    fun showLoading() {
        showLoadingView.value = true
    }

    fun hideLoading() {
        showLoadingView.value = false
    }

}