package com.example.sampleappplatform.module.network

import com.ashokvarma.gander.GanderInterceptor
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.example.sampleappplatform.BuildConfig
import com.example.sampleappplatform.datamodule.service.auth.AuthService
import com.example.sampleappplatform.framework.core.common.Common
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by Isnaeni on 06/08/2021.
 */
val networkPlatformModule = module {

    single<Interceptor>(named("InterceptorUMeetMePlatform")) {
        HttpLoggingInterceptor().also { interceptor ->
            interceptor.level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
            else HttpLoggingInterceptor.Level.NONE
        }
    }

    // comment gander interceptor first before start the unit test of api
    factory(named("OkhttpclientUMeetMePlatform")) {
        OkHttpClient.Builder().apply {
            HttpLoggingInterceptor().also { interceptor ->
                interceptor.level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
                else HttpLoggingInterceptor.Level.NONE
                addInterceptor(interceptor)
            }
            addInterceptor(GanderInterceptor(androidContext()).showNotification(true))
            addInterceptor(
                Common.getInterceptorWithHeader(
                    "Content-Type",
                    BuildConfig.CONTENT_TYPE
                )
            )
            addInterceptor(
                Common.getInterceptorWithHeader(
                    "Authorization",
                    BuildConfig.AUTHORIZATION
                )
            )
            connectTimeout(30, TimeUnit.SECONDS)
            readTimeout(30, TimeUnit.SECONDS)
            writeTimeout(30, TimeUnit.SECONDS)
        }.build()
    }

    single<Retrofit>(named("RetrofitUMeetMePlatform")) {
        Retrofit.Builder().apply {
            client(get(named("OkhttpclientUMeetMePlatform")))
            baseUrl(BuildConfig.API_BASE_URL)
            addConverterFactory(MoshiConverterFactory.create())
            addCallAdapterFactory(CoroutineCallAdapterFactory())
        }.build()
    }

    factory { get<Retrofit>(named("RetrofitUMeetMePlatform")).create(AuthService::class.java) }
}