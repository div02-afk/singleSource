package com.example.singlesource

import NetworkConstants
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.*
@Composable
fun SettingsScreen(navController: NavController){
    var text by remember { mutableStateOf(NetworkConstants.NAME) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 30.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Settings",fontSize = 25.sp)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical =200.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            TextField(
                value = text,
                onValueChange = { newText ->
                    text = newText
                },
                label = { Text("Change Name") }
            )
            Spacer(modifier = Modifier.padding(vertical = 30.dp))
            Button(onClick = { NetworkConstants.NAME = text }) {
                Text(text = "Save")
            }
        }

    }
}

