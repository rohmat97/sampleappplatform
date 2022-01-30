package com.example.sampleappplatform.framework.extention

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes

/**
 * Created by Isnaeni on 05/08/2021.
 */

fun Context.showToast(@StringRes stringResource: Int) = showToast(getString(stringResource))

fun Context.showToast(message: String) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

fun Context.showLongToast(@StringRes stringResource: Int) = showLongToast(getString(stringResource))

fun Context.showLongToast(message: String) = Toast.makeText(this, message, Toast.LENGTH_LONG).show()