package com.easy.myapplication.shared.Header

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.easy.myapplication.R
import com.easy.myapplication.shared.Title.Title
import com.easy.myapplication.shared.UserHead.UserHead
import com.easy.myapplication.ui.theme.Primary
import com.easy.myapplication.ui.theme.Seconday
import kotlinx.coroutines.launch

@Composable
fun Header(){
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Open)
    val scope = rememberCoroutineScope()
    Column {
        Row (modifier = Modifier
            .background(Seconday)
            .fillMaxWidth(1f)
            .padding(20.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Title(content = "EasyFind", color = Primary)
            Box() {
                Row(
                    modifier = Modifier.width(90.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Image(
                        painter = painterResource(id = R.mipmap.cart),
                        contentDescription = "Cart",
                        modifier = Modifier.width(25.dp)
                    )
                    UserHead(id = "1", firstName = "Matheus", lastName = "Lessa", modifier = Modifier.clickable(onClick = { scope.launch { drawerState.open() }
                    }))
                }
            }
        }
    }

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl ) {
        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                ModalDrawerSheet(modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .fillMaxHeight()
                ) {
                    Column (horizontalAlignment = Alignment.End) {
                        Row(modifier = Modifier.padding(10.dp)) {
                            Text("Olá, Matheus", modifier = Modifier.padding(16.dp))
                            UserHead(id = "1", firstName = "Matheus", lastName = "Lessa")
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
            }
        )
        {

        }
    }
}
