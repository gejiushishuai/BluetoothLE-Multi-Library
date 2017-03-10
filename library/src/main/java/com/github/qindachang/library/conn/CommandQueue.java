package com.github.qindachang.library.conn;

import android.bluetooth.BluetoothGattCharacteristic;

/**
 * Created by qindachang on 2017/3/10.
 */

class CommandQueue {

    enum Type {
        WRITE,
        READ,
        NOTIFY,
        INDICATE
    }
    public final CommandQueue.Type type;
    private final BluetoothGattCharacteristic characteristic;
    private boolean enable;

    private CommandQueue(final Type type, final BluetoothGattCharacteristic characteristic) {
        this.type = type;
        this.characteristic = characteristic;
    }

    private CommandQueue(final Type type, final BluetoothGattCharacteristic characteristic, boolean enable) {
        this.type = type;
        this.characteristic = characteristic;
        this.enable = enable;
    }

    public static CommandQueue newReadRequest(final BluetoothGattCharacteristic characteristic) {
        return new CommandQueue(Type.READ, characteristic);
    }

    public static CommandQueue newWriteRequest(final BluetoothGattCharacteristic characteristic) {
        return new CommandQueue(Type.WRITE, characteristic);
    }

    public static CommandQueue newEnableNotificationsRequest(final boolean enable, final BluetoothGattCharacteristic characteristic) {
        return new CommandQueue(Type.NOTIFY, characteristic, enable);
    }

    public static CommandQueue newEnableIndicationsRequest(final boolean enable, final BluetoothGattCharacteristic characteristic) {
        return new CommandQueue(Type.INDICATE, characteristic, enable);
    }


    public BluetoothGattCharacteristic getCharacteristic() {
        return characteristic;
    }

    public boolean isEnable() {
        return enable;
    }
}
