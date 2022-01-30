package com.example.sampleappplatform.framework.util.manager

import android.annotation.TargetApi
import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import java.util.*

/**
 * Created by Isnaeni on 05/08/2021.
 */
object LanguageManager {

    const val LANGUAGE_KEY_ENGLISH = "en"

    const val LANGUAGE_KEY_INDONESIA = "in"

    const val REQUEST_LANGUAGE_ENGLISH = "english"

    const val REQUEST_LANGUAGE_INDONESIA = "indonesia"

    fun setNewLocale(
            mContext: Context,
            mLocaleKey: String): Context {
        PrefManager.LANGUAGE = mLocaleKey
        return updateResources(
                mContext,
                mLocaleKey
        )
    }

    /** update resource
     * @param context
     * @param language
     * @return
     */
    private fun updateResources(
            context: Context,
            language: String?): Context {
        val locale = Locale(language!!)
        Locale.setDefault(locale)
        val res = context.resources
        val config = Configuration(res.configuration)
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            setSystemLocale(config, locale)
            context = context.createConfigurationContext(config)
        } else {*/
        setSystemLocaleLegacy(
                config,
                locale
        )
        res.updateConfiguration(config, res.displayMetrics)
        //        }
        return context
    }

    private fun setSystemLocaleLegacy(
            config: Configuration,
            locale: Locale?) {
        config.locale = locale
    }

    @TargetApi(Build.VERSION_CODES.N)
    private fun setSystemLocale(
            config: Configuration,
            locale: Locale?) {
        config.setLocale(locale)
    }

    /** get current locale
     * @param res
     * @return
     */
    fun getLocale(res: Resources): Locale {
        val config = res.configuration
        return if (Build.VERSION.SDK_INT >= 24) config.locales[0] else config.locale
    }

    fun getLanguageToRequest() = if (PrefManager.LANGUAGE == LANGUAGE_KEY_INDONESIA) {
        REQUEST_LANGUAGE_INDONESIA
    } else {
        REQUEST_LANGUAGE_ENGLISH
    }
}