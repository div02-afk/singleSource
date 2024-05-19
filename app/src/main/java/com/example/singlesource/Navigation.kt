package com.example.singlesource

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun Navigation(){
    val navController = rememberNavController();
    NavHost(navController = navController, startDestination = Screen.HomeScreen.route ){
        composable(route = Screen.HomeScreen.route){
            HomeScreen(navController)
        }
        composable(route = Screen.HostScreen.route){
            HostScreen(navController)
        }
        composable(route = Screen.SpeakerScreen.route){
            SpeakerScreen(navController)
        }
        composable(route = Screen.SettingsScreen.route){
            SettingsScreen(navController)
        }
    }
}