package com.example.sampleappplatform.framework.core.bus

/**
 * Created by Isnaeni on 05/08/2021.
 *
 * EventSubscriber
 * Listener to receive events published by [EventBus]
 */

interface EventSubscriber<T> {

    fun onEvent(event: T)
}