package com.Ounzy.OpenBrowser // ktlint-disable package-name

import android.os.Bundle
import android.view.ContextMenu
import android.view.View
import android.webkit.WebView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.Ounzy.OpenBrowser.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    BrowserPage()
                }
            }
        }
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        val model: BrowserModel = ViewModelProvider(this).get()
        val result = model.getHitTestResult()
        if (result?.type == WebView.HitTestResult.SRC_ANCHOR_TYPE) {
            model.linkDialogUrl = result.extra
        }

        super.onCreateContextMenu(menu, v, menuInfo)
    }
}
