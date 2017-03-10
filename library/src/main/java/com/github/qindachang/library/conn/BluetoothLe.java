package com.github.qindachang.library.conn;

/**
 * Created by qindachang on 2017/3/10.
 */

public final class BluetoothLe {
    private BluetoothLe() {
        //no instance
    }
    public static BluetoothLeConnectorImpl newConnector() {
        return new BluetoothLeConnectorImpl();
    }
}
