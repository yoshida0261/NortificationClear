// Copyright (c) 2019. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
// Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
// Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
// Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
// Vestibulum commodo. Ut rhoncus gravida arcu.

package com.stah.nortificationclear

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import java.util.concurrent.TimeUnit




class MainActivity : AppCompatActivity() {

    private val demoModeChangeReceiver = object : BroadcastReceiver() {
        override fun onReceive(p0: Context?, p1: Intent?) {
            /*
                   updateFromReceiver = true
                   updateDemoModeSwitch()
                   updateFromReceiver = false
                   */
        }
    }


    override fun onStart() {
        super.onStart()


        /** Returns `true` if the required permissions to send out demo mode broadcasts are available, or `false` if they need to be requested first. */

        if(ContextCompat.checkSelfPermission(applicationContext, "android.permission.DUMP") == PackageManager.PERMISSION_GRANTED){
            println("android permission dump")
        }

        /** Returns `true` if the demo mode has been activated in system UI. **/

        if(Settings.Global.getInt(applicationContext.contentResolver, "sysui_demo_allowed", 0) == 1){
            println("sysui_demo_allowed")
        }


        registerReceiver(demoModeChangeReceiver, IntentFilter("com.android.systemui.demo"))
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(demoModeChangeReceiver)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sendDemoCommand("bars", "mode" to "transparent")
        sendDemoCommand("notifications", "visible" to false)
        sendDemoCommand("network", "wifi" to "show")
        sendDemoCommand("network", "wifi" to "show", "level" to 4)


        sendDemoCommand("network", "mobile" to "show")
        sendDemoCommand("network", "mobile" to "show", "level" to 4)

        sendDemoCommand("battery", "level" to 100)
        sendDemoCommand("battery", "plugged" to false)
        Thread.sleep(1000)
        sendDemoCommand("network", "fully" to true)


    }


    private fun sendDemoCommand(command: String, vararg extras: Pair<String, Any>) {
        val intent = Intent("com.android.systemui.demo").apply {
            println("command:$command")
            putExtra("command", command)
            for ((key, value) in extras) {
                putExtra(key, value.toString())
                println("key:$key value:$value")
            }
        }

        applicationContext.sendBroadcast(intent)
    }

    val DEFAULT_STATUS_HIDDEN = "hidden"

    /**
     * Configure the status icons (alarm, volume, bluetooth, etc.)
     */
    //val status = Status()


}
