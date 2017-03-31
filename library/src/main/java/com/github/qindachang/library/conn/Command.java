package com.github.qindachang.library.conn;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattServerCallback;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;

import com.github.qindachang.library.BluetoothConfig;
import com.github.qindachang.library.OnLeReadCharacteristicListener;
import com.github.qindachang.library.exception.BleException;
import com.github.qindachang.library.exception.ReadBleException;
import com.github.qindachang.library.exception.WriteBleException;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

/**
 * Created by qindachang on 2017/3/10.
 */

class Command {

    private boolean mConnected;
    private int mRssiIntervalMilliSecond;

    private BluetoothDevice mBluetoothDevice;
    private BluetoothGatt mBluetoothGatt;
    private Set<Listener> mListeners = new LinkedHashSet<>();

    BluetoothGattCallback mGattCallback = new BluetoothGattCallback() {
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            super.onConnectionStateChange(gatt, status, newState);

        }

        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            super.onServicesDiscovered(gatt, status);
        }

        @Override
        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            super.onCharacteristicRead(gatt, characteristic, status);
        }

        @Override
        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            super.onCharacteristicWrite(gatt, characteristic, status);
        }

        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
            super.onCharacteristicChanged(gatt, characteristic);
        }

        @Override
        public void onDescriptorRead(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            super.onDescriptorRead(gatt, descriptor, status);
        }

        @Override
        public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            super.onDescriptorWrite(gatt, descriptor, status);
        }

        @Override
        public void onReliableWriteCompleted(BluetoothGatt gatt, int status) {
            super.onReliableWriteCompleted(gatt, status);
        }

        @Override
        public void onReadRemoteRssi(BluetoothGatt gatt, int rssi, int status) {
            super.onReadRemoteRssi(gatt, rssi, status);
        }

        @Override
        public void onMtuChanged(BluetoothGatt gatt, int mtu, int status) {
            super.onMtuChanged(gatt, mtu, status);
        }
    };

    void setConfig(BluetoothConfig config) {

    }

    boolean writeCharacteristic(byte[] bytes, UUID serviceUUID, UUID characteristicUUID) {
        return false;
    }

    public void addWriteCharacteristicListener(WriteCharacteristicListener writeCharacteristicListener) {
        mListeners.add(writeCharacteristicListener);
    }

    boolean readCharacteristic(UUID serviceUUID, UUID characteristicUUID) {
        return false;
    }

    public void addReadCharacteristicListener(ReadCharacteristicListener readCharacteristicListener) {
        mListeners.add(readCharacteristicListener);
    }

    void enableIndication(boolean enable, UUID serviceUUID, UUID characteristicUUID) {
    }

    public void addIndicationListener(IndicationListener indicationListener) {
        mListeners.add(indicationListener);
    }

    void enableNotification(boolean enable, UUID serviceUUID, UUID characteristicUUID) {
    }

    public void addNotificationListener(NotificationListener notificationListener) {
        mListeners.add(notificationListener);
    }

    boolean connect(boolean auto, BluetoothDevice bluetoothDevice) {
        if (mBluetoothGatt != null) {
            mBluetoothGatt.close();
            mBluetoothGatt = null;
            mConnected = false;
        }
        mBluetoothGatt = bluetoothDevice.connectGatt(null, auto, mGattCallback);
        return mBluetoothGatt != null;
    }

    @android.support.annotation.RequiresApi(api = Build.VERSION_CODES.M)
    boolean connect(boolean auto, BluetoothDevice bluetoothDevice, int TRANSPORT) {
        if (mBluetoothGatt != null) {
            mBluetoothGatt.close();
            mBluetoothGatt = null;
            mConnected = false;
        }
        mBluetoothGatt = bluetoothDevice.connectGatt(null, auto, mGattCallback, TRANSPORT);
        if (mBluetoothGatt != null) {
            mBluetoothDevice = bluetoothDevice;
        }
        return mBluetoothGatt != null;
    }

    void addConnectListener(ConnectListener connectListener) {
        mListeners.add(connectListener);
    }

    void addRssiListener(int millisecond, RssiListener rssiListener) {
        mRssiIntervalMilliSecond = millisecond;
        mListeners.add(rssiListener);
        readRssiTimerTask();
    }

    private Timer mTimer;
    private TimerTask mTimerTask;

    private void readRssiTimerTask() {
        stopReadRssi();
        mTimer = null;
        mTimerTask = null;
        mTimer = new Timer();
        mTimerTask = new TimerTask() {
            @Override
            public void run() {
                if (mBluetoothGatt != null) {
                    mBluetoothGatt.readRemoteRssi();
                }
            }
        };
        mTimer.schedule(mTimerTask, 100, mRssiIntervalMilliSecond);
    }

    void stopReadRssi() {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
        if (mTimerTask != null) {
            mTimerTask.cancel();
            mTimerTask = null;
        }
    }

    void disconnect() {
        if (mBluetoothGatt != null) {
            mBluetoothGatt.disconnect();
            mBluetoothDevice = null;
        }
    }

    void close() {
        mListeners.clear();
        mBluetoothDevice = null;
        mBluetoothGatt.close();
        mBluetoothGatt = null;
    }

    boolean removeListener(Listener listener) {
        return mListeners.remove(listener);
    }

    boolean write(BluetoothGattCharacteristic characteristic) {
        final BluetoothGatt gatt = mBluetoothGatt;
        if (checkGattNull(gatt)) {
            return false;
        }
        if (characteristic == null) {

            return false;
        }
        final int properties = characteristic.getProperties();
        if ((properties & (BluetoothGattCharacteristic.PROPERTY_WRITE | BluetoothGattCharacteristic.PROPERTY_WRITE_NO_RESPONSE)) == 0) {
            return false;
        }
        return gatt.writeCharacteristic(characteristic);
    }

    private void read(BluetoothGattCharacteristic characteristic) {
        final BluetoothGatt gatt = mBluetoothGatt;

    }

    private void notification(boolean enable, BluetoothGattCharacteristic characteristic) {

    }

    private void indication(boolean enable, BluetoothGattCharacteristic characteristic) {

    }

    public void clearQueue() {
        mRequestQueue.cancelAll();
    }

    BluetoothDevice getBluetoothDevice() {
        return mBluetoothDevice;
    }

    boolean getConnected() {
        return mConnected;
    }

    private boolean checkGattNull(BluetoothGatt gatt) {
        if (gatt == null) {
            for (Listener listener : mListeners) {
                if (listener instanceof WriteCharacteristicListener) {
                    ((WriteCharacteristicListener) listener).error(
                            new WriteBleException(233, BleException.WRITE_CHARACTERISTIC,
                                    "BluetoothGatt object is null. check connect status or onServicesDiscovered.")
                    );
                }
            }
            mRequestQueue.next();
            return true;
        }
        return false;
    }

    private boolean checkCharacteristicNull(BluetoothGattCharacteristic characteristic) {
        if (characteristic == null) {
            for (Listener listener : mListeners) {
                if (listener instanceof OnLeReadCharacteristicListener) {
                    ((OnLeReadCharacteristicListener) listener).onFailure(
                            new ReadBleException(233, BleException.READ_CHARACTERISTIC,
                                    "characteristic uuid is null.")
                    );
                }
            }
            mRequestQueue.next();
            return true;
        }
        return false;
    }



    private boolean enableQueueDelay;
    private int queueDelayTime;
    private int autoQueueInterval;
    private Handler mHandler = new Handler(Looper.getMainLooper());

    private boolean isAndroidMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }

    private void runOnUiThread(Runnable runnable) {
        if (isAndroidMainThread()) {
            runnable.run();
        } else {
            mHandler.post(runnable);
        }
    }

    private RequestQueue mRequestQueue = new RequestQueue();

    private class RequestQueue {

        private Queue<CommandQueue> mCommandQueue = new LinkedList<>();

        void addRequest(CommandQueue command) {
            int oldSize = mCommandQueue.size();
            mCommandQueue.add(command);
            if (mCommandQueue.size() == 1 && oldSize == 0) {
                startExecutor();
            }
        }

        private void startExecutor() {
            CommandQueue queue = mCommandQueue.peek();
            switch (queue.type) {
                case WRITE:
                    write(queue.getCharacteristic());
                    break;
                case READ:
                    read(queue.getCharacteristic());
                    break;
                case NOTIFY:
                    notification(queue.isEnable(), queue.getCharacteristic());
                    break;
                case INDICATE:
                    indication(queue.isEnable(), queue.getCharacteristic());
                    break;
            }
        }

        void next() {
            if (enableQueueDelay) {
                if (queueDelayTime < 0) {
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            runQueue();
                        }
                    }, autoQueueInterval);
                } else {
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            runQueue();
                        }
                    }, queueDelayTime);
                }
            } else {
                runQueue();
            }
        }

        void runQueue() {
            mCommandQueue.poll();
            if (mCommandQueue != null && mCommandQueue.size() > 0) {
                startExecutor();
            }
        }

        void cancelAll() {
            mCommandQueue.clear();
        }

    }
}
