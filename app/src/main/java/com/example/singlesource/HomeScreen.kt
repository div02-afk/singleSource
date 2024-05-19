package com.example.singlesource

import NetworkConstants
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.ui.Modifier
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun HomeScreen (navController: NavController){
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(40.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically

    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Text(text = "Hello there ${NetworkConstants.NAME}", fontSize = 20.sp)



        }
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(30.dp),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically

    ) {
        Button(onClick = { navController.navigate(Screen.SettingsScreen.route) }) {
            Text(text = "*",Modifier.padding(1.dp), fontSize = 20.sp)
        }
    }

    Column(
modifier = Modifier.fillMaxSize(),
verticalArrangement = Arrangement.Center,
horizontalAlignment = Alignment.CenterHorizontally

) {

    Button(onClick = { navController.navigate(Screen.HostScreen.route)  }) {
        Text(text = "   Host   ",fontSize = 32.sp)
    }
    Spacer(modifier = Modifier.height(16.dp) )
    Button(onClick = { navController.navigate(Screen.SpeakerScreen.route)  }) {
        Text(text = "Speaker",fontSize = 32.sp)
    }
}
}

