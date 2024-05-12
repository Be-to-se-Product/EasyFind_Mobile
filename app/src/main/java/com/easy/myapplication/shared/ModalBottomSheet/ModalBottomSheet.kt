package com.easy.myapplication.shared.ModalBottomSheet
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun ModalBottomSheet(showBar: Boolean = false,closeFilter:()->Unit, content: @Composable () -> Unit) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    Scaffold {


        if (showBar) {
            ModalBottomSheet(
                onDismissRequest = {
                    closeFilter()
                },
                sheetState = sheetState

            ) {
                Row(
                    modifier = Modifier.padding(20.dp, 0.dp).fillMaxHeight(0.8f),
                ) {
                    content()
                }
            }
        }
    }
}