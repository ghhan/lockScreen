package kr.ac.hcil.ghhan.mylockscreen

import android.app.Notification
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Intent
import android.content.IntentFilter

import android.os.IBinder
import android.provider.SyncStateContract.Constants
import android.support.v7.app.NotificationCompat

import android.graphics.Bitmap

class LockScreenService : Service(){

    private var mReceiver:BroadcastReceiver? = null

    override fun onBind(intent: Intent): IBinder?{
        return null
    }

    override fun onCreate(){
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int{
        var filter:IntentFilter = IntentFilter(Intent.ACTION_SCREEN_ON)
        filter.addAction(Intent.ACTION_SCREEN_OFF)
        mReceiver = LockScreenIntentReceiver()
        registerReceiver(mReceiver, filter)
        startForeground()
        return START_STICKY
    }

    override fun onDestroy(){
        super.onDestroy()
        unregisterReceiver(mReceiver)
    }

    private fun startForeground(){
        var notification:Notification = NotificationCompat.Builder(this)
                .setContentTitle(getResources().getString(R.string.app_name))
                .setTicker(getResources().getString(R.string.app_name))
                .setContentText("Running")
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentIntent(null)
                .setOngoing(true)
                .build()
        startForeground(9999,notification)
    }

}
