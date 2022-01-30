package com.example.sampleappplatform.framework.core.common

import android.content.Context
import com.example.framework.R
import okhttp3.Interceptor
import java.net.MalformedURLException
import java.net.URL
import java.util.HashMap
import java.util.regex.Pattern

/**
 * Created by Isnaeni on 06/08/2021.
 */
object Common {
    const val REGEX_ALPHA_NUMERIC_ONLY = "[^a-z A-Z0-9]"
    const val REGEX_ALPHA_NUMERIC_ONLY_NO_SPACE = "[^a-zA-Z0-9]"

    fun getInterceptorWithHeader(
        headerName: String,
        headerValue: String
    ): Interceptor {
        val header: HashMap<String, String> = HashMap()
        header[headerName] = headerValue
        return getInterceptorWithHeader(
            header
        )
    }

    fun buildURL(urlStr: String?): URL? {
        return try {
            URL(urlStr)
        } catch (e: MalformedURLException) {
            null
        }
    }

    fun isScreenNotTab(context: Context): Boolean {
        return context.resources.getBoolean(R.bool.portrait_only)
    }

    fun isNotSpecialChar(value: String, noSpace: Boolean? = true): Boolean {
        var isNotSpecialChar = true
        val pattern = Pattern.compile(
            if (noSpace!!) REGEX_ALPHA_NUMERIC_ONLY_NO_SPACE
            else REGEX_ALPHA_NUMERIC_ONLY,
            Pattern.CASE_INSENSITIVE
        )
        val isAnySpecialChar = pattern.matcher(value).find()
        if (isAnySpecialChar)
            isNotSpecialChar = false

        return isNotSpecialChar
    }

    private fun getInterceptorWithHeader(headers: Map<*, *>): Interceptor {
        return Interceptor { chain ->
            val original = chain.request()
            val builder = original.newBuilder()
            val var5: Iterator<*> = headers.entries.iterator()
            while (var5.hasNext()) {
                val var4 =
                    var5.next() as Map.Entry<*, *>
                val key = var4.key as String
                val value = var4.value as String
                builder.addHeader(key, value)
            }
            builder.method(original.method(), original.body())
            chain.proceed(builder.build())
        }
    }
}