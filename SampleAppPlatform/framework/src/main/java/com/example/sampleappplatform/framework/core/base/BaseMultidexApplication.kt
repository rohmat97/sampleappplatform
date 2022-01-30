package com.example.sampleappplatform.framework.core.base

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.multidex.MultiDexApplication

/**
 * Created by Isnaeni on 05/08/2021.
 *
 *
 * BaseMultidexApplication
 * Base class for Multidex Application
 */

abstract class BaseMultidexApplication :
    MultiDexApplication(),
    LifecycleOwner {

    internal val mLifecycleRegistry = LifecycleRegistry(this)

    init {
        mLifecycleRegistry.currentState = Lifecycle.State.INITIALIZED
    }

    override fun getLifecycle(): LifecycleRegistry {
        return mLifecycleRegistry
    }

    override fun onCreate() {
        super.onCreate()
        mLifecycleRegistry.currentState = Lifecycle.State.STARTED
    }
}