package com.example.sampleappplatform.framework.core.bus

import androidx.lifecycle.Observer

/**
 * Created by Isnaeni on 05/08/2021.
 *
 * BusSubscriber
 * Internal class to bridge between [EventBus] and [EventSubscriber]
 */

class BusSubscriber : Observer<Any> {

    private val eventSubscribers = ArrayList<EventSubscriberPair>()

    override fun onChanged(t: Any?) {
        for ((subscriberType, subscriber) in eventSubscribers) {
            if (subscriberType.isInstance(t) && t != null) {
                subscriber.onEvent(t)
            }
        }
    }

    fun add(item: EventSubscriberPair) {
        eventSubscribers.add(item)
    }

    fun clear() {
        eventSubscribers.clear()
    }
}