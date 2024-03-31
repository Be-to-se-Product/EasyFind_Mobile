package com.easy.myapplication
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun ButtonCustomize(
    onClick: () -> Unit, content: @Composable RowScope.() -> Unit
) {
    Button(
        onClick = onClick, content = content,
        shape = RoundedCornerShape(20), modifier = Modifier.fillMaxWidth(fraction = 1f),

        colors = ButtonDefaults.buttonColors(Color(0xFFFCA622))
    )
}


@Composable
fun Title(
    content: String,
    fontSize: TextUnit = 20.sp,
    color: Color = Color.White
) {
    Text(text = content, color = color, fontSize = fontSize, fontWeight = FontWeight.SemiBold)
}

@Composable
fun Subtitle(
    content: String,
    fontSize: TextUnit = 12.sp,
    color: Color = Color.White
) {

    Text(text = content, color = color, fontSize = fontSize, fontWeight = FontWeight.Normal)

}

enum class Type {
    EMAIL, PASSWORD
}

@Composable


fun Input(value: String, onValueChange: (String) -> Unit, type: Type? = null, label: String = "",isValidate: Boolean=false , error: String = "") {
    val color = if (error.isBlank() && !isValidate) Color(android.graphics.Color.parseColor("#FCA622")) else Color.Red;
    Column {
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
                    Type.EMAIL -> KeyboardType.Email
                    Type.PASSWORD -> KeyboardType.Password
                    else -> KeyboardType.Text
                }
            ),
            cursorBrush = SolidColor(Color.White),
            visualTransformation = if (type == Type.PASSWORD) {
                PasswordVisualTransformation()
            } else {
                VisualTransformation.None
            },
            onValueChange = {
                onValueChange(it)
            },
            textStyle = TextStyle(color = Color.White, fontSize = 14.sp),
            decorationBox = { innerTextField ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, color, RoundedCornerShape(20))
                        .padding(10.dp), verticalAlignment = Alignment.CenterVertically
                ) {

                    innerTextField()
                }
                


            })
    }


}
@Composable
fun SelectBox(value: String, onValueChange: (String) -> Unit, label: String = "",isValidate: Boolean=false , error: String = "", expandido: MutableState<Boolean>, generos: MutableList<String>, genero: MutableState<String>) {
    val color = if (error.isBlank() && !isValidate) Color(android.graphics.Color.parseColor("#FCA622")) else Color.Red;
    val focusRequester = remember { FocusRequester() }
    Column {
        if (label.isNotBlank()) {
            Text(
                text = label,
                color = color,
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal
            )
        }
        Spacer(modifier = Modifier.height(5.dp))
        Box(modifier = Modifier.clickable { expandido.value = true }){

            BasicTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester)
                    .onFocusChanged { if (it.isFocused) expandido.value = true }, enabled = false,
                value = value,
                cursorBrush = SolidColor(Color.White),
                onValueChange = {
                    onValueChange(it)
                },
                textStyle = TextStyle(color = Color.White, fontSize = 14.sp),
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

        DropdownMenu(expanded = expandido.value, onDismissRequest = { expandido.value = false }) {
            generos.forEach {
                DropdownMenuItem(text ={ Text(it)}, onClick = {
                    genero.value =it
                    expandido.value = false
                })
            }
        }
    }


}
