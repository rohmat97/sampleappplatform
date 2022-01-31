package com.example.sampleappplatform.presentation.meetcall

import com.example.sampleappplatform.BuildConfig
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import com.example.sampleappplatform.framework.core.common.Common
import com.example.sampleappplatform.framework.util.manager.PrefManager
import com.example.sampleappplatform.presentation.conference.CoreUMeetMe
import org.jitsi.meet.sdk.*
import org.json.JSONObject

class MeetingCallActivity: JitsiMeetActivity(), JitsiMeetViewListener {

    companion object {

        var active = false

        fun startThisActivity(
                context: Context,
                isVideoMuted: Boolean?,
                isAudioMuted: Boolean?,
                coreUMeetMe: CoreUMeetMe
        ) {
            context.startActivity(
                    Intent(context, MeetingCallActivity::class.java).apply {
                        putExtra("ARG_DATA_CONFERENCE", coreUMeetMe)
                        putExtras(
                                Bundle().apply {
                                    putBoolean("isVideoMuted", isVideoMuted!!)
                                    putBoolean("isAudioMuted", isAudioMuted!!)
                                }
                        )
                    }
            )
        }

    }

    private var coreUMeetMe = CoreUMeetMe()

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        active = true
        setScreenOrientation()
    }

    private fun setScreenOrientation() {
        requestedOrientation = if (Common.isScreenNotTab(this)) {
            ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        } else {
            ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        active = false
    }

    override fun initialize() {
        intent.extras?.apply {
            intent.getSerializableExtra("ARG_DATA_CONFERENCE")?.let { data ->
                (data as CoreUMeetMe).also { dataConference ->
                    coreUMeetMe = dataConference
                }
            }
            getBoolean("isVideoMuted").let {
                false
            }
            getBoolean("isAudioMuted").let {
                false
            }

            val credentials = JSONObject()
            credentials.put("base_URL", BuildConfig.API_BASE_URL_CONF)
            credentials.put("AccessToken", "testtoken")

            val validLink = coreUMeetMe.mirrorFullUrl?.replace("https://", "")

            val detailMeetingData = JSONObject()
            detailMeetingData.put("meetingCode", coreUMeetMe.meetingCode)
            detailMeetingData.put("meetingLink", validLink?.replace("conf?room=", ""))
            detailMeetingData.put("meetingCoreLink", coreUMeetMe.coreFullUrl)
            detailMeetingData.put("meetingTitle", coreUMeetMe.subject)
            detailMeetingData.put("participant", coreUMeetMe.displayName)
            detailMeetingData.put("serviceCode", coreUMeetMe.serviceCode)
            detailMeetingData.put("createdDate", coreUMeetMe.dateRoomCreated)
            detailMeetingData.put("isHost", coreUMeetMe.isHost)
            detailMeetingData.put("roomType", coreUMeetMe.roomType)
            detailMeetingData.put("expiredDateUnixTime", coreUMeetMe.expiredDateUnixTime)
            detailMeetingData.put("role", coreUMeetMe.role)
            detailMeetingData.put("linkOrCodeRoom", coreUMeetMe.linkOrCodeRoom)
            detailMeetingData.put("baseURL", BuildConfig.API_BASE_URL_CONF)

            val defaultOptions = JitsiMeetConferenceOptions.Builder().apply {
                setServerURL(Common.buildURL(coreUMeetMe.coreFullUrl))
                setWelcomePageEnabled(false)
                setFeatureFlag("call-integration.enabled", false)
                setFeatureFlag("lang", PrefManager.LANGUAGE)
                setFeatureFlag("meetingCode", coreUMeetMe.meetingCode)
                setFeatureFlag("phoneNumber", null)
                setFeatureFlag("userId", null)
                setFeatureFlag("infoURL", validLink?.replace("https://", ""))
                setFeatureFlag("expiredIn", coreUMeetMe.expiredIn.toString())
                setFeatureFlag("service", coreUMeetMe.serviceCode)
                setFeatureFlag("baseURL", BuildConfig.API_BASE_URL_CONF)
                setFeatureFlag("isHost", coreUMeetMe.isHost!!)
                setFeatureFlag("isPMR", false)
                setFeatureFlag("secondID", false)
                setFeatureFlag("hostName", "UMEETME_SIMULATOR")
                setFeatureFlag("isJoin", false)
                setFeatureFlag("isLogin", false)
                setFeatureFlag("credentials", credentials.toString())
                setFeatureFlag("detailMeetingData", detailMeetingData.toString())
                setFeatureFlag("sdkData", coreUMeetMe.sdkData)
                //Feature Jitsi SDK
                setFeatureFlag("Minimize",true)
                setFeatureFlag("HeaderCamera",true)
                setFeatureFlag("MeetingInfo",true)
                setFeatureFlag("Mic",true)
                setFeatureFlag("Video",true)
                setFeatureFlag("Chat",true)
                setFeatureFlag("ListParticipant",true)
                setFeatureFlag("EndCall",true)
                setFeatureFlag("RaiseHand",true)
                setFeatureFlag("ButtonMore",true)
                setFeatureFlag("Help",true)
                setFeatureFlag("Youtube",true)
                setFeatureFlag("TileView",true)
                setFeatureFlag("ShareScreen",true)
                setFeatureFlag("Security",true)
                setFeatureFlag("LowBandwidthMode",true)
                setFeatureFlag("Sound",true)
                setFeatureFlag("ShareDocument",false)
                setFeatureFlag("WhiteBoard",true)
            }.build()
            JitsiMeet.setDefaultConferenceOptions(defaultOptions)
            val userInfo = JitsiMeetUserInfo()
            userInfo.displayName = coreUMeetMe.displayName
            val options = JitsiMeetConferenceOptions.Builder().apply {
                setServerURL(Common.buildURL(coreUMeetMe.coreFullUrl))
                setRoom(coreUMeetMe.room)
                setVideoMuted(false)
                setAudioMuted(false)
                setUserInfo(userInfo)
                setSubject(coreUMeetMe.subject)
                setToken(if (PrefManager.TOKEN.isNotEmpty()) PrefManager.TOKEN else PrefManager.TEMPORARY_TOKEN)
            }.build()
            this@MeetingCallActivity.jitsiView.listener = this@MeetingCallActivity
            this@MeetingCallActivity.join(options)
        }
    }

    override fun onConferenceJoined(p0: MutableMap<String, Any>?) {
//        TODO("Not yet implemented")
    }

    override fun onConferenceTerminated(map: Map<String, Any>) {
        this.jitsiView?.apply {
            dispose()
            leave()
            PrefManager.clearAllData()
            finish()
        }
    }

    override fun onConferenceWillJoin(p0: MutableMap<String, Any>?) {
//        TODO("Not yet implemented")
    }
}