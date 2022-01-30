package com.example.sampleappplatform.framework.core.owner

import com.example.sampleappplatform.framework.core.base.BaseViewModel

/**
 * Created by Isnaeni on 05/08/2021.
 */

interface ViewModelOwner<T : BaseViewModel> {
    val viewModel: T
}