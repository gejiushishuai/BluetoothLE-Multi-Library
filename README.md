## BluetoothLE-Multi-Library

An Android multi connect more Bluetooth LE device's library.

if you just wanna to connect one Bluetooth-LE device, may be use this library better.
[BluetoothLELibrary](https://github.com/qindachang/BluetoothLELibrary "BluetoothLELibrary")

##Example

    private BleManagerImpl mBleManager;

    //The first you reference this method for get the one BluetoothLE connection object.
    //Flowing with this object, very commands will use this object, such as write/read/indicate/notification.
    //When you wanna connect second bluetooth device, example call mBleManager = BluetoothLe.getDefault(1);
    //The .getDefault(1); will return new connection object, then you can connect second bluetooth device.
    mBleManager = BluetoothLe.getDefault();

    //Config BluetoothLE commands interval time. That commands will be on reference of the queue,
    //than you dosen't need to care about that waiting for previous command successfully or others.
    //Because in library very commands auto by queue.
    //And you can set the queue interval time what ever you want to set. Just like iOS BluetoothLE.
    BluetoothConfig config = new BluetoothConfig.Builder()
            .enableQueueInterval(true)
            .setQueueIntervalTime(500)
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

##Download

    dependencies {
      compile ''
    }



