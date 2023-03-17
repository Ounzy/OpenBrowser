package com.Ounzy.OpenBrowser.Screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.Ounzy.OpenBrowser.Screens.Theme.themedetails

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun ShowSettings(
    onDismissRequest: () -> Unit,
) {
    var showThemeDetails by remember { mutableStateOf(false)}
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(usePlatformDefaultWidth = false),
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Row(
                            Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                        ) {
                            Text("Settings")
                        }
                    },
                )
            },
        ) { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues)) {
                LazyColumn(Modifier.fillMaxWidth()) {
                    item {
                        Row(Modifier.fillMaxWidth()) {
                            Text(
                                text = "Theme",
                                Modifier.clickable(
                                    onClick = { showThemeDetails = true },
                                ),
                            )
                        }
                    }
                }
            }
        }
    }
    if (showThemeDetails) {
        themedetails {
            showThemeDetails = false
        }
    }
}

