package com.example.singlesource

sealed class Screen(val route: String){
    object HomeScreen : Screen( "homeScreen")
    object HostScreen : Screen( "hostScreen")
    object SpeakerScreen : Screen( "speakerScreen")

}