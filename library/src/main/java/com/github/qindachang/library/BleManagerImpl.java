/*
 * Copyright (c) 2017, Qin Dachang
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its contributors may be used to endorse or promote products derived from this
 * software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE
 * USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.github.qindachang.library;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattCharacteristic;

import java.util.Objects;
import java.util.UUID;

/**
 * Created by admin on 2017/1/12.
 */

public abstract class BleManagerImpl {

    public abstract void setConfig(BluetoothConfig config);

    public abstract boolean isSupportBluetooth();

    public abstract boolean isBluetoothOpen();

    public abstract boolean enableBluetooth(Activity activity);

    public abstract boolean enableBluetooth(Activity activity, int requestCode);

    public abstract boolean disableBluetooth();

    public abstract boolean clearDeviceCache();

    public abstract boolean addLeListenerList(LeListener leListener);

    public abstract BleManager setScanWithDeviceName(String deviceName);

    public abstract BleManager setScanWithDeviceName(String[] deviceNames);

    public abstract BleManager setScanWithDeviceAddress(String deviceAddress);

    public abstract BleManager setScanWithDeviceAddress(String[] deviceAddress);

    public abstract BleManager setScanWithServiceUUID(String serviceUUID);

    public abstract BleManager setScanWithServiceUUID(String[] serviceUUIDs);

    public abstract BleManager setScanWithServiceUUID(UUID serviceUUID);

    public abstract BleManager setScanWithServiceUUID(UUID[] serviceUUIDs);

    public abstract BleManager setScanPeriod(int millisecond);

    public abstract BleManager setReportDelay(int reportDelayMillis);

    public abstract void scan();

    public abstract void stopScan();

    public abstract boolean getScanning();

    public abstract BleManager setStopScanAfterConnected(boolean stop);

    public abstract BleManager setRetryConnectEnable(boolean retryConnectEnable);

    public abstract BleManager setConnectTimeoutMillis(int connectTimeoutMillis);

    public abstract BleManager setServiceTimeoutMillis(int serviceTimeoutMillis);

    public abstract BleManager setRetryConnectCount(int retryConnectCount);

    public abstract boolean connect(boolean autoConnect, final BluetoothDevice device);

    public abstract BluetoothDevice getBluetoothDevice();

    public abstract boolean getConnected();

    public abstract boolean getServicesDiscovered();

    public abstract void enableNotifications(boolean enable, UUID serviceUUID, UUID[] characteristicUUIDs);

    public abstract void enableIndicates(boolean enable, UUID serviceUUID, UUID[] characteristicUUIDs);

    public abstract void writeBytesToCharacteristic(byte[] bytes, UUID serviceUUID, UUID characteristicUUID);

    public abstract BluetoothGattCharacteristic getCharacteristic(UUID serviceUUID, UUID characteristicUUID);

    public abstract void writeCharacteristic(BluetoothGattCharacteristic characteristic);

    public abstract void readCharacteristic(UUID serviceUUID, UUID characteristicUUID);


    public abstract void destroy(Object tag);

    public abstract void cancelAllTag();

    public abstract void clearQueue();
}
