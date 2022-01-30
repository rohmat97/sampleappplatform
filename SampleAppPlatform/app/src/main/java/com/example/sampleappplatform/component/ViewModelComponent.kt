package com.example.sampleappplatform.component

import com.example.sampleappplatform.module.viewmodel.viewModelModule
import org.koin.core.module.Module

/**
 * Created by Isnaeni on 06/08/2021.
 */

val viewModelComponent: List<Module> = listOf(
    viewModelModule
)