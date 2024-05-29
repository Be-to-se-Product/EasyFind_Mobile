package com.easy.myapplication.shared.Input


import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.easy.myapplication.utils.CpfMaskTransformation
import com.easy.myapplication.utils.DateMaskTransformation
import com.easy.myapplication.utils.PhoneMaskTransformation
import java.text.SimpleDateFormat

enum class Type {
    EMAIL, PASSWORD, CPF, DATE, PHONE, SEARCH
}

@Composable

fun Input(value: String, onValueChange: (String) -> Unit, cursorColor:Color=Color.White,type: Type? = null, label: String = "",isValidate: Boolean=false , error: String = "",modifier: Modifier? = Modifier,colorText:Color= Color.White) {
    val color = if (error.isBlank() && !isValidate) Color(android.graphics.Color.parseColor("#FCA622")) else androidx.compose.ui.graphics.Color.Red;
    Column(modifier = modifier!!) {
        if (label.isNotBlank()) {
            Text(
                text = label,
                color = color,
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal
            )
        }
        Spacer(modifier = Modifier.height(5.dp))
        BasicTextField(

            modifier = Modifier.fillMaxWidth(),
            value = value,
            keyboardOptions = KeyboardOptions(
                keyboardType = when (type) {
                    Type.EMAIL -> KeyboardType.Text // Alterado de KeyboardType.Email para permitir caracteres especiais
                    Type.PASSWORD -> KeyboardType.Password
                    Type.CPF -> KeyboardType.Number
                    Type.DATE -> KeyboardType.Number
                    Type.PHONE -> KeyboardType.Number
                    Type.SEARCH -> KeyboardType.Uri
                    else -> KeyboardType.Text
                }
            ),
            cursorBrush = SolidColor(cursorColor),
            visualTransformation = if (type == Type.PASSWORD) {
                PasswordVisualTransformation()
            } else if (type == Type.CPF) {
                CpfMaskTransformation()
            } else if (type == Type.DATE) {
                DateMaskTransformation()
            } else if (type == Type.PHONE) {
                PhoneMaskTransformation()
            } else {
                VisualTransformation.None
            },
            onValueChange = {
                if (!(type == Type.CPF && it.length > 11 || type == Type.DATE && it.length > 8)) {
                    onValueChange(it)
                }
            },
            textStyle = TextStyle(color = colorText, fontSize = 14.sp),
            decorationBox = { innerTextField ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, color, RoundedCornerShape(20))
                        .padding(10.dp), verticalAlignment = Alignment.CenterVertically
                ) {
                    innerTextField()
                }
            }
        )
    }
}
