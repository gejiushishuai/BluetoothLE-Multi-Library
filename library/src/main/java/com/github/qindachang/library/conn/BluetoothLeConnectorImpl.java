package com.github.qindachang.library.conn;


import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattCharacteristic;

import com.github.qindachang.library.BluetoothConfig;

import java.util.UUID;

/**
 * Created by qindachang on 2017/3/9.
 */

class BluetoothLeConnectorImpl implements BluetoothLeConnector {

    private Command mCommand;

    BluetoothLeConnectorImpl() {
        mCommand = new Command();
    }

    @Override
    public void setConfig(BluetoothConfig config) {
        mCommand.setConfig(config);
    }

    @Override
    public boolean writeCharacteristic(byte[] bytes, UUID serviceUUID, UUID characteristicUUID) {
        return mCommand.writeCharacteristic(bytes, serviceUUID, characteristicUUID);
    }

    @Override
    public boolean writeCharacteristic(BluetoothGattCharacteristic characteristic) {
        return false;
    }

    @Override
    public void addWriteCharacteristicListener(WriteCharacteristicListener writeCharacteristicListener) {

    }

    @Override
    public boolean readCharacteristic(UUID serviceUUID, UUID characteristicUUID) {
        return mCommand.readCharacteristic(serviceUUID, characteristicUUID);
    }

    @Override
    public void addReadCharacteristicListener(ReadCharacteristicListener readCharacteristicListener) {

    }

    @Override
    public void enableIndication(boolean enable, UUID serviceUUID, UUID characteristicUUID) {
        mCommand.enableIndication(enable, serviceUUID, characteristicUUID);
    }

    @Override
    public void addIndicationListener(IndicationListener indicationListener) {

    }

    @Override
    public void enableNotification(boolean enable, UUID serviceUUID, UUID characteristicUUID) {
        mCommand.enableNotification(enable, serviceUUID, characteristicUUID);
    }

    @Override
    public void addNotificationListener(NotificationListener notificationListener) {

    }

    @Override
    public boolean connect(boolean auto, BluetoothDevice bluetoothDevice) {
        return mCommand.connect(auto, bluetoothDevice);
    }

    @Override
    public void addConnectListener(ConnectListener connectListener) {
        mCommand.addConnectListener(connectListener);
    }

    @Override
    public void addRssiListener(int milliseconds, RssiListener rssiListener) {

    }

    @Override
    public void disconnect() {
        mCommand.disconnect();
    }

    @Override
    public void close() {
        mCommand.close();
    }

    @Override
    public boolean removeListener(Listener listener) {
        return mCommand.removeListener(listener);
    }
}
