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
import com.Ounzy.OpenBrowser.constants.MainStartUrl
import com.Ounzy.OpenBrowser.database.DBInstance.Db
import com.Ounzy.OpenBrowser.database.TabDbItem
import java.io.BufferedReader
import java.io.ByteArrayInputStream
import java.io.InputStreamReader

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebViewPage(
    startUrl: String,
    setBrowserCommands: (BrowserCommands) -> Unit,
    onUrlChanged: (url: String) -> Unit,
) {
    var openedTab = 0

    var backEnabled by remember { mutableStateOf(false) }
    var webView: WebView? = null
    val context = LocalContext.current
    var showLoading by remember { mutableStateOf(false) }

    val adServers = StringBuilder()
    var line: String?
    val inputStream = context.resources.openRawResource(R.raw.adblockserverlist)
    val br = BufferedReader(InputStreamReader(inputStream))

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
                        if (url != null) onUrlChanged(url)
                        val tabs: List<TabDbItem> = Db.TabDao().getAll()
                        if (tabs.size >= openedTab + 1) {
                            val tabDbItem = tabs[openedTab]
                            tabDbItem.url = url
                            Db.TabDao().update(tabDbItem)
                            Log.e("Tabs:", Db.TabDao().getAll().toString())
                        } else {
                            val tabDbItem = TabDbItem(url = url)
                            Db.TabDao().insert(tabDbItem)
                            Log.e("Tabs:", Db.TabDao().getAll().toString())
                        }
                    }

                    override fun shouldOverrideUrlLoading(
                        view: WebView?,
                        request: WebResourceRequest?,
                    ): Boolean {
                        val redirect =
                            request?.url?.toString()?.contains("youtube.com") ?: return false
                        if (redirect) loadUrl("https://piped.video")
                        return redirect
                    }

                    override fun onPageCommitVisible(view: WebView?, url: String?) {
                        super.onPageCommitVisible(view, url)
                    }

                    override fun shouldInterceptRequest(
                        view: WebView,
                        request: WebResourceRequest,
                    ): WebResourceResponse? {
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
                        if (error != null) {
                            if (error.description.toString().contains("NAME_NOT_RESOLVED")) {
                                webView?.loadUrl("file:///android_asset/404.html")
                            } else {
                                return
                            }
                        }
                    }

                    override fun onPageFinished(view: WebView?, url: String?) {
                        super.onPageFinished(view, url)
                        showLoading = false
                    }
                }
                loadUrl(startUrl)
                webView = this

                val browserCommands = object : BrowserCommands {
                    override fun refresh() {
                        webView?.reload()
                    }

                    override fun loadUrl(url: String) {
                        webView?.loadUrl(url)
                    }

                    override fun goHome() {
                        webView?.loadUrl(MainStartUrl)
                        Log.e("startUrl;", MainStartUrl)
                    }

                    override fun setSelectedTab(index: Int) {
                        openedTab = index
                    }
                }
                setBrowserCommands(browserCommands)
            }
        },
        update = {
            webView = it
        },
    )
    BackHandler(enabled = backEnabled) {
        webView?.goBack()
    }
}
