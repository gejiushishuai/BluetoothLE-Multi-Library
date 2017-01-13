package com.github.qindachang.library;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;

import java.util.List;
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



}
