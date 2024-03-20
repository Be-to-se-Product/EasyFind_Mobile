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
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
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
fun CpfMaskTransformation() = VisualTransformation { text ->
    val trimmed = if (text.text.length >= 11) text.text.substring(0, 11) else text.text
    val transformed = StringBuilder()
    val offsetTranslator = object : OffsetMapping {
        override fun originalToTransformed(offset: Int) = offset + when (offset) {
            in 0..2 -> 0
            in 3..5 -> 1
            in 6..8 -> 2
            else -> 3
        }
        override fun transformedToOriginal(offset: Int) = offset - when (offset) {
            in 0..3 -> 0
            in 4..7 -> 1
            in 8..11 -> 2
            else -> 3
        }
    }
    trimmed.forEachIndexed { index, char ->
        transformed.append(char)
        if (index == 2 || index == 5) transformed.append('.')
        if (index == 8) transformed.append('-')
    }
    TransformedText(AnnotatedString(transformed.toString()), offsetTranslator)
}
class DateMaskTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        var out = ""
        when (text.text.length) {
            in 1..2 -> out = text.text
            in 3..4 -> out = text.text.substring(0, 2) + "/" + text.text.substring(2)
            in 5..8 -> out = text.text.substring(0, 2) + "/" + text.text.substring(2, 4) + "/" + text.text.substring(4)
            else -> out = text.text
        }
        val outputText = buildAnnotatedString {
            withStyle(style = SpanStyle(color = Color.White)) {
                append(out)
            }
        }
        val offsetTranslator = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                return when (offset) {
                    in 0..2 -> offset
                    in 3..4 -> offset + 1
                    in 5..8 -> offset + 2
                    else -> offset
                }
            }

            override fun transformedToOriginal(offset: Int): Int {
                return when (offset) {
                    in 0..2 -> offset
                    in 4..5 -> offset - 1
                    in 7..10 -> offset - 2
                    else -> offset
                }
            }
        }
        return TransformedText(outputText, offsetMapping = offsetTranslator)
    }
}
enum class Type {
    EMAIL, PASSWORD, CPF, DATE
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
                    Type.CPF -> KeyboardType.Number
                    Type.DATE -> KeyboardType.Number
                    else -> KeyboardType.Text
                }
            ),
            cursorBrush = SolidColor(Color.White),
            visualTransformation = if (type == Type.PASSWORD) {
                PasswordVisualTransformation()
            } else if (type == Type.CPF) {
                CpfMaskTransformation()
            } else if (type == Type.DATE) {
                DateMaskTransformation()
            } else {
                VisualTransformation.None
            },
            onValueChange = {
                if (type == Type.CPF && it.length > 11 || type == Type.DATE && it.length > 8) {

                } else {
                    onValueChange(it)
                }
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
            DropdownMenuItem(
                text = {
                    Text("Qual o genêro?", style = TextStyle(color = Color.Gray))
                },
                onClick = { expandido.value = false })
            Divider()
            generos.forEach {
                DropdownMenuItem(text ={ Text(it)}, onClick = {
                    genero.value =it
                    expandido.value = false
                })
            }
        }
    }


}
