package com.example.sampleappplatform.framework.util.manager

import android.content.Context
import android.content.SharedPreferences

/**
 * Created by Isnaeni on 05/08/2021.
 */
object PrefManager {
    private const val NAME = "PreferenceWhiteLabel"
    private const val MODE = Context.MODE_PRIVATE
    private lateinit var preferences: SharedPreferences

    private val default_language = Pair("default_language", "")
    private val language = Pair("language", "")
    private val token = Pair("token", "")
    private val temporary_token = Pair("temporary_token", "")
    private val store_date_token = Pair("store_date_token", null)
    private val time_expired_token = Pair("time_expired_token", 0L)
    private val special_user = Pair("special_user", false)
    private val app_id = Pair("app_id", "")
    private val app_key = Pair("app_key", "")
    private val access_token_expired = Pair("appId", 0)

    fun init(context: Context) {
        preferences = context.getSharedPreferences(
                NAME,
                MODE
        )
    }

    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = edit()
        operation(editor)
        editor.apply()
    }

    fun clearAllData() {
        preferences.edit()?.apply {
            remove(default_language.first)
            remove(language.first)
            remove(token.first)
           apply()
        }
    }

    var DEFAULT_LANGUAGE: String
        get() = preferences.getString(
                default_language.first, default_language.second
        )!!
        set(value) = preferences.edit {
            it.putString(default_language.first, value)
        }

    var LANGUAGE: String
        get() = preferences.getString(
                language.first,
                when {
                    DEFAULT_LANGUAGE.startsWith(
                            LanguageManager.LANGUAGE_KEY_INDONESIA
                    ) || DEFAULT_LANGUAGE.endsWith("_ID") -> LanguageManager.LANGUAGE_KEY_INDONESIA
                    else -> LanguageManager.LANGUAGE_KEY_ENGLISH
                }
        )!!
        set(value) = preferences.edit {
            it.putString(language.first, value)
        }

    var TOKEN: String
        get() = preferences.getString(
                token.first, token.second
        )!!
        set(value) = preferences.edit {
            it.putString(token.first, value.trim())
        }

    var TEMPORARY_TOKEN: String
        get() = preferences.getString(
                temporary_token.first, temporary_token.second
        )!!
        set(value) = preferences.edit {
            it.putString(temporary_token.first, value.trim())
        }

    var STORE_DATE_TOKEN: String
        get() = preferences.getString(
                store_date_token.first, store_date_token.second
        )!!
        set(value) = preferences.edit {
            it.putString(store_date_token.first, value)
        }

    var TIME_EXPIRED_TOKEN: Long
        get() = preferences.getLong(
                time_expired_token.first, time_expired_token.second
        )
        set(value) = preferences.edit {
            it.putLong(time_expired_token.first, value)
        }

    var SPECIAL_USER: Boolean
        get() = preferences.getBoolean(
            special_user.first, special_user.second
        )
        set(value) = preferences.edit {
            it.putBoolean(special_user.first, value)
        }

    var APP_ID: String
        get() = preferences.getString(
                app_id.first, app_id.second
        )!!
        set(value) = preferences.edit {
            it.putString(app_id.first, value)
        }

    var APP_KEY: String
        get() = preferences.getString(
                app_key.first, app_key.second
        )!!
        set(value) = preferences.edit {
            it.putString(app_key.first, value)
        }

    var ACCESS_TOKEN_EXPIRED: Int
        get() = preferences.getInt(
                access_token_expired.first, access_token_expired.second
        )
        set(value) = preferences.edit {
            it.putInt(access_token_expired.first, value)
        }
}