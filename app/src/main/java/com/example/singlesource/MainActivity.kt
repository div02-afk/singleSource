package com.example.singlesource

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import NetworkConstants
import android.content.SharedPreferences
import android.media.AudioAttributes
import android.media.AudioFocusRequest
import android.media.AudioManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.content.ContextCompat
import java.net.*


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
        NetworkConstants.NAME = loadName();
        println("The broadcast address is ${NetworkConstants.BROADCAST_IP}");

        enableEdgeToEdge()
        setContent {
            Navigation(this)
        }
        getAudioPermission();
    }
     fun saveName(name:String){
        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("name", name)
        editor.apply()
    }
    private fun loadName(): String {
        val sharedPreferences: SharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        return sharedPreferences.getString("name", "Joe") ?: "Joe"
    }
    private fun getAudioPermission(){



    }
}


class NetworkChangeReceiver(private val callback: () -> Unit) : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == ConnectivityManager.CONNECTIVITY_ACTION) {
            val connectivityManager =
                context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = connectivityManager.activeNetworkInfo
            val isConnected = networkInfo != null && networkInfo.isConnected

            // Call the callback function
            NetworkConstants.BROADCAST_IP = getBroadcastAddress();
            println("IP changed");
        }
    }
}



