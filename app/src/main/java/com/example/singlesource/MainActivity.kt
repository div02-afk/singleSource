package com.example.singlesource

import NetworkConstants
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import java.net.*
import kotlin.concurrent.thread
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

fun getBroadcastAddress(): InetAddress? {
    val interfaces = NetworkInterface.getNetworkInterfaces()
    while (interfaces.hasMoreElements()) {
        val networkInterface = interfaces.nextElement()
        if (networkInterface.isLoopback || !networkInterface.isUp) continue

        val interfaceAddresses = networkInterface.interfaceAddresses
        for (address in interfaceAddresses) {
            val broadcast = address.broadcast
            if (broadcast != null) {
                return broadcast
            }
        }
    }
    return null
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        NetworkConstants.BROADCAST_IP = getBroadcastAddress();
        println("The broadcast address is ${NetworkConstants.BROADCAST_IP}");
        enableEdgeToEdge()
        setContent {
            Navigation()
        }
    }
}



