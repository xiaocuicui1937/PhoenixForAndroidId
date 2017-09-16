package com.yalantis.phoenix.sample;

import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

public class TestActivity extends AppCompatActivity implements IScreenState {
    private static final String TAG = "TestActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        TextView sbId = (TextView) findViewById(R.id.sb_id);

        sbId.setText(getSbId());
    }

    /**
     * 9fd8fdf7-b729-3928-8ab3-46496076b798    uuid
     * f13461ea-f823-40cd-a897-123359b8a23a   installId   0397d719-a949-47a9-bd9c-0aa584aabd6c
     *
     * @return
     */
    private String getSbId() {//: 9fd8fdf7-b729-3928-8ab3-46496076b798  9fd8fdf7-b729-3928-8ab3-46496076b798
        String result = null;
        final TelephonyManager tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        final String imei = tm.getDeviceId();
        final WifiManager wifi = (WifiManager) getSystemService(WIFI_SERVICE);
        final String macAddress = wifi.getConnectionInfo().getMacAddress();
        //只有恢复出厂设置的时候值才会改变
        final String androidId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        Log.d(TAG, imei);
        final StringBuilder res = new StringBuilder();
        res.append("imei:").append(imei).append("\nmacAddress:").append(macAddress).append("\nandroidId:").append(androidId);
        Log.d(TAG, res.toString());
        if (!TextUtils.isEmpty(imei) && !"000000000000000".equals(imei)) {
            result = imei;
        } else if (!TextUtils.isEmpty(androidId) && "9774d56d682e549c".equals(androidId)) {
            result = androidId;
        } else {
            final DeviceUuidFactory deviceId = new DeviceUuidFactory(this);
            result = deviceId.getDeviceUuid().toString();
        }


        final DeviceUuidFactory deviceId = new DeviceUuidFactory(this);
        String id = InstalltionId.id(this);
        Log.i(TAG, deviceId.getDeviceUuid().toString() + "\r\n" + id);
        return result;
    }

    @Override
    public void onScreenOn() {
        Log.i(TAG,"开屏");
    }

    @Override
    public void onScreenOff() {
        Log.i(TAG,"关屏");
    }

    @Override
    public void onUserPresent() {
        Log.i(TAG,"解锁");
    }
}
