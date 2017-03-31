package com.github.qindachang.library.conn;

import android.bluetooth.BluetoothGattCharacteristic;

import com.github.qindachang.library.exception.ReadBleException;

/**
 * Created by qindachang on 2017/3/10.
 */

public interface ReadCharacteristicListener extends Listener {
    void onReaded(BluetoothGattCharacteristic characteristic);

    void error(ReadBleException e);
}
