package com.github.qindachang.library.conn;

import android.bluetooth.BluetoothGattCharacteristic;

import com.github.qindachang.library.exception.WriteBleException;

/**
 * Created by qindachang on 2017/3/10.
 */

public interface WriteCharacteristicListener extends Listener {
    void onWrited(BluetoothGattCharacteristic characteristic);

    void error(WriteBleException e);
}
