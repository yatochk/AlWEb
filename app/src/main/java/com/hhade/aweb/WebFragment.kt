package com.hhade.aweb

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_web.*

class WebFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_web, container, false)
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(main_web.settings) {
            javaScriptEnabled = true
            javaScriptCanOpenWindowsAutomatically = true
            allowFileAccess = true
            domStorageEnabled = true
        }
        main_web.webChromeClient = WebChromeClient()
        main_web.webViewClient = MWebViewClient()

        main_web.loadUrl(getString(R.string.url))
    }

    fun goBack() {
        if (main_web.canGoBack())
            main_web.goBack()
    }
}