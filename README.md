BluetoothLE-Multi-Library
============

一个能够连接多台蓝牙设备的库，它可以作为client端，也可以为server端。支持主机／从机，外围设备连接。

If you just wanna to connect one Bluetooth-LE device, may be use this library better.
[BluetoothLELibrary](https://github.com/qindachang/BluetoothLELibrary "BluetoothLELibrary")

## Example

### scan

```java
BluetoothLeScannerCompat scannerCompat = BluetoothLeScannerCompat.getScanner();
ScanSettings scanSettings = new ScanSettings.Builder()
    .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
    .setReportDelay(int reportDelayMillis) //0 or above >0
    .setUseHardwareBatchingIfSupported(false)
    .build();

List<ScanFilter> filters = new ArrayList<>();

ScanFilter builder = new ScanFilter.Builder().setDeviceName(deviceName).build();
filters.add(builder);

ScanFilter builder = new ScanFilter.Builder().setDeviceAddress(deviceAddress).build();
filters.add(builder);

ScanFilter builder = new ScanFilter.Builder()
                    .setServiceUuid(ParcelUuid.fromString(serviceUUID.toString())).build();
filters.add(builder);

scannerCompat.startScan(filters, scanSettings, scanCallback);

```

```java
private ScanCallback scanCallback = new ScanCallback() {
        @Override
        public void onScanResult(final int callbackType, final ScanResult result) {

        }

        @Override
        public void onBatchScanResults(final List<ScanResult> results) {

        }

        @Override
        public void onScanFailed(final int errorCode) {

        }
};
```



### connection

```java
//When you create new connection of bluetooth le , firstly newConnector object. In order to command following operation.
private BluetoothLeConnector connector = BluetoothLe.newConnector();
private BluetoothGatt mBluetoothGatt;

//Set operation config about interval time about bluetooth command.
        connector.setConfig(new BluetoothConfig.Builder()
                .enableQueueInterval(true)
                .setQueueIntervalTime(BluetoothConfig.AUTO)
                .build());

        connector.connect(true, mBluetoothDevice);
//        connector.connect(true, mBluetoothDevice, BluetoothLeConnector.TRANSPORT_AUTO);
        connector.enableIndication(true,UUID_SERVICE,UUID_INDICATION);
        connector.enableNotification(true, UUID_SERVICE, UUID_NOTIFICATION);

        connector.writeCharacteristic(new byte[]{0x01, 0x02}, UUID_SERVICE, UUID_WRITE);

        BluetoothGattService service = mBluetoothGatt.getService(UUID_SERVICE);
        BluetoothGattCharacteristic characteristic = service.getCharacteristic(UUID_WRITE);
        characteristic.setValue(byte[] value);
        characteristic.setValue(int value, int formatType, int offset);
        characteristic.setValue(int mantissa, int exponent, int formatType, int offset);
        characteristic.setValue(String value);
        connector.writeCharacteristic(characteristic);

        connector.readCharacteristic(UUID_SERVICE, UUID_READ);

        connector.disconnect();

```

## Listener

```java
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
```
More listener such as :

```java
mBleManager.addConnectListener(...)
mBleManager.addNotificationListener(...)
mBleManager.addIndicationListener(...)
mBleManager.addWriteCharacteristicListener(...)
mBleManager.addReadCharacteristicListener(...)
mBleManager.addRssiListener(...)
```


Remove listener:

```java
connector.removeListener(mConnectListener);
```

## GattServer

### Advertising

```java
private GattServer mGattServer = new GattServer();

mGattServer.startAdvertising(UUID.fromString("0000fff0-0000-1000-8000-00805f9b34fb"));

mGattServer.stopAdvertising();
```

### Server

1. startServer

```java
mGattServer.startServer(context);
```

2. closeServer

```java
mGattServer.closeServer();
```
3. addService

```java
List<ServiceProfile> list = new ArrayList<>();

ServiceProfile profile = new ServiceProfile();
profile.setCharacteristicUuid(UUID.fromString("0000fff3-0000-1000-8000-00805f9b34fb"));
profile.setCharacteristicProperties(BluetoothGattCharacteristic.PROPERTY_WRITE);
profile.setCharacteristicPermission(BluetoothGattCharacteristic.PERMISSION_WRITE);
profile.setDescriptorUuid(GattServer.CLIENT_CHARACTERISTIC_CONFIG_DESCRIPTOR_UUID);
profile.setDescriptorPermission(BluetoothGattDescriptor.PERMISSION_READ);
profile.setDescriptorValue(new byte[]{0});
list.add(profile);

ServiceProfile profile1 = new ServiceProfile();
profile1.setCharacteristicUuid(UUID.fromString("0000fff2-0000-1000-8000-00805f9b34fb"));
profile1.setCharacteristicProperties(BluetoothGattCharacteristic.PROPERTY_READ);
profile1.setCharacteristicPermission(BluetoothGattCharacteristic.PERMISSION_READ);
profile1.setDescriptorUuid(GattServer.CLIENT_CHARACTERISTIC_CONFIG_DESCRIPTOR_UUID);
profile1.setDescriptorPermission(BluetoothGattDescriptor.PERMISSION_READ);
profile1.setDescriptorValue(new byte[]{1});
list.add(profile1);

ServiceProfile profile2 = new ServiceProfile();
profile2.setCharacteristicUuid(UUID.fromString("0000fff1-0000-1000-8000-00805f9b34fb"));
profile2.setCharacteristicProperties(BluetoothGattCharacteristic.PROPERTY_NOTIFY);
profile2.setCharacteristicPermission(BluetoothGattCharacteristic.PERMISSION_READ);
profile2.setDescriptorUuid(GattServer.CLIENT_CHARACTERISTIC_CONFIG_DESCRIPTOR_UUID);
profile2.setDescriptorPermission(BluetoothGattDescriptor.PERMISSION_WRITE);
profile2.setDescriptorValue(new byte[]{1});
list.add(profile2);

final ServiceSettings serviceSettings = new ServiceSettings.Builder()
        .setServiceUuid(UUID.fromString("0000fff0-0000-1000-8000-00805f9b34fb"))
        .setServiceType(BluetoothGattService.SERVICE_TYPE_PRIMARY)
        .addServiceProfiles(list)
        .build();

mGattServer.addService(serviceSettings);
```


### Listener

        mGattServer.setOnAdvertiseListener(new OnAdvertiseListener() {
            @Override
            public void onStartSuccess(AdvertiseSettings settingsInEffect) {
                setContentText("开启广播  成功，uuid：0000fff0-0000-1000-8000-00805f9b34fb");
            }

            @Override
            public void onStartFailure(int errorCode) {
                setContentText("开启广播  失败，uuid：0000fff0-0000-1000-8000-00805f9b34fb");
            }

            @Override
            public void onStopAdvertising() {
                setContentText("停止广播，uuid：0000fff0-0000-1000-8000-00805f9b34fb");
            }
        });

        mGattServer.setOnServiceAddedListener(new OnServiceAddedListener() {
            @Override
            public void onSuccess(BluetoothGattService service) {
                setContentText("添加服务成功！");
            }

            @Override
            public void onFail(BluetoothGattService service) {
                setContentText("添加服务失败");
            }
        });

        mGattServer.setOnConnectionStateChangeListener(new OnConnectionStateChangeListener() {
            @Override
            public void onChange(BluetoothDevice device, int status, int newState) {

            }

            @Override
            public void onConnected(BluetoothDevice device) {
                setContentText("连接上一台设备 ：{ name = " + device.getName() + ", address = " + device.getAddress() + "}");
                mBluetoothDevice = device;
            }

            @Override
            public void onDisconnected(BluetoothDevice device) {
                setContentText("设备断开连接 ：{ name = " + device.getName() + ", address = " + device.getAddress() + "}");
            }
        });

        mGattServer.setOnWriteRequestListener(new OnWriteRequestListener() {
            @Override
            public void onCharacteristicWritten(BluetoothDevice device, BluetoothGattCharacteristic characteristic, byte[] value) {
                setContentText("设备写入特征请求 ： device = " + device.getAddress() + ", characteristic uuid = " + characteristic.getUuid().toString() + ", value = " + Arrays.toString(value));
            }

            @Override
            public void onDescriptorWritten(BluetoothDevice device, BluetoothGattDescriptor descriptor, byte[] value) {
                setContentText("设备写入描述请求 ： device = " + device.getAddress() + ", descriptor uuid = " + descriptor.getUuid().toString() + ", value = " + Arrays.toString(value));
            }
        });


## Download

    dependencies {
      compile 'will be come soon..'
    }



