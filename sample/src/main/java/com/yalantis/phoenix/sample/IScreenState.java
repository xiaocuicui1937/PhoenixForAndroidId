package com.yalantis.phoenix.sample;

/**
 * Created by zxkjc3 on 2017/9/16.
 */

public interface IScreenState {
    void onScreenOn();
    void onScreenOff();
    void onUserPresent();//解锁
}
