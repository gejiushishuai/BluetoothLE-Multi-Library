package com.github.qindachang.library.conn;

import android.bluetooth.BluetoothDevice;

import com.github.qindachang.library.BluetoothConfig;

import java.util.UUID;

/**
 * Created by qindachang on 2017/3/9.
 */

public interface BluetoothLeConnector {

    void setConfig(BluetoothConfig config);

    boolean writeCharacteristic(UUID serviceUUID, UUID characteristicUUID);

    void addWriteCharacteristicListener(WriteCharacteristicListener writeCharacteristicListener);

    boolean readCharacteristic(UUID serviceUUID, UUID characteristicUUID);

    void addReadCharacteristicListener(ReadCharacteristicListener readCharacteristicListener);

    void enableIndication(boolean enable, UUID serviceUUID, UUID characteristicUUID);

    void addIndicationListener(IndicationListener indicationListener);

    void enableNotification(boolean enable, UUID serviceUUID, UUID characteristicUUID);

    void addNotificationListener(NotificationListener notificationListener);

    boolean connect(boolean auto, BluetoothDevice bluetoothDevice);

    void addConnectListener(ConnectListener connectListener);

    void disconnect();

    void close();

    boolean removeListener(Listener listener);

}
