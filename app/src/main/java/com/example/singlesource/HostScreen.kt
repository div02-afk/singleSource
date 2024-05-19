package com.example.singlesource

import NetworkConstants
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
import kotlinx.coroutines.launch
import java.net.BindException
import java.net.DatagramPacket
import java.net.DatagramSocket
import kotlin.concurrent.thread

@Composable
fun HostScreen(navController: NavController){
    val discoveredSpeakers = mutableListOf<String>()
    var discoveredNames by remember { mutableStateOf(listOf<String>(NetworkConstants.NAME)) }
    announcePresence(NetworkConstants.ROLE_HOST)
    println("joined");
    val coroutineScope = rememberCoroutineScope()
    val updatedDiscoveredNames by rememberUpdatedState(discoveredNames)


    listenForAnnouncements { role, address ->
        if (role.substring(0,NetworkConstants.ROLE_SPEAKER.length) == NetworkConstants.ROLE_SPEAKER && !discoveredSpeakers.contains(address)) {
            val name = role.substring(NetworkConstants.ROLE_SPEAKER.length);
            if (address != null) {
                discoveredSpeakers.add(address)
                coroutineScope.launch {
                    discoveredNames = updatedDiscoveredNames + name
                }
            }
            println("Found speaker: $address - ${name}, total ${discoveredSpeakers.size}")

        }
    }
    Column (
        modifier = Modifier.fillMaxSize(),

    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick = {

            navController.navigate(Screen.HomeScreen.route) }) {
            Text(text = "Back")
        }

        Text(text = "Host Screen", textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth(), fontSize = 30.sp)
        LazyColumn {
            items(discoveredNames) { name ->
                Row (
                    modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ){
                    Text(text = name, fontSize = 20.sp, modifier = Modifier.padding(20.dp));
                }

            }
        }
    }

}


fun listenForAnnouncements(onDeviceFound: (String, String?) -> Unit) {
    thread {
        try {
            val listenSocket = DatagramSocket(NetworkConstants.BROADCAST_PORT).apply {
                reuseAddress = true
            }
            val buffer = ByteArray(1024)
            while (true) {
                val packet = DatagramPacket(buffer, buffer.size)
                listenSocket?.receive(packet)
                val message = String(packet.data, 0, packet.length)
                val address = packet.address.hostAddress
                onDeviceFound(message, address)
            }
        } catch (e: BindException) {
            e.printStackTrace()
            // Handle the error, possibly by retrying with a different port
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}