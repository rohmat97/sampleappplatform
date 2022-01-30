package com.example.sampleappplatform.component

import com.example.sampleappplatform.module.network.networkModule
import com.example.sampleappplatform.module.network.networkPlatformModule
import org.koin.core.module.Module

val networkComponent: List<Module> = listOf(
    networkPlatformModule,
    networkModule
)