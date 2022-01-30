package com.example.sampleappplatform.datamodule.repository.auth

import com.example.sampleappplatform.datamodule.model.dto.auth.GenerateTokenDto
import com.example.sampleappplatform.datamodule.model.dto.auth.TemporaryTokenDto
import com.example.sampleappplatform.datamodule.model.entities.request.auth.GenerateToken
import com.example.sampleappplatform.datamodule.model.entities.request.auth.TemporaryToken
import com.example.sampleappplatform.datamodule.service.auth.AuthService
import kotlinx.coroutines.Deferred
import retrofit2.Response

interface AuthRepository {

    fun sendGenerateJWTTokenAsync(
            request: GenerateToken
    ):Deferred<Response<GenerateTokenDto>>

    fun sendGenerateTemporaryTokenAsync(
            request: TemporaryToken
    ):Deferred<Response<TemporaryTokenDto>>

}

class AuthRepositoryImpl(private val authService: AuthService) : AuthRepository {

    override fun sendGenerateJWTTokenAsync(
            request: GenerateToken
    ) = authService.sendGenerateJWTTokenAsync(request)

    override fun sendGenerateTemporaryTokenAsync(
            request: TemporaryToken
    ) = authService.sendGenerateTemporaryTokenAsync(request)

}