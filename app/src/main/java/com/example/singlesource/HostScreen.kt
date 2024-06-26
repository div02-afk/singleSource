package com.example.singlesource

import NetworkConstants
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


@Composable
fun HostScreen(navController: NavController){
    var newConnection = Connection();
    val coroutineScope = rememberCoroutineScope()
    var announceJob: Job? by remember { mutableStateOf(null) }
    LaunchedEffect(Unit) {
        announceJob = announcePresence(coroutineScope, NetworkConstants.ROLE_HOST + NetworkConstants.NAME)
    }
    var discoveredNames by remember { mutableStateOf(listOf<String>()) }
    val updatedDiscoveredNames by rememberUpdatedState(discoveredNames)
    newConnection.listenForAnnouncements { role, address ->
        println("got a connection : $role")
        if (role.substring(0,NetworkConstants.ROLE_SPEAKER.length) == NetworkConstants.ROLE_SPEAKER && !newConnection.discoveredSpeakers.contains(address) && role.endsWith(NetworkConstants.NAME)) {
            val name = role.substring(NetworkConstants.ROLE_SPEAKER.length,role.indexOf(NetworkConstants.NAME))
            if (address != null) {
                newConnection.discoveredSpeakers.add(address)
                coroutineScope.launch {
                    discoveredNames = updatedDiscoveredNames + name
                }
            }
            println("Speaker found: $name");


        }
    }
    BackHandler {
        announceJob?.cancel();
        navController.navigate(Screen.HomeScreen.route);
    }
    Column (
        modifier = Modifier.fillMaxSize(),

    ) {
        Spacer(modifier = Modifier.height(30.dp))
        Button(onClick = {
            announceJob?.cancel()
            newConnection = Connection();
            navController.navigate(Screen.HomeScreen.route)

                         }
            , modifier = Modifier.padding(horizontal = 10.dp)
        ) {
            Text(text = "<", fontSize = 20.sp)
        }

        Text(text = "Host Screen", textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth(), fontSize = 30.sp)
        LazyColumn {
            items(discoveredNames) { name ->
                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ){
                    Text(text = name, fontSize = 20.sp, modifier = Modifier.padding(20.dp));
                }

            }
        }
    }

}


