package com.example.singlesource

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import java.net.BindException
import java.net.DatagramPacket
import java.net.DatagramSocket
import kotlin.concurrent.thread
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf

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
    fun announcePresence(role: String) {
        thread {
            try {
                val socket = DatagramSocket()
                socket.broadcast = true;
                val message = role.toByteArray()
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
}