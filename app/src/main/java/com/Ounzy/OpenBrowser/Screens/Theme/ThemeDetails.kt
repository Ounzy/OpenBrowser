package com.Ounzy.OpenBrowser.Screens.Theme

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.Ounzy.OpenBrowser.Preferences

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun themedetails(
    onDismissRequest: () -> Unit,
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        ),
            ) {
                Scaffold(
                    modifier = Modifier
                        .width(250.dp)
                        .height(300.dp)
                        .padding(20.dp, 20.dp),
                    topBar = {
                        Row(Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            TopAppBar(
                                title = { Text(text = "Theme") },
                            )
                        }
                    },
                ) { paddingValues ->
                    Box(modifier = Modifier.padding(paddingValues)) {
                        LazyColumn(Modifier.fillMaxWidth()) {
                            item {
                                var darkGreenTheme by remember {
                                    mutableStateOf(Preferences.instance.getString(Preferences.themeModePrefKey, "") == "green")
                                }
                                Row(
                                    Modifier
                                        .fillMaxWidth()
                                        .padding(10.dp, 10.dp),
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically
                                    ) {
                                    Text(text = "GreenDark")
                                    Checkbox(checked = darkGreenTheme, onCheckedChange = {
                                        darkGreenTheme = it
                                        val newValue = if (it) "green" else ""
                                        Preferences.instance.edit()
                                            .putString(Preferences.themeModePrefKey, newValue).apply()
                                    })
                                }
                            }
                        }
                    }
                }
            }
        }




