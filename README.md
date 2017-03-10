BluetoothLE-Multi-Library
============

An Android multi connect more Bluetooth LE device's library.

If you just wanna to connect one Bluetooth-LE device, may be use this library better.
[BluetoothLELibrary](https://github.com/qindachang/BluetoothLELibrary "BluetoothLELibrary")

##Example

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
        connector.enableIndication(true,UUID_SERVICE,UUID_INDICATION);
        connector.enableNotification(true, UUID_SERVICE, UUID_NOTIFICATION);

        connector.writeCharacteristic(new byte[]{0x01, 0x02}, UUID_SERVICE, UUID_WRITE);

        BluetoothGattService service = mBluetoothGatt.getService(UUID_SERVICE);
        BluetoothGattCharacteristic characteristic = service.getCharacteristic(UUID_WRITE);
        characteristic.setValue(byte[] value);
        characteristic.setValue(int value, int formatType, int offset);
        characteristic.setValue(int mantissa, int exponent, int formatType, int offset);
        characteristic.setValue(String value);

        connector.readCharacteristic(UUID_SERVICE, UUID_READ);

        connector.disconnect();

```

##Listener

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

##Download

    dependencies {
      compile ''
    }



