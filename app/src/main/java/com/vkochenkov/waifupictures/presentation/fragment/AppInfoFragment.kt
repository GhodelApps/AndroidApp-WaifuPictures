package com.vkochenkov.waifupictures.presentation.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.vkochenkov.waifupictures.R
import com.vkochenkov.waifupictures.di.App

class AppInfoFragment : Fragment() {

    lateinit var appInfoCreatedTv: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_app_info, container, false)

        setSpanForInfoCreatedTextView(view)

        return view
    }

    private fun setSpanForInfoCreatedTextView(view: View) {
        appInfoCreatedTv = view.findViewById(R.id.app_info_created_tv)

        val span = SpannableString(activity?.getString(R.string.app_info_created_str))

        fun openBrowser(url: String) {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }

        val clickableSpanKochenkov = object : ClickableSpan(){
            override fun onClick(widget: View) {
                openBrowser("https://play.google.com/store/apps/developer?id=Kochenkov")
            }
        }
        val clickableSpanWaifuApi = object : ClickableSpan(){
            override fun onClick(widget: View) {
                openBrowser("https://waifu.pics/docs")
            }
        }

        span.setSpan(clickableSpanKochenkov, 17, 26, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        span.setSpan(clickableSpanWaifuApi, 54, 63, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        appInfoCreatedTv.text = span
        appInfoCreatedTv.movementMethod = LinkMovementMethod.getInstance()
    }
}