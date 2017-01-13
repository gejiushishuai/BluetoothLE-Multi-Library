package com.github.qindachang.bluetoothle_multi_library;

import android.bluetooth.BluetoothGattCharacteristic;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.qindachang.library.BleManagerImpl;
import com.github.qindachang.library.BluetoothConfig;
import com.github.qindachang.library.BluetoothLe;

import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = MainActivity.class.getSimpleName();

    public static final UUID UUID_SERVICE = UUID.fromString("");
    public static final UUID UUID_WRITE = UUID.fromString("");
    public static final UUID UUID_READ = UUID.fromString("");

    private BleManagerImpl mBleManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBleManager = BluetoothLe.getDefault();

        BluetoothConfig config = new BluetoothConfig.Builder()
                .enableQueueInterval(true)
                .setQueueIntervalTime(500)
                .build();
        mBleManager.setConfig(config);

        BluetoothGattCharacteristic characteristic = mBleManager.getCharacteristic(UUID_SERVICE, UUID_WRITE);
        characteristic.setValue(new byte[]{1});
        mBleManager.writeCharacteristic(characteristic);

        mBleManager.writeBytesToCharacteristic(new byte[]{1, 2}, UUID_SERVICE, UUID_WRITE);

        mBleManager.readCharacteristic(UUID_SERVICE, UUID_READ);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBleManager.cancelAllTag();
        mBleManager.destroy(TAG);
    }
}
