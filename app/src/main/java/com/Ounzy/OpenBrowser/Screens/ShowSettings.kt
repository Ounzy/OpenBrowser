package com.Ounzy.OpenBrowser.Screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
                            Text(
                                "Settings",
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    },
                )
            },
        ) { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues)) {
                LazyColumn(Modifier.fillMaxWidth()) {
                    item {
                        ElevatedCard(
                            Modifier.clickable(
                                onClick = { showThemeDetails = true }
                        )
                                .padding(5.dp, 10.dp),
                        ) {
                            Row(Modifier.fillMaxWidth()) {
                                Text(
                                    text = "Theme",
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
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

