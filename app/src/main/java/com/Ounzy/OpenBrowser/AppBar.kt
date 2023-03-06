package com.Ounzy.OpenBrowser

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.Ounzy.OpenBrowser.constants.startUrl

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar() {
    var domain by remember { mutableStateOf(startUrl) }
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
                                onClick = { /* Move to Startsite */ },
                            )
                                .size(50.dp),
                        )
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = null,
                            Modifier.clickable(
                                onClick = { /* Refresh site fun */ },
                            )
                                .size(50.dp),
                        )
                        OutlinedTextField(
                            value = domain,
                            onValueChange = { domain = it },
                            label = {
                                Text(text = "domain")
                            },
                            keyboardOptions = KeyboardOptions(
                                imeAction = ImeAction.Go,
                            ),
                            maxLines = 1
                        )
                    }
                },
            )
        },
    ) { pV ->
        Box(Modifier.padding(pV)) {
            WebViewPage(url = domain) {
                domain = it
            }
        }
    }
}
