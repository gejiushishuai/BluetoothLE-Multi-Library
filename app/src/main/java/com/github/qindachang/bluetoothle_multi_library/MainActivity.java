package com.github.qindachang.bluetoothle_multi_library;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.qindachang.library.BleManagerImpl;
import com.github.qindachang.library.BluetoothConfig;
import com.github.qindachang.library.BluetoothLe;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BluetoothConfig config = new BluetoothConfig.Builder()
                .enableQueueInterval(true)
                .setQueueIntervalTime(500)
                .build();
        BleManagerImpl manager = BluetoothLe.getDefault();
        manager.setConfig(config);



    }
}
