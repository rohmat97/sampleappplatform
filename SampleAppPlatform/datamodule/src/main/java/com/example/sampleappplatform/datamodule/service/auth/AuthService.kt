package com.example.sampleappplatform.datamodule.service.auth

import com.example.sampleappplatform.datamodule.model.dto.auth.GenerateTokenDto
import com.example.sampleappplatform.datamodule.model.dto.auth.TemporaryTokenDto
import com.example.sampleappplatform.datamodule.model.entities.request.auth.GenerateToken
import com.example.sampleappplatform.datamodule.model.entities.request.auth.TemporaryToken
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

    @POST("platform/auth/login/app")
    fun sendGenerateJWTTokenAsync(
        @Body request: GenerateToken?
    ): Deferred<Response<GenerateTokenDto>>

    @POST("platform/auth/temporary-token")
    fun sendGenerateTemporaryTokenAsync(
        @Body request: TemporaryToken?
    ): Deferred<Response<TemporaryTokenDto>>

}