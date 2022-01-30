package com.example.sampleappplatform.datamodule.module.repository

import com.example.sampleappplatform.datamodule.repository.auth.AuthRepository
import com.example.sampleappplatform.datamodule.repository.auth.AuthRepositoryImpl
import org.koin.dsl.module

val repositoryPlatformModule = module {

    single<AuthRepository> { AuthRepositoryImpl(authService = get()) }

}