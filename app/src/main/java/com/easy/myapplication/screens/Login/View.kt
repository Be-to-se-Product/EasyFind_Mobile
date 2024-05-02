package com.easy.myapplication.screens.Login

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.easy.myapplication.R
import com.easy.myapplication.shared.Subtitle.Subtitle
import com.easy.myapplication.shared.Title.Title
import com.easy.myapplication.ui.theme.Primary
import com.easy.myapplication.ui.theme.Seconday

@Composable
fun Login(navController: NavHostController, modifier: Modifier = Modifier) {
    Surface(color = Color(0xFF292929)) {

        val switch = remember { mutableStateOf(true) }
        val context = LocalContext.current
        val model = Model(context)

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

        Column(modifier = Modifier.verticalScroll(rememberScrollState()).padding(PaddingValues(top = 25.dp))) {
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
                        modifier = Modifier.fillMaxSize(0.2f)
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
            Spacer(modifier = Modifier.height(30.dp))

            Box() {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth(1f)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(.7f)
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
                                        .fillMaxSize()
                                        .padding(0.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = animatedButtonEnabled.value
                                    ),
                                    onClick = {
                                        switch.value = !switch.value
                                    },
                                    shape = RoundedCornerShape(0)
                                ) {
                                    Text(
                                        text = "LOGIN",
                                        fontSize = 12.sp,
                                        color = if (!switch.value) Color.White else Color.Black,
                                        textAlign = TextAlign.Center)
                                }
                            }

                            Column(
                                modifier = Modifier
                                    .background(Seconday)
                                .fillMaxWidth(1f),
                            ) {
                                Button(
                                    modifier = Modifier
                                        .fillMaxSize()
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
                                        text = "CADASTRO",
                                        fontSize = 12.sp,
                                        color = if (!switch.value) Color.Black else Color.White,
                                        modifier = Modifier.fillMaxWidth(1f),
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(30.dp))
            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth(1f)) {
                NavHost(modifier = modifier,navController = navController, startDestination = "LOGIN" ){
                    composable("LOGIN"){
                        login(model)
                    }
                    composable("CADASTRO"){
                        cadastro(model)
                    }
                }
                if(switch.value){
                    navController.navigate("LOGIN")
                }else{
                    navController.navigate("CADASTRO")
                }
            }
        }
    }