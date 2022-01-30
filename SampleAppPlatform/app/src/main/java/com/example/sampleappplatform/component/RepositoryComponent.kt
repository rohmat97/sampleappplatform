package com.example.sampleappplatform.component

import com.example.sampleappplatform.datamodule.module.repository.repositoryModule
import com.example.sampleappplatform.datamodule.module.repository.repositoryPlatformModule
import org.koin.core.module.Module

/**
 * Created by Isnaeni on 06/08/2021.
 */

val repositoryComponent: List<Module> = listOf(
    repositoryPlatformModule,
    repositoryModule
)