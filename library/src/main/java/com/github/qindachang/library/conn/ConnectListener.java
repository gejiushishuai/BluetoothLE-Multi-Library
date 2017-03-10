package com.github.qindachang.library.conn;

import android.bluetooth.BluetoothGatt;

import com.github.qindachang.library.exception.ConnBleException;

/**
 * Created by qindachang on 2017/3/9.
 */

public interface ConnectListener extends Listener {
    void connecting();

    void connected();

    void disconnected();

    void onServicesDiscovered(BluetoothGatt gatt, int status);

    void error(ConnBleException e);
}
