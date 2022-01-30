package com.example.sampleappplatform.framework.core.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sampleappplatform.framework.core.common.NetworkState

/**
 * Created by Isnaeni on 05/08/2021.
 */

abstract class BaseViewModel : ViewModel() {
    var initialState: MutableLiveData<NetworkState> = MutableLiveData()
    var networkState: MutableLiveData<NetworkState> = MutableLiveData()

    fun getInitialState(): LiveData<NetworkState> {
        return initialState
    }

    fun getNetworkState(): LiveData<NetworkState> {
        return networkState
    }
}