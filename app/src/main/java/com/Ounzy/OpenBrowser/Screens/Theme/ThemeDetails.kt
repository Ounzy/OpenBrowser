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
    var restart by remember {
        mutableStateOf(false)
    }

    AlertDialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        ),
            ) {
        ElevatedCard() {
            Scaffold(
                modifier = Modifier
                    .width(250.dp)
                    .height(300.dp)
                    .padding(),
                topBar = {
                    TopAppBar(
                            title = { Text(text = "Theme") },
                        )
                },
            ) { paddingValues ->
                Box(modifier = Modifier.padding(paddingValues)) {
                    LazyColumn(
                        Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        item {
                            var darkGreenTheme by remember {
                                mutableStateOf(Preferences.instance.getString(Preferences.themeModePrefKey, "") == "green")
                            }
                            var darkTheme by remember {
                                mutableStateOf(Preferences.instance.getString(Preferences.themeModePrefKey, "") == "dark")
                            }
                            var lightTheme by remember {
                                mutableStateOf(Preferences.instance.getString(Preferences.themeModePrefKey, "") == "light")
                            }
                            var materialTheme by remember {
                                mutableStateOf(Preferences.instance.getString(Preferences.themeModePrefKey, "") == "materialTheme")
                            }
                            Row(
                                Modifier
                                    .fillMaxWidth()
                                    .padding(0.dp, 0.dp),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = "System")
                                Checkbox(checked = materialTheme, onCheckedChange = {
                                    materialTheme = it
                                    val newValue = if (it) "materialTheme" else ""
                                    Preferences.instance.edit()
                                        .putString(Preferences.themeModePrefKey, newValue)
                                        .apply()
                                    darkGreenTheme = false
                                    darkTheme = false
                                    lightTheme = false
                                    restart = true
                                })
                            }
                            Row(
                                Modifier
                                    .fillMaxWidth()
                                    .padding(0.dp, 0.dp),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = "Light")
                                Checkbox(checked = lightTheme, onCheckedChange = {
                                    lightTheme = it
                                    val newValue = if (it) "light" else ""
                                    Preferences.instance.edit()
                                        .putString(Preferences.themeModePrefKey, newValue)
                                        .apply()
                                    darkGreenTheme = false
                                    darkTheme = false
                                    materialTheme = false
                                    restart = true
                                })
                            }
                            Row(
                                Modifier
                                    .fillMaxWidth()
                                    .padding(0.dp, 0.dp),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = "Dark")
                                Checkbox(checked = darkTheme, onCheckedChange = {
                                    darkTheme = it
                                    val newValue = if (it) "dark" else ""
                                    Preferences.instance.edit()
                                        .putString(Preferences.themeModePrefKey, newValue)
                                        .apply()
                                    darkGreenTheme = false
                                    lightTheme = false
                                    materialTheme = false
                                    restart = true
                                })
                            }
                            Row(
                                Modifier
                                    .fillMaxWidth()
                                    .padding(0.dp, 0.dp),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = "DarkGreen")
                                Checkbox(checked = darkGreenTheme, onCheckedChange = {
                                    darkGreenTheme = it
                                    val newValue = if (it) "green" else ""
                                    Preferences.instance.edit()
                                        .putString(Preferences.themeModePrefKey, newValue)
                                        .apply()
                                    darkTheme = false
                                    lightTheme = false
                                    materialTheme = false
                                    restart = true
                                })
                            }
                        }
                    }
                }
            }
        }

    }
    if (restart) restartPhone {
        restart = false
    }
}





@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun restartPhone(
    onDismissRequest: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        ),
    ) {
        Scaffold(
            modifier = Modifier
                .height(0.dp)
                .width(0.dp)
                .padding(20.dp, 20.dp),
            containerColor = MaterialTheme.colorScheme.surface
            ) { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues)) {
                Dialog(onDismissRequest = onDismissRequest) {
                    Text(
                        text = "Restart your phone to apply new theme",
                        color = MaterialTheme.colorScheme.primary
                        )
                }
            }
        }
    }
}