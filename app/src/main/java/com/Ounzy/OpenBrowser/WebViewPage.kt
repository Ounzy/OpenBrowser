package com.Ounzy.OpenBrowser

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.util.Log
import android.view.ViewGroup
import android.webkit.*
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.Ounzy.OpenBrowser.Screens.Loading
import java.io.BufferedReader
import java.io.ByteArrayInputStream
import java.io.InputStreamReader

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebViewPage(
    url: String,
    onUrlChanged: (url: String) -> Unit,
) {
    var backEnabled by remember { mutableStateOf(false) }
    var webView: WebView? = null
    val context = LocalContext.current
    var showLoading by remember { mutableStateOf(false) }

    val adServers = StringBuilder()
    var line: String?
    val inputStream = context.resources.openRawResource(R.raw.adblockserverlist)
    val br = BufferedReader(InputStreamReader(inputStream))
    var errorLoading by remember { mutableStateOf(false) }
    var loadURL by remember {
        mutableStateOf("")
    }

    if (showLoading) {
        Loading()
    }

    while (br.readLine().also { line = it } != null) {
        adServers.append(line)
        adServers.append("\n")
    }

    AndroidView(
        factory = {
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
                        url?.let { it1 -> onUrlChanged(it1) }
                    }

                    override fun shouldOverrideUrlLoading(
                        view: WebView?,
                        request: WebResourceRequest?,
                    ): Boolean {
                        val redirect = request?.url?.toString()?.contains("youtube.com") ?: return false
                        if (redirect) loadUrl("https://piped.video")
                        return redirect
                    }

                    override fun onPageCommitVisible(view: WebView?, url: String?) {
                        super.onPageCommitVisible(view, url)

                    }

                    override fun shouldInterceptRequest(view: WebView, request: WebResourceRequest): WebResourceResponse? {
                        val empty = ByteArrayInputStream("".toByteArray())
                        val kk5 = adServers.toString()
                        if (kk5.contains(":::::" + request.url.host)) {
                            return WebResourceResponse("text/plain", "utf-8", empty)
                        }
                        return super.shouldInterceptRequest(view, request)
                    }

                    override fun onReceivedError(
                        view: WebView?,
                        request: WebResourceRequest?,
                        error: WebResourceError?,
                    ) {
                        super.onReceivedError(view, request, error)
                        loadURL = "file:///android_asset/404.html"
                        errorLoading = true
                    }

                    override fun onPageFinished(view: WebView?, url: String?) {
                        super.onPageFinished(view, url)
                        showLoading = false
                    }
                }
                loadUrl(url)
                webView = this
            }
        },
        update = {
            webView = it
            // it.url?.let { url -> onUrlChanged(url) }
        },
    )
    BackHandler(enabled = backEnabled) {
        webView?.goBack()
    }

    if (errorLoading) WebViewPage(url = loadURL) {}
}
