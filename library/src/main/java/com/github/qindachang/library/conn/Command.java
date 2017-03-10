package com.github.qindachang.library.conn;

import android.bluetooth.BluetoothDevice;

import com.github.qindachang.library.BluetoothConfig;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Created by qindachang on 2017/3/10.
 */

class Command {

    private Set<Listener> mListeners = new LinkedHashSet<>();

    void setConfig(BluetoothConfig config) {

    }

    boolean writeCharacteristic(UUID serviceUUID, UUID characteristicUUID) {
        return false;
    }



    boolean readCharacteristic(UUID serviceUUID, UUID characteristicUUID) {
        return false;
    }

    void enableIndication(boolean enable, UUID serviceUUID, UUID characteristicUUID) {
    }

    void enableNotification(boolean enable, UUID serviceUUID, UUID characteristicUUID) {
    }

    boolean connect(boolean auto, BluetoothDevice bluetoothDevice) {
        return false;
    }

    void addConnectListener(ConnectListener connectListener) {
        mListeners.add(connectListener);
    }

    void disconnect() {

    }

    void close() {

    }

    boolean removeListener(Listener listener) {
        return mListeners.remove(listener);
    }
}
