package com.Ounzy.OpenBrowser

import android.webkit.WebView.HitTestResult
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class BrowserModel: ViewModel() {
    var linkDialogUrl by mutableStateOf<String?>(null)
    var getHitTestResult: () -> HitTestResult? = { null }
}
