package com.example.sampleappplatform.presentation.conference

import android.content.Context
import android.net.Uri
import android.webkit.URLUtil
import com.example.sampleappplatform.R
import com.example.sampleappplatform.framework.core.common.Common
import java.net.URL

class MirrorUMeetMeManager(private val ctx: Context) {
    fun isDeeplink(data: Uri?) = data != null

    fun isOnTheFlyMeeting(coreUMeetMe: CoreUMeetMe) = coreUMeetMe.isCreate

    fun isUpcomingMeeting(coreUMeetMe: CoreUMeetMe): Boolean {
        return if (coreUMeetMe.room?.length == 10) {
            coreUMeetMe.room?.matches("-?\\d+(\\.\\d+)?".toRegex())!!
        } else {
            false
        }
    }

    fun isUpcomingMeeting(value: String): Boolean {
        return if (value.length == 10) {
            value.matches("-?\\d+(\\.\\d+)?".toRegex())
        } else {
            false
        }
    }

    fun isJoinMeeting(coreUMeetMe: CoreUMeetMe) = coreUMeetMe.isJoin

    fun isMirrorURLCorrect(urlMirror: URL?) = isHostExist(urlMirror?.host) &&
            isPathExist(urlMirror?.path) &&
            isQueryExist(urlMirror?.query) &&
            isCorrectHost(urlMirror?.host!!) &&
            isCorrectPath(urlMirror.path) &&
            isCorrectQuery(urlMirror.query)

    fun isHostExist(host: String?) = host != null && host.isNotEmpty()

    fun isPathExist(path: String?) = path != null && path.isNotEmpty() &&
            path.split("/").size == 2 &&
            path.split("/")[1].isNotEmpty()

    fun isQueryExist(query: String?) = query != null && query.isNotEmpty() &&
            query.split("=").size == 2 &&
            query.split("=")[0].equals("room", true) &&
            query.split("=")[1].isNotEmpty()

    fun isCorrectHost(host: String): Boolean {
        return host.equals(
            ctx.getString(R.string.mirror_domain_umeetme_id), true
        )
    }

    fun isCorrectPath(path: String) = path.replace("/", "").equals("conf", true) ||
            path.replace("/", "").equals("edu", true) ||
            path.replace("/", "").equals("vip", true)

    fun isCorrectQuery(query: String) =
        if (query.split("=")[1].endsWith(".conf") && query.split("=")[1].length > 5 ||
            query.split("=")[1].endsWith(".edu") && query.split("=")[1].length > 4 ||
            query.split("=")[1].endsWith(".vip") && query.split("=")[1].length > 4
        ) {
            Common.isNotSpecialChar(query.split("=")[1].split(".")[0])
        } else Common.isNotSpecialChar(query.split("=")[1])

    fun getPath(fullUrl: String): String {
        return if (URLUtil.isValidUrl(fullUrl) && URLUtil.isHttpsUrl(fullUrl)) {
            val url = Common.buildURL(fullUrl)
            if (isHostExist(url?.host) &&
                isPathExist(url?.path) &&
                isQueryExist(url?.query) &&
                isCorrectHost(url?.host!!) &&
                isCorrectPath(url.path!!) &&
                isCorrectQuery(url.query!!)
            ) {
                url.path.replace("/", "")
            } else {
                "ERROR_GET_PATH[URL_ERROR]"
            }
        } else {
            "ERROR_GET_PATH[URL_NOT_VALID]"
        }
    }

    fun getQuery(fullUrl: String): String {
        return if (URLUtil.isValidUrl(fullUrl) && URLUtil.isHttpsUrl(fullUrl)) {
            val url = Common.buildURL(fullUrl)
            if (isHostExist(url?.host) &&
                isPathExist(url?.path) &&
                isQueryExist(url?.query) &&
                isCorrectHost(url?.host!!) &&
                isCorrectPath(url.path!!) &&
                isCorrectQuery(url.query!!)
            ) {
                url.query.split("=")[1]
            } else {
                "ERROR_GET_QUERY[URL_ERROR]"
            }
        } else {
            "ERROR_GET_QUERY[URL_NOT_VALID]"
        }
    }
}
