package com.Ounzy.OpenBrowser

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.view.ViewGroup
import android.webkit.*
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.Ounzy.OpenBrowser.Screens.Loading
import java.io.BufferedReader
import java.io.ByteArrayInputStream
import java.io.IOException
import java.io.InputStreamReader

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebViewPage(URL: String) {
    var backEnabled by remember { mutableStateOf(false) }
    var webView: WebView? = null
    val context = LocalContext.current
    var showLoading by remember { mutableStateOf(false) }

    val adServers = StringBuilder()
    var line: String? = ""
    val inputStream = context.resources.openRawResource(R.raw.adblockserverlist)
    val br = BufferedReader(InputStreamReader(inputStream))

    if (showLoading) {
        Loading()
    }

    try {
        while (br.readLine().also { line = it } != null) {
            adServers.append(line)
            adServers.append("\n")
        }
    } catch (e: IOException) {
        e.printStackTrace()
    }

    AndroidView(factory = {
        WebView(it).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT,
            )
            webViewClient = WebViewClient()
            settings.javaScriptEnabled = true
            settings.userAgentString = System.getProperty("http.agent")
            settings.setSupportZoom(true)
            settings.domStorageEnabled = true
            settings.loadWithOverviewMode = true
            settings.allowContentAccess = true
            settings.loadsImagesAutomatically = true
            settings.mixedContentMode = WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE
            CookieManager.getInstance().setAcceptCookie(true)

            webViewClient = object : WebViewClient() {

                override fun onPageStarted(view: WebView, url: String?, favicon: Bitmap?) {
                    backEnabled = view.canGoBack()
                    showLoading = true
                }

                override fun shouldOverrideUrlLoading(
                    view: WebView?,
                    request: WebResourceRequest?,
                ): Boolean {
                    val redirect = request?.url?.toString()?.contains("youtube") ?: return false
                    if (redirect) loadUrl("https://bnyro.is-a.dev")
                    return redirect
                }

                override fun onPageCommitVisible(view: WebView?, url: String?) {
                    super.onPageCommitVisible(view, url)
                    showLoading = false
                }

                override fun shouldInterceptRequest(view: WebView, request: WebResourceRequest): WebResourceResponse? {
                    val empty = ByteArrayInputStream("".toByteArray())
                    val kk5 = adServers.toString()
                    if (kk5.contains(":::::" + request.url.host)) {
                        return WebResourceResponse("text/plain", "utf-8", empty)
                    }
                    return super.shouldInterceptRequest(view, request)
                }
            }
            loadUrl(URL)
            webView = this
        }
    }, update = {
        webView = it
    })
    BackHandler(enabled = backEnabled) {
        webView?.goBack()
    }
}
