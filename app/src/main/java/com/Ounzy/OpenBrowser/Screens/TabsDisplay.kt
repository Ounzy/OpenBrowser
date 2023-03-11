package com.Ounzy.OpenBrowser.Screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.Ounzy.OpenBrowser.BrowserCommands
import com.Ounzy.OpenBrowser.database.DBInstance
import com.Ounzy.OpenBrowser.database.TabDbItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TabsList(
    onDismissRequest: () -> Unit,
    onTabUrlClicked: (index: Int, tabDbItem: TabDbItem) -> Unit
) {
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
                            Text("Tabs")
                        }
                    },
                )
            },
        ) { pV ->
            Box(modifier = Modifier.padding(pV)) {
                LazyColumn(Modifier.fillMaxWidth()) {
                    // DBInstance.Db.TabDao().insert(TabDbItem(url = "https://piped.video"))
                    val tabList: List<TabDbItem> = DBInstance.Db.TabDao().getAll()
                    item {
                        var browserCommands: BrowserCommands? = null
                        tabList.forEachIndexed { index, tabDbItem ->
                            ElevatedCard(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp, 12.dp)
                                    .clickable(
                                        onClick = {
                                            onTabUrlClicked(index, tabDbItem)
                                        }
                                    )
                            ) {
                                LazyRow(Modifier.fillMaxWidth()) {
                                    item {
                                        Text(
                                            tabDbItem.url.toString(),
                                            fontSize = 30.sp,
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
