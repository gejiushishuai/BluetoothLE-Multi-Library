package com.github.qindachang.library.conn;

import android.bluetooth.BluetoothGattCharacteristic;

import com.github.qindachang.library.exception.BleException;

/**
 * Created by qindachang on 2017/3/10.
 */

public interface NotificationListener extends Listener {
    void onNotify(BluetoothGattCharacteristic characteristic);

    void error(BleException e);
}
