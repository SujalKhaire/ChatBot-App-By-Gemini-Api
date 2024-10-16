package com.example.mychatroom

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


@Composable
fun Navigation(navController: NavHostController= rememberNavController(),chatViewModel: ChatViewModel,modifier: Modifier  ){
    NavHost(navController = navController, startDestination = Screen.LoginScreen.route ){
        composable(Screen.LoginScreen.route){
            loginScreen(navController = navController)
        }
        composable(Screen.SignupScreen.route){
            SignUpScreen(navController = navController)
        }
        composable(Screen.ChatScreen.route){
            chatScreen(navController = navController, modifier =modifier,
                 chatViewModel = chatViewModel
            )
        }
    }
}