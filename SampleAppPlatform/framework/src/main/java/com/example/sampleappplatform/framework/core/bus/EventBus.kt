package com.example.sampleappplatform.framework.core.bus

import androidx.lifecycle.LifecycleOwner

/**
 * Created by Isnaeni on 05/08/2021.
 *
 * EventBus
 * Library to communicate between android components (Activities, Fragments, Services, etc)
 * Utilize [androidx.lifecycle.LiveData] as Event Publisher (Observable) which aware
 * of [androidx.lifecycle.Lifecycle] of Subscriber (Observer)
 */
object EventBus {

    private val mPublisherMap: PublisherMap = PublisherMap()

    @Suppress("UNCHECKED_CAST")
    fun subscribe(lifecycle: LifecycleOwner, subscriber: EventSubscriber<out Any>, classOfT: Class<out Any>) {
        val publisher = mPublisherMap.getOrCreate(lifecycle)
        publisher.subscribe(lifecycle, Pair(classOfT, subscriber) as EventSubscriberPair)
    }

    fun send(event: Any) {
        for (publisher in mPublisherMap.values) {
            publisher.sendEvent(event)
        }
    }

    fun post(event: Any) {
        for (publisher in mPublisherMap.values) {
            publisher.postEvent(event)
        }
    }
}