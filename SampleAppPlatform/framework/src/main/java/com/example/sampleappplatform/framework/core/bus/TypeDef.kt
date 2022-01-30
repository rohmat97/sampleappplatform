package com.example.sampleappplatform.framework.core.bus

import androidx.lifecycle.LifecycleOwner

/**
 * Created by Isnaeni on 05/08/2021.
 */

internal typealias EventSubscriberPair = Pair<Class<out Any>, EventSubscriber<in Any>>

internal typealias LifecycleUnsubscribeCallback = ((owner: LifecycleOwner) -> Unit)
internal typealias PublisherMap = HashMap<LifecycleOwner, BusPublisher>

internal fun PublisherMap.getOrCreate(owner: LifecycleOwner): BusPublisher {
    return get(owner) ?: {
        val publisher = BusPublisher()
        publisher.onUnsubscribe = { remove(it) }
        put(owner, publisher)
        publisher
    }.invoke()
}