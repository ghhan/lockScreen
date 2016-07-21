package kr.ac.hcil.ghhan.mylockscreen

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

class LockScreenIntentReceiver : BroadcastReceiver(){

    override fun onReceive(context: Context?, intent: Intent?)  {

        if( intent!!.action.equals(Intent.ACTION_SCREEN_OFF)
                || intent.action.equals(Intent.ACTION_SCREEN_ON)
                || intent.action.equals(Intent.ACTION_BOOT_COMPLETED)) {
            start_lockscreen(context)
        }
    }

    private fun start_lockscreen(context: Context?){

       var mIntent = Intent(context, LockScreenActivity::class.java)
        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context!!.startActivity(mIntent)
    }
}
