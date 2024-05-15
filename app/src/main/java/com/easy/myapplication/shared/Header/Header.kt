package com.easy.myapplication.shared.Header

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.easy.myapplication.R
import com.easy.myapplication.repositories.StorageRepository
import com.easy.myapplication.shared.Drawble.Drawble
import com.easy.myapplication.shared.Title.Title
import com.easy.myapplication.shared.UserHead.UserHead
import com.easy.myapplication.ui.theme.Primary
import com.easy.myapplication.ui.theme.Seconday
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.koin.androidx.compose.inject
import org.koin.core.component.inject

@Composable
fun Header(content:@Composable ()-> Unit) {
    val storage : StorageRepository by inject<StorageRepository>();

    val nome = remember { mutableStateOf("") }

    fun getUserName() {
        CoroutineScope(Dispatchers.IO).launch {
            val nomeValue = storage.readFromDataStore("nome").toString()
            withContext(Dispatchers.Main) {
                nome.value = nomeValue
            }
        }
    }
    getUserName()

    Surface {
        Drawble() { drawerState: DrawerState, scope: CoroutineScope ->
            Column {
                Row(
                    modifier = Modifier
                        .background(Seconday)
                        .fillMaxWidth(1f)
                        .padding(20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box() {
                        Row(
                            modifier = Modifier.width(100.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            UserHead(
                                id = "1",
                                firstName = nome.value,
                                lastName = "",
                                modifier = Modifier.clickable(
                                    onClick = { scope.launch { drawerState.open() } },
                                    enabled = true
                                )
                            )
                            Image(
                                painter = painterResource(id = R.mipmap.cart),
                                contentDescription = "Cart",
                                modifier = Modifier.width(25.dp)
                            )
                        }
                    }
                    Title(content = "EasyFind", color = Primary, maxLines = 1)
                }
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                    content()
                }
            }

        }
    }
}





