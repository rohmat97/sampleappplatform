package com.example.sampleappplatform.presentation

import android.view.View
import com.example.sampleappplatform.framework.core.view.LifecycleView
import com.example.sampleappplatform.framework.design.LoadingView

interface MainView : LifecycleView {
    var retryListener: LoadingView.OnRetryListener
    fun onClickGenerateToken(view: View)
    fun onClickSubmit(view: View)
}