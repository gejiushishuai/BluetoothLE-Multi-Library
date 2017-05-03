#BluetoothLE-Multi-Library


一个能够连接多台蓝牙设备的库，它可以作为client端，也可以为server端。支持主机／从机，外围设备连接。    
在发送消息时，它内部支持队列控制，避免因蓝牙间隔出现操作失败的情况。


## 开始使用

### 1. 主机client

### 扫描

```java
BluetoothLeScannerCompat scannerCompat = BluetoothLeScannerCompat.getScanner();
ScanSettings scanSettings = new ScanSettings.Builder()
    .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
    .setReportDelay(int reportDelayMillis) //0 or above >0
    .setUseHardwareBatchingIfSupported(false)
    .build();
    
//设置过滤扫描
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

扫描回调

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



### 连接

```java
//创建连接的一个对象，后续将使用该对象来访问操作
private BluetoothLeConnector connector = BluetoothLe.newConnector();
private BluetoothGatt mBluetoothGatt;

        //配置连接对象
        connector.setConfig(new BluetoothConfig.Builder()
                .enableQueueInterval(true)//开启操作时间间隔
                .setQueueIntervalTime(BluetoothConfig.AUTO)//单位ms，这里为自动
                .build());
        //连接蓝牙
        connector.connect(true, mBluetoothDevice);
        connector.connect(true, mBluetoothDevice, BluetoothLeConnector.TRANSPORT_AUTO);//最后一个参数设置连接通道
        //开启indicate通知
        connector.enableIndication(true,UUID_SERVICE,UUID_INDICATION);
        //开启notify通知
        connector.enableNotification(true, UUID_SERVICE, UUID_NOTIFICATION);
        //写数据
        connector.writeCharacteristic(new byte[]{0x01, 0x02}, UUID_SERVICE, UUID_WRITE);
        //自定义写数据
        BluetoothGattService service = mBluetoothGatt.getService(UUID_SERVICE);
        BluetoothGattCharacteristic characteristic = service.getCharacteristic(UUID_WRITE);
        characteristic.setValue(byte[] value);
        characteristic.setValue(int value, int formatType, int offset);
        characteristic.setValue(int mantissa, int exponent, int formatType, int offset);
        characteristic.setValue(String value);
        connector.writeCharacteristic(characteristic);
        //读数据
        connector.readCharacteristic(UUID_SERVICE, UUID_READ);
        //断开连接
        connector.disconnect();
        //关闭gatt
        connector.close();

```

### 回调监听 

```java
//连接状态监听
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
connector.addConnectListener(mConnectListener);
```
更多的回调监听如下:

```java
mBleManager.addConnectListener(...)
mBleManager.addNotificationListener(...)
mBleManager.addIndicationListener(...)
mBleManager.addWriteCharacteristicListener(...)
mBleManager.addReadCharacteristicListener(...)
mBleManager.addRssiListener(...)
```


移除回调监听（好的程序员要懂避免内存泄漏）:

```java
connector.removeListener(mConnectListener);
```

---

### 2. 从机Server

### 广播

```java
private GattServer mGattServer = new GattServer();

mGattServer.startAdvertising(UUID.fromString("0000fff0-0000-1000-8000-00805f9b34fb"));//该uuid可提供给主机client过滤扫描

mGattServer.stopAdvertising();
```

### 伺服器server

##### 1. 启动startServer


```java
mGattServer.startServer(context);
```


##### 2. 关闭closeServer



```java
mGattServer.closeServer();
```

##### 3. 添加服务addService



```java
List<ServiceProfile> list = new ArrayList<>();

//设置一个写的特征
ServiceProfile profile = new ServiceProfile();
profile.setCharacteristicUuid(UUID.fromString("0000fff3-0000-1000-8000-00805f9b34fb"));
profile.setCharacteristicProperties(BluetoothGattCharacteristic.PROPERTY_WRITE);
profile.setCharacteristicPermission(BluetoothGattCharacteristic.PERMISSION_WRITE);
profile.setDescriptorUuid(GattServer.CLIENT_CHARACTERISTIC_CONFIG_DESCRIPTOR_UUID);
profile.setDescriptorPermission(BluetoothGattDescriptor.PERMISSION_READ);
profile.setDescriptorValue(new byte[]{0});
list.add(profile);

//设置一个读的特征
ServiceProfile profile1 = new ServiceProfile();
profile1.setCharacteristicUuid(UUID.fromString("0000fff2-0000-1000-8000-00805f9b34fb"));
profile1.setCharacteristicProperties(BluetoothGattCharacteristic.PROPERTY_READ);
profile1.setCharacteristicPermission(BluetoothGattCharacteristic.PERMISSION_READ);
profile1.setDescriptorUuid(GattServer.CLIENT_CHARACTERISTIC_CONFIG_DESCRIPTOR_UUID);
profile1.setDescriptorPermission(BluetoothGattDescriptor.PERMISSION_READ);
profile1.setDescriptorValue(new byte[]{1});
list.add(profile1);

//设置一个notify通知
ServiceProfile profile2 = new ServiceProfile();
profile2.setCharacteristicUuid(UUID.fromString("0000fff1-0000-1000-8000-00805f9b34fb"));
profile2.setCharacteristicProperties(BluetoothGattCharacteristic.PROPERTY_NOTIFY);
profile2.setCharacteristicPermission(BluetoothGattCharacteristic.PERMISSION_READ);
profile2.setDescriptorUuid(GattServer.CLIENT_CHARACTERISTIC_CONFIG_DESCRIPTOR_UUID);
profile2.setDescriptorPermission(BluetoothGattDescriptor.PERMISSION_WRITE);
profile2.setDescriptorValue(new byte[]{1});
list.add(profile2);

final ServiceSettings serviceSettings = new ServiceSettings.Builder()
        .setServiceUuid(UUID.fromString("0000fff0-0000-1000-8000-00805f9b34fb"))//服务uuid
        .setServiceType(BluetoothGattService.SERVICE_TYPE_PRIMARY)
        .addServiceProfiles(list)//上述设置添加到该服务里
        .build();

mGattServer.addService(serviceSettings);
```


### 回调监听

```java
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

```
    
---        
   

## Download

    dependencies {
      compile 'will be come soon..'
    }



