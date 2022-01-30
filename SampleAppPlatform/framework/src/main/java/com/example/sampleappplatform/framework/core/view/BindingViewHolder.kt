package com.example.sampleappplatform.framework.core.view

import android.view.View

/**
 * Created by Isnaeni on 05/08/2021.
 */

interface BindingViewHolder<in T> : BindingView {

    fun onItemClick(view: View, item: T)

}