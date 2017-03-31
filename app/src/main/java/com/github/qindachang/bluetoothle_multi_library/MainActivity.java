package com.github.qindachang.bluetoothle_multi_library;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.github.qindachang.library.BluetoothConfig;
import com.github.qindachang.library.conn.BluetoothLe;
import com.github.qindachang.library.conn.BluetoothLeConnector;
import com.github.qindachang.library.conn.ConnectListener;
import com.github.qindachang.library.conn.RssiListener;
import com.github.qindachang.library.exception.ConnBleException;

import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = MainActivity.class.getSimpleName();

    public static final UUID UUID_SERVICE = UUID.fromString("");
    public static final UUID UUID_WRITE = UUID.fromString("");
    public static final UUID UUID_READ = UUID.fromString("");
    public static final UUID UUID_INDICATION = UUID.fromString("");
    public static final UUID UUID_NOTIFICATION = UUID.fromString("");

    private BluetoothDevice mBluetoothDevice;

    private BluetoothLeConnector connector = BluetoothLe.newConnector();

    private BluetoothGatt mBluetoothGatt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewPager viewPager = (ViewPager) findViewById(R.id.vp_main);

        connector.setConfig(new BluetoothConfig.Builder()
                .enableQueueInterval(true)
                .setQueueIntervalTime(BluetoothConfig.AUTO)
                .build());

        connector.addConnectListener(new ConnectListener() {
            @Override
            public void connecting() {

            }

            @Override
            public void connected() {

            }

            @Override
            public void disconnected() {

            }

            @Override
            public void onServicesDiscovered(BluetoothGatt gatt, int status) {
                mBluetoothGatt = gatt;
            }

            @Override
            public void error(ConnBleException e) {

            }
        });

        connector.connect(true, mBluetoothDevice);
//        connector.connect(true, mBluetoothDevice, BluetoothLeConnector.TRANSPORT_AUTO);
        connector.enableIndication(true,UUID_SERVICE,UUID_INDICATION);
        connector.enableNotification(true, UUID_SERVICE, UUID_NOTIFICATION);

        connector.writeCharacteristic(new byte[]{0x01, 0x02}, UUID_SERVICE, UUID_WRITE);

        BluetoothGattService service = mBluetoothGatt.getService(UUID_SERVICE);
        BluetoothGattCharacteristic characteristic = service.getCharacteristic(UUID_WRITE);
        characteristic.setValue(new byte[]{});
        connector.writeCharacteristic(characteristic);

        connector.readCharacteristic(UUID_SERVICE, UUID_READ);

        connector.disconnect();

        connector.removeListener(mConnectListener);


    }

    private ConnectListener mConnectListener = new ConnectListener() {
        @Override
        public void connecting() {

        }

        @Override
        public void connected() {

        }

        @Override
        public void disconnected() {

        }

        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {

        }

        @Override
        public void error(ConnBleException e) {

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        connector.close();
    }
}
