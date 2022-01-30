package com.example.sampleappplatform.framework.core.bus

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

/**
 * Created by Isnaeni on 05/08/2021.
 *
 * BusPublisher
 * Internal class to publish event from [EventBus] to [BusSubscriber]
 */

class BusPublisher : LiveData<Any>() {

    private val mBusSubscriber = BusSubscriber()
    internal var onUnsubscribe: LifecycleUnsubscribeCallback? = null

    fun sendEvent(event: Any) {
        value = event
    }

    fun postEvent(event: Any) {
        postValue(event)
    }

    override fun removeObserver(observer: Observer<in Any>) {
        super.removeObserver(observer)
        if (observer is BusSubscriber) {
            observer.clear()
        }
    }

    override fun removeObservers(owner: LifecycleOwner) {
        super.removeObservers(owner)
        onUnsubscribe?.invoke(owner)
    }

    fun subscribe(lifecycle: LifecycleOwner, eventSubscriber: EventSubscriberPair) {
        mBusSubscriber.add(eventSubscriber)
        observe(lifecycle, mBusSubscriber)
    }
}