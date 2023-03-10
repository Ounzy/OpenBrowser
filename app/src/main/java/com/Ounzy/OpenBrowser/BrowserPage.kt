package com.Ounzy.OpenBrowser

import android.webkit.URLUtil
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.Ounzy.OpenBrowser.constants.startUrl

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BrowserPage() {
    var domain by remember { mutableStateOf(startUrl) }
    var browserCommands: BrowserCommands? = null

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        Modifier.fillMaxWidth()
                            .background(MaterialTheme.colorScheme.background),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            imageVector = Icons.Default.Home,
                            contentDescription = null,
                            Modifier.clickable(
                                onClick = {
                                    browserCommands?.goHome()
                                },
                            )
                                .size(50.dp)
                                .weight(0.15f)
                        )
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = null,
                            Modifier.clickable(
                                onClick = {
                                    browserCommands?.refresh()
                                },
                            )
                                .size(50.dp)
                                .weight(0.15f),
                        )
                        OutlinedTextField(
                            modifier = Modifier
                                .padding(bottom = 10.dp, )
                                .height(60.dp)
                                .width(230.dp)
                                .weight(0.7f),
                            value = domain,
                            onValueChange = { domain = it },
                            label = {
                                Text(text = "domain")
                            },
                            keyboardOptions = KeyboardOptions(
                                imeAction = ImeAction.Go,
                            ),
                            keyboardActions = KeyboardActions(
                                onGo = {
                                    if (URLUtil.isValidUrl(domain)) {
                                        browserCommands?.loadUrl(domain)
                                    } else {
                                        browserCommands?.loadUrl(startUrl + "search?q=" + domain)
                                    }
                                },
                            ),
                            singleLine = true,
                            textStyle = TextStyle(fontSize = 15.sp),
                        )
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = null,
                            modifier = Modifier
                                .size(50.dp)
                                .weight(0.15f)
                        )
                    }
                },
            )
        },
    ) { pV ->
        Box(Modifier.padding(pV)) {
            WebViewPage(
                startUrl,
                setBrowserCommands = {
                    browserCommands = it
                },
                onUrlChanged = { newUrl ->
                    domain = newUrl
                },
            )
        }
    }
}