package com.example.sampleappplatform.module.viewmodel

import com.example.sampleappplatform.presentation.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Created by Isnaeni on 06/08/2021.
 */

val viewModelModule = module {

    viewModel {
        MainViewModel(
            authRepository = get(),
            roomRepository = get()
        )
    }

}