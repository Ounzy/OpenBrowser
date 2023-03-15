package com.Ounzy.OpenBrowser.Screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.Ounzy.OpenBrowser.database.DBInstance
import com.Ounzy.OpenBrowser.database.TabDBItem.TabDbItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TabsList(
    onDismissRequest: () -> Unit,
    onTabUrlClicked: (index: Int, tabDbItem: TabDbItem) -> Unit,
) {
    val tabList = remember {
        DBInstance.Db.TabDao().getAll().toMutableStateList()
    }

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
                    itemsIndexed(tabList) { index, tabDbItem ->
                        ElevatedCard(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp, 12.dp)
                                .clickable(
                                    onClick = {
                                        onTabUrlClicked(index, tabDbItem)
                                    },
                                ),

                        ) {
                            Row(
                                Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                LazyRow(
                                    Modifier
                                        .fillMaxWidth()
                                        .weight(1f),
                                ) {
                                    item {
                                        Text(
                                            tabDbItem.url.toString(),
                                            fontSize = 30.sp,
                                        )
                                    }
                                }
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = null,
                                    Modifier
                                        .weight(0.15f)
                                        .size(30.dp)
                                        .clickable(
                                            onClick = {
                                                DBInstance.Db.TabDao().delete(tabDbItem)
                                                tabList.removeAt(index)
                                            },
                                        ),
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
