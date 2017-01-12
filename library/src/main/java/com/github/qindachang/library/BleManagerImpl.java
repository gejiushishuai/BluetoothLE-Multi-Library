package com.github.qindachang.library;

import android.app.Activity;

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

    public abstract boolean enableBluetooth(Activity activity,int requestCode);

    public abstract boolean disableBluetooth();

    public abstract boolean clearDeviceCache();

    public abstract boolean addLeListenerList(LeListener leListener);

    public abstract void scan(Activity activity, List<String> filterDeviceNameList, List<String> filterDeviceAddressList, List<UUID> filerServiceUUIDList,
                              int scanPeriod, int reportDelayMillis);

    public abstract void stopScan();

    public abstract boolean scanning();



}
