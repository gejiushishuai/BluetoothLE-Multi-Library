package com.github.qindachang.library.conn;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattCharacteristic;

/**
 * Created by qindachang on 2017/3/9.
 */

public abstract class BluetoothLeConnector {

    public static BluetoothLeConnectorImpl newConnector() {
        return new BluetoothLeConnectorImpl();
    }

    public abstract boolean writeCharacteristic(BluetoothGattCharacteristic characteristic);

    public abstract boolean readCharacteristic(BluetoothGattCharacteristic characteristic);

    public abstract void enableIndicate(boolean enable, BluetoothGattCharacteristic characteristic);

    public abstract void enableNotification(boolean enable, BluetoothGattCharacteristic characteristic);

    public abstract boolean connect(boolean auto, BluetoothDevice bluetoothDevice);

    public abstract void addConnectListener(ConnectListener connectListener);

    public abstract void disConnect();

    public abstract void close();

}
