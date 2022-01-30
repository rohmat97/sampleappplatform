package com.example.sampleappplatform

import android.content.Context
import android.util.Log
import androidx.multidex.MultiDex
import com.ashokvarma.gander.Gander
import com.ashokvarma.gander.imdb.GanderIMDB
import com.example.framework.BuildConfig
import com.example.sampleappplatform.component.networkComponent
import com.example.sampleappplatform.component.repositoryComponent
import com.example.sampleappplatform.component.viewModelComponent
import com.example.sampleappplatform.framework.core.base.BaseMultidexApplication
import com.example.sampleappplatform.framework.util.manager.LanguageManager
import com.example.sampleappplatform.framework.util.manager.PrefManager
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber

class BaseApplication  : BaseMultidexApplication() {

    companion object {
        var appContext: Context? = null
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()
        appContext = this

        setupPreference()
        setupGander()
        setupKoin()
        setDefaultLanguage()
        setupTimber()
    }

    private fun setupGander() {
        Gander.setGanderStorage(GanderIMDB.getInstance())
    }

    private fun setupPreference() {
        PrefManager.init(this)
    }

    private fun setupKoin() {
        // start Koin context
        startKoin {
            androidContext(this@BaseApplication)
            androidLogger(Level.ERROR)
            modules(networkComponent)
            modules(repositoryComponent)
            modules(viewModelComponent)
        }
    }

    private fun setDefaultLanguage() {
        PrefManager.DEFAULT_LANGUAGE = LanguageManager.getLocale(
            appContext?.resources!!
        ).toString()
    }

    private fun setupTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(ReleaseTree())
        }
    }

    inner class ReleaseTree : Timber.Tree() {
        override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
            if (priority == Log.VERBOSE || priority == Log.DEBUG) {
                return
            }
        }

    }
}