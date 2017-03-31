package com.github.qindachang.library.conn;

/**
 * Created by qindachang on 2017/3/31.
 */

enum Type {

    gattNull("BluetoothGatt object is null. check connect status or onServicesDiscovered."),
    characteristicNull("characteristic uuid is null.");


    private final String message;
    Type(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
