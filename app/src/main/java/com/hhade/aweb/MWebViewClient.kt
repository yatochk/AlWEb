package com.hhade.aweb

import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient

class MWebViewClient : WebViewClient() {
    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        view?.loadUrl(request?.url.toString())
        return true
    }
}