package com.example.singlesource


import android.content.Context
import android.media.AudioManager
import androidx.core.content.ContextCompat.getSystemService
import java.net.BindException
import java.net.DatagramPacket
import java.net.DatagramSocket
import kotlin.concurrent.thread
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class Connection {
    val discoveredSpeakers = mutableListOf<String>()
    val discoveredHosts = mutableListOf<String>()
    var host = "";
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



}

fun announcePresence(scope: CoroutineScope, role: String): Job {
    return scope.launch(Dispatchers.IO) {
        try {
            val socket = DatagramSocket()
            socket.broadcast = true
            val message = role.toByteArray()
            val packet = DatagramPacket(
                message,
                message.size,
                getBroadcastAddress(),
                NetworkConstants.BROADCAST_PORT
            )
            while (isActive) {
                if (role == "") {
                    continue
                }
                socket.send(packet)
                delay(2000) // Announce every 2 seconds
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}