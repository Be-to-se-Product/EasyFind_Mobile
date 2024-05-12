package com.easy.myapplication.shared.Drawble

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.easy.myapplication.shared.UserHead.UserHead
import kotlinx.coroutines.CoroutineScope
import androidx.compose.ui.unit.LayoutDirection
import com.easy.myapplication.repositories.StorageRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.compose.inject

@Composable
fun Drawble(content: @Composable (DrawerState,CoroutineScope) -> Unit) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val nome = remember { mutableStateOf("") }
    val storage : StorageRepository by inject<StorageRepository>();

    fun getUserName() {
        CoroutineScope(Dispatchers.IO).launch {
            val nomeValue = storage.readFromDataStore("nome").toString()
            withContext(Dispatchers.Main) {
                nome.value = nomeValue
            }
        }
    }
    getUserName()
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        ModalNavigationDrawer(
            drawerState =drawerState,
            drawerContent = {
                ModalDrawerSheet(modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .fillMaxHeight()
                ) {
                    Column (horizontalAlignment = Alignment.End) {
                        Row(modifier = Modifier.padding(10.dp), verticalAlignment = Alignment.CenterVertically) {
                            Text("Olá, ${nome.value}", modifier = Modifier.padding(16.dp))
                            UserHead(id = "1", firstName = nome.value, lastName = "")
                        }
                        Divider()
                        NavigationDrawerItem(
                            label = { Text(text = "Meus pedidos", textAlign = TextAlign.Left, modifier = Modifier.fillMaxWidth())},
                            selected = false,
                            onClick = {}
                        )
                        NavigationDrawerItem(
                            label = { Text(text = "Buscar pedidos", textAlign = TextAlign.Left, modifier = Modifier.fillMaxWidth())},
                            selected = false,
                            onClick = {}
                        )
                    }
                }
            },
            content = { content(drawerState,scope) }

        )
    }

}