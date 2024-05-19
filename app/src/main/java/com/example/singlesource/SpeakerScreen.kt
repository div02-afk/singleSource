package com.example.singlesource

import NetworkConstants
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress
import kotlin.concurrent.thread
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SpeakerScreen(navController: NavController){
    announcePresence(NetworkConstants.ROLE_SPEAKER)
    Column (
        modifier = Modifier.fillMaxSize(),

        ) {
        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick = { navController.navigate(Screen.HomeScreen.route) }) {
            Text(text = "Back")
        }

        Text(text = "Speaker Screen", textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth(), fontSize = 30.sp)

    }

}

fun announcePresence(role: String) {
    thread {
        try {
            val socket = DatagramSocket()
            socket.broadcast = true;
            val messageToSend = role + NetworkConstants.NAME;
            val message = messageToSend.toByteArray()
            val packet = DatagramPacket(
                message,
                message.size,
                getBroadcastAddress(),
                NetworkConstants.BROADCAST_PORT
            )
            while (true) {
                socket.send(packet)
                Thread.sleep(2000) // Announce every 2 seconds
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}