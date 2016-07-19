package kr.ac.hcil.ghhan.mylockscreen

import android.app.Activity
import android.app.AlertDialog
import android.app.KeyguardManager
import android.content.Context
import android.view.Gravity
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.WindowManager
import android.view.WindowManager.LayoutParams

class LockScreenUtils{

    private var mOverlayDialog:OverlayDialog? = null
    private var mLockStatusChangedListener:OnLockStatusChangedListener? = null

    private inner class OverlayDialog: AlertDialog{
        constructor(activity: Activity):super(activity, R.style.OverlayDialog){
            var params: WindowManager.LayoutParams = getWindow().getAttributes()
            params.type = LayoutParams.TYPE_SYSTEM_ERROR
            params.dimAmount = 0.0F
            params.width = 0
            params.height = 0
            params.gravity = Gravity.BOTTOM;
            getWindow().setAttributes(params)
            getWindow().setFlags(LayoutParams.FLAG_SHOW_WHEN_LOCKED or LayoutParams.FLAG_NOT_TOUCH_MODAL,
            0xffffff)
            setOwnerActivity(activity);
            setCancelable(false);
        }

        override  fun dispatchTouchEvent(motionevent: MotionEvent):Boolean{
            return true
        }
    }

    interface OnLockStatusChangedListener{
        fun onLockStatusChanged(isLocked:Boolean)
    }

    fun reset(){
        if (mOverlayDialog != null){

            (mOverlayDialog as OverlayDialog).dismiss()
            mOverlayDialog = null
        }
    }

    fun lock(activity:Activity){
        if(mOverlayDialog == null){
            mOverlayDialog = OverlayDialog(activity)
            (mOverlayDialog as OverlayDialog).show()
            mLockStatusChangedListener = activity as OnLockStatusChangedListener
        }
    }

    fun unlock(){
        if (mOverlayDialog != null){
            (mOverlayDialog as OverlayDialog).dismiss()
            mOverlayDialog = null
            if(mLockStatusChangedListener!= null){
                (mLockStatusChangedListener as OnLockStatusChangedListener).onLockStatusChanged(false)
            }
        }

    }
}

