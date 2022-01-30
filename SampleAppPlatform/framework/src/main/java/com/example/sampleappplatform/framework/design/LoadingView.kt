package com.example.sampleappplatform.framework.design

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.util.MalformedJsonException
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat
import com.airbnb.lottie.LottieAnimationView
import com.example.framework.R
import com.example.sampleappplatform.framework.extention.showToast
import okhttp3.internal.http2.StreamResetException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * Created by Isnaeni on 05/08/2021.
 */

class LoadingView(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
    RelativeLayout(context, attrs, defStyleAttr) {

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    init {
        injectViews()
    }

    enum class State {
        LOADING, ERROR, EMPTY, UNAUTHORIZE
    }

    private var state: State = State.ERROR
    private var progressBar: LottieAnimationView? = null
    private var progressAnimation: LottieAnimationView? = null
    private var baseView: LinearLayout? = null
    private var backgroundImageView: ImageView? = null
    private var progressSubtitle: TextView? = null
    private var progressTitle: TextView? = null
    private var progressMessage: TextView? = null
    private var retryButton: Button? = null
    private var listener: OnRetryListener? = null

    private fun injectViews() {
        inflate(context, R.layout.loading_view, this)
        progressBar = findViewById(R.id.lottie_progress)
        progressAnimation = findViewById(R.id.progressAnimation)
        baseView = findViewById(R.id.view_loading)
        backgroundImageView = findViewById(R.id.img_background)
        progressSubtitle = findViewById(R.id.txt_progress_subtitle)
        progressTitle = findViewById(R.id.txt_title)
        progressMessage = findViewById(R.id.txt_message)
        retryButton = findViewById(R.id.btn_retry)
        retryButton?.setOnClickListener { onClickRetry() }
        setDefaultRetrybutton()
        showLoading()
    }

    private fun showLoading() {
        state = State.LOADING
        progressSubtitle?.text = context.getString(R.string.pl_loading)
        progressTitle?.text = null
        progressMessage?.text = null
        baseView?.background = ContextCompat.getDrawable(context, R.color.white)
        showButton()
        showProgress()
    }

    fun showLoading(message: String, jsonName: String) {
        state = State.LOADING
        progressTitle?.text = null
        progressMessage?.text = message
        showButton()
        progressTitle?.visibility = View.GONE
        progressMessage?.visibility = View.VISIBLE
        showProgressAnimation(jsonName)
    }

    private fun showProgressAnimation(jsonName: String) {
        progressAnimation?.setAnimation(jsonName)
        showProgressAnimation()
    }

    private fun showProgressAnimation() {
        progressBar?.visibility = View.GONE
        progressAnimation?.visibility = View.VISIBLE
        progressAnimation?.playAnimation()
    }

    private fun showProgress() {
        progressBar?.visibility = when (state) {
            State.LOADING -> View.VISIBLE
            else -> View.GONE
        }
        progressSubtitle?.visibility = when (state) {
            State.LOADING -> View.VISIBLE
            else -> View.GONE
        }
        progressTitle?.visibility = when (state) {
            State.LOADING -> View.GONE
            else -> View.VISIBLE
        }
        progressMessage?.visibility = when (state) {
            State.LOADING -> View.GONE
            else -> View.VISIBLE
        }
        if (state == State.LOADING)
            backgroundImageView?.visibility = View.GONE
        progressAnimation?.visibility = View.GONE
    }

    private fun showButton() {
        retryButton?.visibility = when (state) {
            State.LOADING -> View.GONE
            else -> View.VISIBLE
        }
    }

    fun showEmpty(
        title: String,
        message: String,
        showButton: Boolean,
        buttonText: String?
    ) {
        state = State.EMPTY
        baseView?.background = ContextCompat.getDrawable(context, R.color.white)
        progressTitle?.text = title
        progressMessage?.text = message
        if (buttonText.isNullOrEmpty()) setDefaultRetrybutton()
        else retryButton?.text = buttonText
        retryButton?.visibility = when (showButton) {
            true -> View.VISIBLE
            false -> View.GONE
        }
        showProgress()
    }

    @SuppressLint("LogNotTimber")
    fun showError(title: String? = null, message: String, exception: Exception? = null) {
        state = State.ERROR
        baseView?.background = ContextCompat.getDrawable(context, R.color.white)
        if (title == null) {
            progressTitle?.visibility = View.INVISIBLE
        } else {
            progressTitle?.text = title
            progressTitle?.visibility = View.VISIBLE
        }
        progressMessage?.text = message
        exception?.let {
            Log.e("ERROR_EXCEPTION", it.toString())
        }
        showButton()
        showProgress()
    }

    fun getMessage(exception: Exception?): String {
        return when (exception) {
            is UnknownHostException, is ConnectException -> {
                context.getString(R.string.me_connection)
            }
            is SocketTimeoutException -> {
                context.getString(R.string.me_time_out)
            }
            is MalformedJsonException -> {
                context.getString(R.string.me_unknown)
            }
            is StreamResetException -> {
                context.getString(R.string.me_server_down)
            }
            else -> {
                context.getString(R.string.me_server)
            }
        }
    }

    fun showMessage(exception: Exception?) {
        when (exception) {
            is UnknownHostException, is ConnectException -> {
                context.showToast(context.getString(R.string.me_connection))
            }
            is SocketTimeoutException -> {
                context.showToast(context.getString(R.string.me_time_out))
            }
            is MalformedJsonException -> {
                context.showToast(context.getString(R.string.me_unknown))
            }
            is StreamResetException -> {
                context.showToast(context.getString(R.string.me_server_down))
            }
            else -> {
                context.showToast(context.getString(R.string.me_server))
                Log.e("ERROR_EXCEPTION", exception.toString())
            }
        }
    }

    fun showError(exception: Exception?) {
        setDefaultRetrybutton()
        retryButton?.setOnClickListener { onClickRetry() }
        state = State.ERROR
        when (exception) {
            is UnknownHostException, is ConnectException -> {
                progressTitle?.text = context.getString(R.string.me_connection_title)
                progressMessage?.text = context.getString(R.string.me_connection)
            }
            is SocketTimeoutException -> {
                progressTitle?.text = context.getString(R.string.me_time_out_title)
                progressMessage?.text = context.getString(R.string.me_time_out)
            }
            is MalformedJsonException -> {
                progressTitle?.text = context.getString(R.string.me_unknown_title)
                progressMessage?.text = context.getString(R.string.me_unknown)
            }
            is StreamResetException -> {
                progressTitle?.text = context.getString(R.string.me_server_down_title)
                progressMessage?.text = context.getString(R.string.me_server_down)
            }
            else -> {
                progressTitle?.text = context.getString(R.string.me_server_title)
                progressMessage?.text = context.getString(R.string.me_server)
                Log.e("ERROR_EXCEPTION", exception.toString())
            }
        }
        showButton()
        showProgress()
    }

    private fun setDefaultRetrybutton() {
        retryButton?.text = context.getString(R.string.action_retry)
    }

    fun setOnRetryListener(listener: OnRetryListener) {
        this.listener = listener
    }

    private fun onClickRetry() {
        listener?.let {
            if (progressAnimation?.animation != null) showProgressAnimation() else showLoading()

            if (state == State.EMPTY) it.onClickEmpty() else it.onRetry()
        }
    }

    interface OnRetryListener {
        fun onRetry()

        fun onClickEmpty() {
            //Do something for empty state
        }
    }
}