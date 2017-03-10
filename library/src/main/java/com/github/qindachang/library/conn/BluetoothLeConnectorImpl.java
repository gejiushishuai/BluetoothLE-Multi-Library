package com.github.qindachang.library.conn;


import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattCharacteristic;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;

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
    public void setConfig(@NonNull BluetoothConfig config) {
        mCommand.setConfig(config);
    }

    @Override
    public boolean writeCharacteristic(byte[] bytes,@NonNull  UUID serviceUUID,@NonNull  UUID characteristicUUID) {
        return mCommand.writeCharacteristic(bytes, serviceUUID, characteristicUUID);
    }

    @Override
    public boolean writeCharacteristic(BluetoothGattCharacteristic characteristic) {
        return mCommand.write(characteristic);
    }

    @Override
    public void addWriteCharacteristicListener(WriteCharacteristicListener writeCharacteristicListener) {

    }

    @Override
    public boolean readCharacteristic(@NonNull UUID serviceUUID,@NonNull  UUID characteristicUUID) {
        return mCommand.readCharacteristic(serviceUUID, characteristicUUID);
    }

    @Override
    public void addReadCharacteristicListener(ReadCharacteristicListener readCharacteristicListener) {

    }

    @Override
    public void enableIndication(boolean enable,@NonNull  UUID serviceUUID,@NonNull  UUID characteristicUUID) {
        mCommand.enableIndication(enable, serviceUUID, characteristicUUID);
    }

    @Override
    public void addIndicationListener(IndicationListener indicationListener) {

    }

    @Override
    public void enableNotification(boolean enable,@NonNull  UUID serviceUUID,@NonNull  UUID characteristicUUID) {
        mCommand.enableNotification(enable, serviceUUID, characteristicUUID);
    }

    @Override
    public void addNotificationListener(NotificationListener notificationListener) {

    }

    @Override
    public boolean connect(boolean autoConnect, @NonNull BluetoothDevice bluetoothDevice) {
        return mCommand.connect(autoConnect, bluetoothDevice);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public boolean connect(boolean autoConnect,@NonNull  BluetoothDevice bluetoothDevice, int TRANSPORT) {
        return mCommand.connect(autoConnect, bluetoothDevice, TRANSPORT);
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

    @Override
    public BluetoothDevice getBluetoothDevice() {
        return mCommand.getBluetoothDevice();
    }
}
