package com.example.sampleappplatform.presentation

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.RadioButton
import com.example.sampleappplatform.R
import com.example.sampleappplatform.databinding.ActivityMainBinding
import com.example.sampleappplatform.datamodule.model.entities.request.auth.GenerateToken
import com.example.sampleappplatform.datamodule.model.entities.request.auth.TemporaryToken
import com.example.sampleappplatform.datamodule.model.entities.request.room.CreateMeeting
import com.example.sampleappplatform.datamodule.model.entities.request.room.JoinMeeting
import com.example.sampleappplatform.framework.core.base.BaseActivity
import com.example.sampleappplatform.framework.core.common.NetworkState
import com.example.sampleappplatform.framework.core.owner.ViewDataBindingOwner
import com.example.sampleappplatform.framework.core.owner.ViewModelOwner
import com.example.sampleappplatform.framework.design.LoadingView
import com.example.sampleappplatform.framework.extention.showToast
import com.example.sampleappplatform.framework.util.manager.PrefManager
import com.example.sampleappplatform.presentation.conference.CoreUMeetMe
import com.example.sampleappplatform.presentation.conference.MirrorUMeetMeManager
import com.example.sampleappplatform.presentation.meetcall.MeetingCallActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : BaseActivity(),
    MainView,
    ViewModelOwner<MainViewModel>,
    ViewDataBindingOwner<ActivityMainBinding> {

    override fun getViewLayoutResId() = R.layout.activity_main

    override lateinit var binding: ActivityMainBinding
    override val viewModel: MainViewModel by viewModel()

    private lateinit var currentDate: String
    private lateinit var language: String
    private lateinit var createOrJoin: String
    private var meetingCodeLink: String? = null

    private var coreUMeetMe = CoreUMeetMe()
    private var isCreateMeeting: Boolean? = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setTokenGenerator()
        observeInitialState()
        observeNetworkState()
        observeGeneratedToken()
        observeTemporaryToken()
        observeCreateMeeting()
        observeJoinMeeting()

        textInput_title.hint = getString(R.string.fn_title)


        textInput_title.addTextChangedListener(textWatcher)

    }

    private val textWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }
    }


    private fun setTokenGenerator() {
        textInput_app_id.setText(getString(R.string.fv_app_id))
        textInput_app_key.setText(getString(R.string.fv_app_key))
        textInput_access_token_expired.setText(getString(R.string.fv_access_token_expired))
    }

    override var retryListener: LoadingView.OnRetryListener = object : LoadingView.OnRetryListener {
        override fun onRetry() {

        }
    }

    private fun observeInitialState() {
        observeData(viewModel.getInitialState()) { networkState ->
            networkState?.let {
                when (it) {
                    NetworkState.LOADING -> {
                        viewModel.showLoading()
                    }
                    NetworkState.LOADED -> {
                        viewModel.hideLoading()
                    }
                    NetworkState.UNKNOWN -> {
                        viewModel.showLoading()
                        binding.loadingView.showError(
                            getString(R.string.me_title_oops),
                            getString(R.string.me_unknown)
                        )
                    }
                    else -> {
                        it.message?.let { message ->
                            viewModel.showLoading()
                            binding.loadingView.showError(
                                getString(R.string.me_title_oops),
                                message
                            )
                        }
                        it.exception?.let { e ->
                            viewModel.showLoading()
                            binding.loadingView.showError(e)
                        }
                    }
                }
            }
        }
    }

    private fun observeNetworkState() {
        observeData(viewModel.getNetworkState()) { networkState ->
            networkState?.let {
                when (it) {
                    NetworkState.LOADING -> {
                        viewModel.showLoading()
                    }
                    NetworkState.LOADED -> {
                        viewModel.hideLoading()
                    }
                    NetworkState.UNKNOWN -> {
                        viewModel.showLoading()
                        binding.loadingView.showError(
                            getString(R.string.me_title_oops),
                            getString(R.string.me_unknown)
                        )
                    }
                    else -> {
                        it.message?.let { message ->
                            viewModel.hideLoading()
                            showToast(message)
                        }
                        it.exception?.let { e ->
                            viewModel.showLoading()
                            binding.loadingView.showError(e)
                        }
                    }
                }
            }
        }
    }

    private val isDataValid: Boolean
        get() {
            clearError()
            var isValid = true
            val appId = binding.textInputAppId.text.toString()
            val appKey = binding.textInputAppKey.text.toString()
            val accessTokenExpired = binding.textInputAccessTokenExpired.text.toString()

            if (appId.isEmpty()) {
                binding.textLayoutAppId.apply {
                    error = getString(R.string.fe_required)
                    requestFocus()
                }
                isValid = false
            }

            if (appKey.isEmpty()) {
                binding.textLayoutAppKey.apply {
                    error = getString(R.string.fe_required)
                    requestFocus()
                }
                isValid = false
            }

            if (accessTokenExpired.isEmpty()) {
                binding.textLayoutAccessTokenExpired.apply {
                    error = getString(R.string.fe_required)
                    requestFocus()
                }
                isValid = false
            }

            return isValid
        }

    private val isTitleValid: Boolean
        get() {
            clearError()
            var isValid = true
            val title = binding.textInputTitle.text.toString()

            if (title.isEmpty()) {
                binding.textLayoutAppId.apply {
                    error = getString(R.string.fe_required)
                    requestFocus()
                }
                isValid = false
            }

            return isValid
        }

    private fun clearError() {
        binding.textLayoutAppId.error = null
        binding.textLayoutAppKey.error = null
        binding.textLayoutAccessTokenExpired.error = null
    }

    override fun onClickGenerateToken(view: View) {
        val appId = binding.textInputAppId.text.toString()
        val appKey = binding.textInputAppKey.text.toString()
        val accessTokenExpired = binding.textInputAccessTokenExpired.text.toString()
        currentDate = SimpleDateFormat.getDateInstance().format(Date())
        if (isDataValid) {
            viewModel.sendGenerateJWTToken(
                GenerateToken(
                    appId,
                    appKey,
                    accessTokenExpired
                )
            )
        }
    }

    private fun observeGeneratedToken() {
        observeData(viewModel.token) { tokenJWT ->
            tokenJWT?.let { tokenData ->
                if (tokenData.success!!) {
                    tokenData.data?.let { data ->
                        data.accessToken?.let {
                            PrefManager.TOKEN = it
                        }
                        data.expiredIn?.let { unixTime ->
                            PrefManager.TIME_EXPIRED_TOKEN = unixTime
                            PrefManager.STORE_DATE_TOKEN = currentDate
                        }
                    }
                }
            }
        }
    }


    private fun observeTemporaryToken() {
        observeData(viewModel.temporaryToken) { temporaryToken ->
            temporaryToken?.let { tokenData ->
                if (tokenData.success!!) {
                    tokenData.data?.let { data ->
                        data.accessToken?.let {
                            PrefManager.TEMPORARY_TOKEN = it
                            joinMeeting(meetingCodeLink!!, PrefManager.TEMPORARY_TOKEN)
                        }
                    }
                }
            }
        }
    }

    override fun onClickSubmit(view: View) {
        saveDataIdKeyToken()
        lateinit var radioLanguage: RadioButton
        lateinit var radioCreateJoin: RadioButton

        val selectedLanguage: Int = binding.radioGroupLanguage.checkedRadioButtonId
        val selectedCreteJoin: Int = binding.radioGroupCreatejoin.checkedRadioButtonId

        radioLanguage = findViewById(selectedLanguage)
        radioCreateJoin = findViewById(selectedCreteJoin)

        language = radioLanguage.text.toString()
        createOrJoin = radioCreateJoin.text.toString()
        if (isTitleValid) {
            if (createOrJoin == "Create Meeting") {
                viewModel.sendCreateMeetingFromApi(
                    CreateMeeting(
                        language.lowercase(Locale.getDefault()),
                        PrefManager.TOKEN,
                        binding.textInputTitle.text.toString(),
                        "",
                        "",
                        binding.textInputAppId.text.toString(),
                        "UMEETME",
                        true
                    )
                )
            } else {
                meetingCodeLink = binding.textInputTitle.text.toString()
                if (meetingCodeLink!!.contains("platform-stage")) {
                    meetingCodeLink = "https://" + binding.textInputTitle.text.toString()
                }

                viewModel.sendGenerateTemporaryToken(
                    TemporaryToken(meetingCodeLink!!)
                )
//                joinMeeting(meetingCodeLink!!, PrefManager.TOKEN)
            }
        }
    }

    private fun saveDataIdKeyToken() {
        PrefManager.APP_ID = textInput_app_id.text.toString()
        PrefManager.APP_KEY = textInput_app_key.text.toString()
        PrefManager.ACCESS_TOKEN_EXPIRED =
            Integer.parseInt(textInput_access_token_expired.text.toString())
    }

    private fun observeCreateMeeting() {
        observeData(viewModel.getCreateMeeting()) { createMeetingDto ->
            createMeetingDto?.let { createMeeting ->
                if (createMeeting.success!!) {
                    createMeeting.data?.let { data ->
                        data.detailMeetingData?.let { detailMeetingData ->
                            coreUMeetMe.mirrorFullUrl = detailMeetingData.meetingLink
                                ?.replace("lite?", "conf?")
                            coreUMeetMe.coreFullUrl = detailMeetingData.meetingCoreLink
                            coreUMeetMe.room = detailMeetingData.meetingCode
                            coreUMeetMe.meetingCode = detailMeetingData.meetingCode
                            MirrorUMeetMeManager(this).apply {
                                coreUMeetMe.service = getPath(coreUMeetMe.mirrorFullUrl!!)
                            }
                            coreUMeetMe.serviceCode = detailMeetingData.serviceCode
                            coreUMeetMe.subject = binding.textInputTitle.text.toString()
                            coreUMeetMe.secondId = null
                            coreUMeetMe.isHost = detailMeetingData.isHost
                            coreUMeetMe.latestActivity = null
                            detailMeetingData.createdDate?.split("T")!![0].let { date ->
                                coreUMeetMe.dateRoomCreated = date
                            }
                            detailMeetingData.createdDate?.split("T")!![1]
                                .split(".")[0].let { time ->
                                coreUMeetMe.timeRoomCreated = time
                            }
                            detailMeetingData.expiredDateUnixTime?.let { expiredIn ->
                                coreUMeetMe.expiredIn = expiredIn
                            }
                            coreUMeetMe.isPMR = null

                            isCreateMeeting = true
                            joinMeeting(detailMeetingData.meetingCode, PrefManager.TOKEN)

                        }
                    }

                }
            }
        }
    }

    private fun joinMeeting(meetingCode: String?, token: String?) {
        val joinMeeting = JoinMeeting(
            language.lowercase(Locale.getDefault()),
            token.toString(),
            meetingCode.toString(),
            "UMEETME_SIMULATOR",
            "",
            ""
        )
        viewModel.sendJoinMeetingFromApi(joinMeeting)
    }

    private fun observeJoinMeeting() {
        observeData(viewModel.getJoinMeeting()) { dto ->
            dto?.let { joinMeetingDto ->
                if (joinMeetingDto.success!!) {
                    joinMeetingDto.data?.let { data ->
                        if (isCreateMeeting == true) {
                            data.meetingData?.let { meetingData ->
                                coreUMeetMe.role = "host"
                                coreUMeetMe.linkOrCodeRoom = meetingData.linkOrCodeRoom
                            }
                        } else {
                            data.meetingData?.let { meetingData ->
                                coreUMeetMe.role = meetingData.role
                                coreUMeetMe.linkOrCodeRoom = meetingData.linkOrCodeRoom
                                coreUMeetMe.isSpecialUser = meetingData.specialUser
                            }
                        }
                        data.detailMeetingData?.let { detailMeetingData ->
                            coreUMeetMe.mirrorFullUrl = detailMeetingData.meetingLink
                                ?.replace("lite?", "conf?")
                            coreUMeetMe.coreFullUrl = detailMeetingData.meetingCoreLink
                            coreUMeetMe.room = detailMeetingData.meetingCode
                            coreUMeetMe.meetingCode = detailMeetingData.meetingCode
                            MirrorUMeetMeManager(this).apply {
                                coreUMeetMe.service = getPath(coreUMeetMe.mirrorFullUrl!!)
                            }
                            coreUMeetMe.serviceCode = detailMeetingData.serviceCode
                            coreUMeetMe.subject = binding.textInputTitle.text.toString()
                            coreUMeetMe.secondId = null
                            coreUMeetMe.latestActivity = null
                            detailMeetingData.createdDate?.split("T")!![0].let { date ->
                                coreUMeetMe.dateRoomCreated = date
                            }
                            detailMeetingData.createdDate?.split("T")!![1]
                                .split(".")[0].let { time ->
                                coreUMeetMe.timeRoomCreated = time
                            }
                            detailMeetingData.expiredDateUnixTime?.let { expiredIn ->
                                coreUMeetMe.expiredIn = expiredIn
                            }
                            coreUMeetMe.isPMR = null
                            coreUMeetMe.roomType = detailMeetingData.roomType

                            coreUMeetMe.sdkData = detailMeetingData.sdkData

                            MeetingCallActivity.startThisActivity(
                                this,
                                isVideoMuted = false,
                                isAudioMuted = false,
                                coreUMeetMe = coreUMeetMe
                            )
                        }
                    }

                }
            }
        }
    }

}