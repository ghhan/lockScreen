package kr.ac.hcil.ghhan.mylockscreen

import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context
import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;

class LockScreenActivity : Activity(), LockScreenUtils.OnLockStatusChangedListener{

    private var btnUnlock:Button? = null
    private var mLockscreenUtils:LockScreenUtils? = null

    override fun onAttachedToWindow(){
        this.window.setType(WindowManager.LayoutParams.TYPE_KEYGUARD_DIALOG)
        this.window.addFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN or
                        WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED or
                        WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON or
                        WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
        )
        super.onAttachedToWindow()
    }

    override fun onCreate(savedInstanceState:Bundle){

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lockscreen)
        init()

        if(intent != null && intent.hasExtra("kill") && intent.extras.getInt("kill") == 1){
            enableKeyguard()
            unlockHomeButton()
        }
        else{
            try{
                disableKeyguard()
                lockHomeButton()
                startService(Intent(this, LockScreenService::class.java))

                var phoneStateListener:PhoneStateListener = PhoneStateListener()
                var telephonyManager = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager

                telephonyManager.listen(phoneStateListener, PhoneStateListener.LISTEN_CALL_STATE)
            }
            catch(e:Exception){

            }
        }
    }

    private fun init(){
        mLockscreenUtils = LockScreenUtils()
        btnUnlock = findViewById(R.id.btnUnlock) as Button?
    }


    override fun onLockStatusChanged(isLocked: Boolean){
        if(!isLocked){
            unlockDevice()
        }
    }

    override fun onStop(){
        super.onStop()
        unlockHomeButton()
    }
}
