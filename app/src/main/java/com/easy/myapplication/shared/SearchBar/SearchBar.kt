package com.easy.myapplication.shared.SearchBar

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp



@Composable
fun SearchBar(modifier: Modifier=Modifier,value:String="",setValue:(value:String)->Unit,colorText:Color=Color.Black,borderColor:Color=Color.Black,cursorColor:Color= Color.Black,onSearch:(value:String)->Unit){
    Column(modifier=modifier) {
        BasicTextField(
            modifier = Modifier.fillMaxWidth(),
            value = value,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = { onSearch(value) }),
            cursorBrush = SolidColor(cursorColor),
            onValueChange = setValue,
            textStyle = TextStyle(color = colorText, fontSize = 14.sp),
            decorationBox = { innerTextField ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .border(1.dp, borderColor, RoundedCornerShape(20))
                        .padding(10.dp), verticalAlignment = Alignment.CenterVertically
                ) {

                    innerTextField()
                }
            }

        )
        Spacer(modifier = Modifier.height(5.dp))
    }
}