package com.github.qindachang.library;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.util.Log;

/**
 * Created by qindachang on 2017/3/10.
 */

public class BluetoothUtils {
    private static final String TAG = BluetoothUtils.class.getSimpleName();

    private BluetoothUtils() {
        //no instance
    }

    public static boolean isSupportBluetooth() {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        return bluetoothAdapter != null;
    }

    public static boolean isBluetoothEnabled() {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        return bluetoothAdapter.isEnabled();
    }

    public static boolean enableBluetooth(Activity activity) {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            Log.e(TAG, "The device/phone does not support bluetooth. ");
            return false;
        }
        if (bluetoothAdapter.isEnabled()) {
            Log.e(TAG, "The device/phone has been turn on bluetooth.");
            return false;
        }
        Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        activity.startActivity(intent);
        return true;
    }

    public static boolean enableBluetooth(Activity activity, int requestCode) {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            Log.e(TAG, "The device/phone does not support bluetooth. ");
            return false;
        }
        if (bluetoothAdapter.isEnabled()) {
            Log.e(TAG, "The device/phone has been turn on bluetooth.");
            return false;
        }
        Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        activity.startActivityForResult(intent, requestCode);
        return true;
    }

    public static boolean disableBluetooth() {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter.isEnabled()) {
            bluetoothAdapter.disable();
            return true;
        } else {
            Log.e(TAG, "The device/phone has been turn off Bluetooth.");
            return false;
        }
    }
}
