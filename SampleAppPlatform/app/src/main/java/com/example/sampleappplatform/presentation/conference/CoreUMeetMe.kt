package com.example.sampleappplatform.presentation.conference

import java.io.Serializable

class CoreUMeetMe : Serializable {
    var mirrorFullUrl: String? = null
    var coreFullUrl: String? = null
    var service: String? = null
    var room: String? = null
    var subject: String? = null
    var isHost: Boolean? = false
    var isJoin: Boolean? = false
    var isCreate: Boolean? = false
    var isPMR: Boolean? = false

    var latestActivity: String? = null
    var displayName: String? = null
    var dateRoomCreated: String? = null
    var timeRoomCreated: String? = null

    var meetingCode: String? = null
    var expiredIn: Long? = null

    var role: String? = null
    var linkOrCodeRoom: String? = null

    var serviceCode: String? = null
    var joinLinkOrCode: String? = null
    var roomType: String? = null
    var expiredDateUnixTime: Long? = null

    var secondId: Boolean? = null
    var hostName: String? = null
    var isSpecialUser: Boolean? = false
    var sdkData: String? = null

    var jid: String? = null
    var isLogin: Boolean? = false
}