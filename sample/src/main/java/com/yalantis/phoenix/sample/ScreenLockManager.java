package com.yalantis.phoenix.sample;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.sa.all_cui.quick_lib.fix.cache.SharePreKit;
import com.sa.all_cui.quick_lib.fix.screenlock.ScreenLockDialog;

/**
 * Created by zxkjc3 on 2017/10/21.
 */

@SuppressWarnings("WeakerAccess")
@SuppressLint("NewApi")
public class ScreenLockManager implements IScreenState, Application.ActivityLifecycleCallbacks {
    private static final String TAG = "ScreenLockManager";
    private static final String ISACTIVE = "DialogActive";
    private static int ACTIVE_COUNT = 0;
    private static boolean isBackground = false;
    private ScreenLockDialog mScreenLockDialog = null;
    private SharePreKit mSharePreKit = null;

    public ScreenLockManager(Context context) {
        mSharePreKit = SharePreKit.getInstance(context);
        App.getApp().registerActivityLifecycleCallbacks(this);
    }

    @Override
    public void onScreenOn() {
        mSharePreKit.putBoolean(ISACTIVE, false);
        Log.i(TAG, "开屏");
    }

    @Override
    public void onScreenOff() {
        mSharePreKit.putBoolean(ISACTIVE, true);
        Log.i(TAG, "关屏");
    }

    @Override
    public void onUserPresent() {

        Log.i(TAG, "解锁");
    }

    /**
     * 关闭屏幕后才将ScreenLockDialog销毁掉，减少占用系统内存
     */
    public void onResume(FragmentManager manager) {
        if (mScreenLockDialog == null) {
            mScreenLockDialog = new ScreenLockDialog();
        }
        final boolean isActive = mSharePreKit.getBoolean(ISACTIVE);
        if (!isActive && !mScreenLockDialog.isAdded()) {
            mSharePreKit.putBoolean(ISACTIVE, true);
            mScreenLockDialog.show(manager, "ScreenLockDialog");
        }
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(Activity activity) {
        isBackground = false;
        ACTIVE_COUNT++;
    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {
        ACTIVE_COUNT--;
        if (ACTIVE_COUNT<=0){
            isBackground = true;
        }
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }
    /**
     * 判断是否进入到后台中
     * */
    public boolean isBackgroundState(){
        return isBackground;
    }
}
