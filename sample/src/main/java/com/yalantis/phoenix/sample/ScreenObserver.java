package com.yalantis.phoenix.sample;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.PowerManager;

/**
 * Created by zxkjc3 on 2017/9/16.
 * 屏幕事件监听器
 */

public class ScreenObserver {
    private Context mContext;
    private ScreenBroadReceive mScreenBroadReceiver;
    private IScreenState mState;

    private ScreenObserver(Context context, ScreenBroadReceive screenReceive) {
        if (context == null) {
            throw new IllegalArgumentException("Context must not be null!");
        }
        if (screenReceive == null) {
            throw new IllegalArgumentException("ScreenBroadReceive must not be null!");
        }
        this.mContext = context;
        this.mScreenBroadReceiver = screenReceive;
    }

    public static void create(Context context, ScreenBroadReceive receive) {
        final ScreenObserver observer = new ScreenObserver(context, receive);
    }

    private void regesterObserve(IScreenState state) {
        this.mState = state;
        //注册监听过滤器
        registerListenerFilter();
        getScreenState();
    }

    //注销广播接收者
    private void unRegesterObserve() {
        if (mScreenBroadReceiver != null) {
            mContext.unregisterReceiver(mScreenBroadReceiver);
        }
    }

    private void registerListenerFilter() {
        final IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_USER_PRESENT);
        if (mScreenBroadReceiver != null) {
            mContext.registerReceiver(mScreenBroadReceiver, filter);
        }
    }

    @SuppressLint("NewApi")
    private void getScreenState() {
        final PowerManager powerManager = (PowerManager) mContext.getSystemService(Context.POWER_SERVICE);
        if (powerManager.isInteractive()) {
            if (mState != null) {
                mState.onScreenOn();
            }
        } else {
            if (mState != null) {
                mState.onScreenOff();
            }
        }
    }
}
