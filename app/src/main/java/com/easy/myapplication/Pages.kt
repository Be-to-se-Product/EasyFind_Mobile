package com.easy.myapplication

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.easy.myapplication.ui.theme.Primary
import com.easy.myapplication.ui.theme.Seconday


@Composable
@Preview
fun Login() {


    val switch = remember { mutableStateOf(false) }
    val email = remember {
        mutableStateOf("")
    }

    val senha = remember {
        mutableStateOf("")
    }

    val animatedButtonEnabled = animateColorAsState(
        targetValue = if (switch.value) Primary else Seconday, animationSpec = tween(
            150, 0,
            LinearEasing
        )
    );

    val animatedButtonDisabled = animateColorAsState(
        targetValue = if (switch.value) Seconday else Primary, animationSpec = tween(
            150, 0,
            LinearEasing
        )
    )

    Column {
        Box(
            contentAlignment = Alignment.Center, modifier = Modifier
                .fillMaxWidth(1f)
                .fillMaxHeight(0.3f)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(15.dp),
                modifier = Modifier.fillMaxWidth(1f)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_logo),
                    contentDescription = "Tefdfd",
                    modifier = Modifier.fillMaxSize(0.4f)
                )

                Box {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth(1f)
                    ) {
                        Row() {
                            Title(content = "Bem vindo a Easy Find")

                        }
                        Row {
                            Subtitle(content = "Conectando você ao comércio local")
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Divider(
                            Modifier
                                .border(1.dp, Primary)
                                .fillMaxWidth(0.7f)
                        )
                    }
                }
            }
        }

        Box() {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth(1f)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                ) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(1f)
                    ) {

                        Column(
                            modifier = Modifier
                                .fillMaxWidth(0.5f),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Button(
                                modifier = Modifier
                                    .fillMaxWidth(1f)
                                    .height(30.dp)
                                    .padding(0.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = animatedButtonEnabled.value
                                ),
                                onClick = {
                                    switch.value = !switch.value
                                },
                                shape = RoundedCornerShape(0)
                            ) {
                                Text(text = "Login", fontSize = 11.sp, color = Color.White)
                            }
                        }

                        Column(
                            modifier = Modifier
                                .background(Seconday)
                        ) {
                            Button(
                                modifier = Modifier
                                    .fillMaxWidth(1f)
                                    .height(30.dp)
                                    .padding(0.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = animatedButtonDisabled.value
                                ),
                                onClick = {
                                    switch.value = !switch.value
                                },
                                shape = RoundedCornerShape(0)
                            ) {
                                Text(
                                    text = "Cadastro",
                                    fontSize = 11.sp,
                                    color = if (!switch.value) Color.Black else Color.White,
                                    modifier = Modifier.fillMaxWidth(1f)
                                )
                            }
                        }
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(30.dp))
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth(1f)) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth(0.8f)
            ) {
                Input(value = email.value, onValueChange = {email.value = it},label="Email")
                Spacer(modifier = Modifier.height(30.dp))
                Input(value = senha.value, onValueChange = {senha.value = it }, type = Type.PASSWORD, label = "Senha")
                Spacer(modifier = Modifier.height(40.dp))
                ButtonCustomize(onClick = {}){
                    Title(content = "Entrar", fontSize = 14.sp)
                }
            }
        }

    }
}
    
