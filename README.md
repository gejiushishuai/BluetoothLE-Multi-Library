BluetoothLE-Multi-Library
============

An Android multi connect more Bluetooth LE device's library.

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

## Download

    dependencies {
      compile 'will be come soon..'
    }



