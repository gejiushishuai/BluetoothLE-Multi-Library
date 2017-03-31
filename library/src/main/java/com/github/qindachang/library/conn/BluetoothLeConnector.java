package com.github.qindachang.library.conn;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattCharacteristic;
import android.os.Build;

import com.github.qindachang.library.BluetoothConfig;

import java.util.UUID;

/**
 * Created by qindachang on 2017/3/9.
 */

public interface BluetoothLeConnector {

    int TRANSPORT_AUTO = 0x00000000;
    int TRANSPORT_BREDR = 0x00000001;
    int TRANSPORT_LE = 0x00000002;

    void setConfig(BluetoothConfig config);

    boolean writeCharacteristic(byte[] bytes, UUID serviceUUID, UUID characteristicUUID);

    boolean writeCharacteristic(BluetoothGattCharacteristic characteristic);

    void addWriteCharacteristicListener(WriteCharacteristicListener writeCharacteristicListener);

    boolean readCharacteristic(UUID serviceUUID, UUID characteristicUUID);

    void addReadCharacteristicListener(ReadCharacteristicListener readCharacteristicListener);

    void enableIndication(boolean enable, UUID serviceUUID, UUID characteristicUUID);

    void addIndicationListener(IndicationListener indicationListener);

    void enableNotification(boolean enable, UUID serviceUUID, UUID characteristicUUID);

    void addNotificationListener(NotificationListener notificationListener);

    boolean connect(boolean autoConnect, BluetoothDevice bluetoothDevice);

    /**
     * @param autoConnect     Whether to directly connect to the remote device (false)
     *                        or to automatically connect as soon as the remote
     *                        device becomes available (true).
     * @param bluetoothDevice BluetoothDevice
     * @param TRANSPORT       TRANSPORT_AUTO, No preferrence of physical transport for GATT connections to remote dual-mode devices;
     *                        TRANSPORT_BREDR, Prefer BR/EDR transport for GATT connections to remote dual-mode devices;
     *                        TRANSPORT_LE, Prefer LE transport for GATT connections to remote dual-mode devices.
     *                        <p/>
     *                        增强数据率BR/EDR配置文件包括： 耳机（HSP）、对象交换（OBEX）、音频传输（A2DP）、视频传输（VDP） 和文件传输（FTP）
     *                        <p/>
     *                        设备被贴上了“BluetoothSmart” 标签，代表属于蓝牙4.x LE的类型
     * @return connected
     */
    @android.support.annotation.RequiresApi(api = Build.VERSION_CODES.M)
    boolean connect(boolean autoConnect, BluetoothDevice bluetoothDevice, int TRANSPORT);

    void addConnectListener(ConnectListener connectListener);

    /**
     * When you set the Bluetooth signal strength monitoring, Curitiba will automatically start to read .
     *
     * @param milliseconds Read rssi interval time.
     * @param rssiListener RssiListener
     */
    void addRssiListener(int milliseconds, RssiListener rssiListener);

    void stopReadRssi();

    void disconnect();

    void close();

    boolean removeListener(Listener listener);

    /**
     * Clear command queue.
     */
    void clearQueue();

    BluetoothDevice getBluetoothDevice();

    boolean getConnected();
}
