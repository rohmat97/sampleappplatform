package com.example.sampleappplatform.framework.core.view

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

/**
 * Created by Isnaeni on 05/08/2021.
 */

interface LifecycleView : BindingView, LifecycleOwner {

    fun <T> observeData(data: LiveData<T>, observer: Observer<T>) {
        data.observe(this, observer)
    }

    fun <T> observeData(data: LiveData<T>, onChanged: (T?) -> Unit) {
        observeData(data, Observer { onChanged(it) })
    }
}