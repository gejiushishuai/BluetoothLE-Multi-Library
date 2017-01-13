package com.github.qindachang.bluetoothle_multi_library;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.qindachang.library.BleManagerImpl;
import com.github.qindachang.library.BluetoothConfig;
import com.github.qindachang.library.BluetoothLe;
import com.github.qindachang.library.OnLeConnectListener;
import com.github.qindachang.library.OnLeIndicationListener;
import com.github.qindachang.library.OnLeNotificationListener;
import com.github.qindachang.library.OnLeReadCharacteristicListener;
import com.github.qindachang.library.OnLeReadRssiListener;
import com.github.qindachang.library.OnLeScanListener;
import com.github.qindachang.library.OnLeWriteCharacteristicListener;
import com.github.qindachang.library.exception.BleException;
import com.github.qindachang.library.exception.ConnBleException;
import com.github.qindachang.library.exception.ReadBleException;
import com.github.qindachang.library.exception.ScanBleException;
import com.github.qindachang.library.exception.WriteBleException;
import com.github.qindachang.library.scanner.ScanRecord;
import com.github.qindachang.library.scanner.ScanResult;

import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = MainActivity.class.getSimpleName();

    public static final UUID UUID_SERVICE = UUID.fromString("");
    public static final UUID UUID_WRITE = UUID.fromString("");
    public static final UUID UUID_READ = UUID.fromString("");
    public static final UUID UUID_NOTIFICATION_1 = UUID.fromString("");
    public static final UUID UUID_NOTIFICATION_2 = UUID.fromString("");
    public static final UUID UUID_INDICATE_1 = UUID.fromString("");

    private BleManagerImpl mBleManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //The first you reference this method for get the one BluetoothLE connection object.
        //Flowing with this object, very commands will use this object, such as write/read/indicate/notification.
        //When you wanna connect second bluetooth device, example call mBleManager = BluetoothLe.getDefault(1);
        //The .getDefault(1); will return new connection object, then you can connect second bluetooth device.
        mBleManager = BluetoothLe.getDefault();

        //Config BluetoothLE commands interval time. That commands will be on reference of the queue,
        //than you dosen't need to care about that waiting for previous command successfully or others.
        //Because in library every commands auto operation by queue.
        //And you can set the queue interval time what ever you want to set. Just like iOS BluetoothLE.
        BluetoothConfig config = new BluetoothConfig.Builder()
                .enableQueueInterval(true)
                .setQueueIntervalTime(BluetoothConfig.AUTO)
                .build();
        mBleManager.setConfig(config);

        //Write method have tow type, one is detail about set characteristic, the other one is just set bytes.
        BluetoothGattCharacteristic characteristic = mBleManager.getCharacteristic(UUID_SERVICE, UUID_WRITE);
        characteristic.setValue(new byte[]{1});
        mBleManager.writeCharacteristic(characteristic);

        mBleManager.writeBytesToCharacteristic(new byte[]{1, 2}, UUID_SERVICE, UUID_WRITE);

        //Read characteristic from service
        mBleManager.readCharacteristic(UUID_SERVICE, UUID_READ);

        //Enable true or false indicates
        mBleManager.enableIndicates(true, UUID_SERVICE, new UUID[]{UUID_INDICATE_1});

        //Enable true or false notifications
        mBleManager.enableNotifications(true, UUID_SERVICE, new UUID[]{UUID_NOTIFICATION_1, UUID_NOTIFICATION_2});



        //Every Bluetooth-LE commands status will be callback in here. Flowing listener:
        mBleManager.setOnScanListener(TAG, new OnLeScanListener() {
            @Override
            public void onScanResult(BluetoothDevice bluetoothDevice, int rssi, ScanRecord scanRecord) {

            }

            @Override
            public void onBatchScanResults(List<ScanResult> results) {

            }

            @Override
            public void onScanCompleted() {

            }

            @Override
            public void onScanFailed(ScanBleException e) {

            }
        });

        mBleManager.setOnConnectListener(TAG, new OnLeConnectListener() {
            @Override
            public void onDeviceConnecting() {

            }

            @Override
            public void onDeviceConnected() {
                //When bluetooth connected . This callback will be called.
                //You can change your App's UI form here, in order to notice bluetooth connection status.
            }

            @Override
            public void onDeviceDisconnected() {
                //When bluetooth disconnected . This callback will be called.
                //You can change your App's UI form here, in order to notice bluetooth connection status.
            }

            @Override
            public void onServicesDiscovered(BluetoothGatt gatt) {
                //After bluetooth connected, the library will auto discover service.
                //When service be fond, This method will be called.
                //Then you can do bluetooth operations. Such as write/read et..
            }

            @Override
            public void onDeviceConnectFail(ConnBleException e) {

            }
        });

        mBleManager.setOnNotificationListener(TAG, new OnLeNotificationListener() {
            @Override
            public void onSuccess(BluetoothGattCharacteristic characteristic) {

            }

            @Override
            public void onFailed(BleException e) {

            }
        });

        mBleManager.setOnIndicateListener(TAG, new OnLeIndicationListener() {
            @Override
            public void onSuccess(BluetoothGattCharacteristic characteristic) {

            }

            @Override
            public void onFailed(BleException e) {

            }
        });

        mBleManager.setOnWriteCharacteristicListener(TAG, new OnLeWriteCharacteristicListener() {
            @Override
            public void onSuccess(BluetoothGattCharacteristic characteristic) {

            }

            @Override
            public void onFailed(WriteBleException e) {

            }
        });

        mBleManager.setOnReadCharacteristicListener(TAG, new OnLeReadCharacteristicListener() {
            @Override
            public void onSuccess(BluetoothGattCharacteristic characteristic) {

            }

            @Override
            public void onFailure(ReadBleException e) {

            }
        });

        mBleManager.setOnReadRssiListener(TAG, new OnLeReadRssiListener() {
            @Override
            public void onSuccess(int rssi, int cm) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //must call this method in order to avoid leaks.
        mBleManager.destroy(TAG);
    }
}
