package com.github.qindachang.library.conn;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import com.github.qindachang.library.BluetoothConfig;
import com.github.qindachang.library.exception.BleException;
import com.github.qindachang.library.exception.ConnBleException;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.UUID;

/**
 * Created by qindachang on 2017/3/10.
 */

class Command {
    private boolean mConnected;
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


    boolean readCharacteristic(UUID serviceUUID, UUID characteristicUUID) {
        return false;
    }

    void enableIndication(boolean enable, UUID serviceUUID, UUID characteristicUUID) {
    }

    void enableNotification(boolean enable, UUID serviceUUID, UUID characteristicUUID) {
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
        return mBluetoothGatt != null;
    }

    void addConnectListener(ConnectListener connectListener) {
        mListeners.add(connectListener);
    }

    void addRssiListener(int millisecond, RssiListener rssiListener) {
        mListeners.add(rssiListener);
    }

    void disconnect() {

    }

    void close() {
        mListeners.clear();
        mBluetoothDevice = null;
    }

    boolean removeListener(Listener listener) {
        return mListeners.remove(listener);
    }

    boolean write(BluetoothGattCharacteristic characteristic) {
        return false;
    }

    private void read(BluetoothGattCharacteristic characteristic) {

    }

    private void notification(boolean enable, BluetoothGattCharacteristic characteristic) {

    }

    private void indication(boolean enable, BluetoothGattCharacteristic characteristic) {

    }

    BluetoothDevice getBluetoothDevice() {
        return mBluetoothDevice;
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
