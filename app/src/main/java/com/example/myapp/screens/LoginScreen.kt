package com.example.myapp.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.navigation.NavController
import com.example.myapp.compose.TopBar
import com.example.myapp.compose.TopBarWithBackArrow


@Composable
fun Login(navController: NavController) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            // 内容从上到下排布
            verticalArrangement = Arrangement.Top,

            // 水平上居中
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            TopBar(
                title = { Text(text = "登录") }
            )
            TextField(
                value = username,
                onValueChange = { value ->
                    username = value
                },
                label = {
                    Text("username") // TODO: add to strings.xml, replaced by stringResource
                },
                singleLine = true,

                )
            TextField(
                value = password,
                onValueChange = { value ->
                    password = value
                },
                label = {
                    Text("password") // TODO: add to strings.xml, replaced by stringResource
                },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation()
            )
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Button(
                    onClick = {

                        navController.navigate("content") {
                            popUpTo("login") { inclusive = true }
                        }
                    }
                ) {
                    Text("Sign in") // TODO: add to strings.xml, replaced by stringResource
                }
                Button(
                    onClick = {
                        navController.navigate("signUp")
                    }
                ) {
                    Text("Sign up") // TODO: add to strings.xml, replaced by stringResource
                }
            }
            TextButton(onClick = { navController.navigate("forgetPassword") }) {
                Text("forget password?")
            }

        }
    }
}