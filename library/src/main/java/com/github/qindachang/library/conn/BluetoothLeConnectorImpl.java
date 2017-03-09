package com.github.qindachang.library.conn;


import android.bluetooth.BluetoothDevice;

/**
 * Created by qindachang on 2017/3/9.
 */

class BluetoothLeConnectorImpl extends BluetoothLeConnector {

    @Override
    public boolean writeCharacteristic() {
        return false;
    }

    @Override
    public boolean readCharacteristic() {
        return false;
    }

    @Override
    public void enableIndicate() {

    }

    @Override
    public void enableNotification() {

    }

    @Override
    public boolean connect(boolean auto, BluetoothDevice bluetoothDevice) {
        return false;
    }

    @Override
    public void addConnectListener(ConnectListener connectListener) {

    }

    @Override
    public void disConnect() {

    }

    @Override
    public void close() {

    }
}
