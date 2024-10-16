package com.example.mychatroom

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController


@Composable
fun loginScreen(navController: NavController,authViewModel: AuthViewModel = viewModel()){
    var emailInfo by remember { mutableStateOf("") }
    var paswordInfo by remember { mutableStateOf("") }

    val authState = authViewModel.authState.observeAsState()
    val context = LocalContext.current


    LaunchedEffect(authState.value) {
        when(authState.value){
           is AuthViewModel.AuthState.authenticated -> navController.navigate(Screen.ChatScreen.route)
            is AuthViewModel.AuthState.error -> Toast.makeText(context,
                (authState.value as AuthViewModel.AuthState.error).message, Toast.LENGTH_SHORT).show()

           else -> null
        }
    }



    Column( modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally){

        Column (

            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally){
            Text(text = "WELCOME", fontSize = 50.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))
        Image(painter = painterResource(id = R.drawable.login), contentDescription =null )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(value = emailInfo, onValueChange ={emailInfo = it}, label = { Text(text = "ENTER YOUR EMAIL") } )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(value = paswordInfo, onValueChange ={paswordInfo = it} ,label = { Text(text = "ENTER YOUR PASSWORD") },visualTransformation = PasswordVisualTransformation())
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {authViewModel.login(emailInfo,paswordInfo)},enabled = authState.value != AuthViewModel.AuthState.loading) {
            Text(text = "LOGIN")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "CREATE NEW ACCOUNT?", Modifier.clickable { navController.navigate(Screen.SignupScreen.route) })
        Spacer(modifier = Modifier.height(32.dp))
        Row(modifier = Modifier
            .fillMaxWidth()
            , horizontalArrangement = Arrangement.SpaceEvenly) {

            IconButton(onClick = { }, Modifier.size(60.dp)) {
                Image(painter = painterResource(id = R.drawable.facebook), contentDescription =null, Modifier.size(75.dp))
            }
            IconButton(onClick = { /*TODO*/ }, Modifier.size(60.dp)) {
                Image(painter = painterResource(id = R.drawable.google), contentDescription =null, Modifier.size(75.dp))
            }


        }
    }
}