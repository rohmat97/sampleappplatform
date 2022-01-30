package com.example.sampleappplatform.framework.core.base

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.framework.BR
import com.example.sampleappplatform.framework.core.annotation.ViewLayout
import com.example.sampleappplatform.framework.core.bus.EventBus
import com.example.sampleappplatform.framework.core.bus.EventSubscriber
import com.example.sampleappplatform.framework.core.owner.ViewDataBindingOwner
import com.example.sampleappplatform.framework.core.owner.ViewModelOwner
import com.example.sampleappplatform.framework.core.view.BindingView

/**
 * Created by Isnaeni on 05/08/2021.
 */

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setLayoutIfDefined()
    }

    private fun setLayoutIfDefined() {
        val layoutResId = getViewLayoutResId()
        if (layoutResId == View.NO_ID) return

        if (this is ViewDataBindingOwner<*>) {
            setContentViewBinding(this, layoutResId)
            if (this is ViewModelOwner<*>) {
                binding.setVariable(BR.vm, this.viewModel)
            }
            if (this is BindingView) {
                binding.setVariable(BR.view, this)
            }
            binding.lifecycleOwner = this
        } else {
            setContentView(layoutResId)
        }
    }

    protected open fun getViewLayoutResId(): Int {
        val layout = javaClass.annotations.find { it is ViewLayout } as? ViewLayout
        return layout?.value ?: View.NO_ID
    }

    protected open fun onToolBarBackButtonPressed() {
        finish()
    }

    protected fun subscribeEvent(observer: EventSubscriber<out Any>, classOfT: Class<out Any>) {
        EventBus.subscribe(this, observer, classOfT)
    }
}