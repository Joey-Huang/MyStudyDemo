# Android学习之蓝牙
### 实现步骤
* 权限	<uses-permission android:name="android.permission.BLUETOOTH" />
* 获取蓝牙适配器       BluetoothAdapter.getDefaultAdapter()
* 判断蓝牙是否打开      adapter.isEnabled()
	* 如果没有打开，则弹出一个打开蓝牙的 Activity
	* Intent intent=new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
* 遍历搜索到的蓝牙列表		Set<BluetoothDevice> devices = adapter.getBondedDevices();
	* BluetoothDevice常用方法
	* 端口名		getName()
	* 串口号		getAddress()
	* 绑定状态	getBondState();
		* BOND_NONE		10
		* BOND_BONDING	11
		* BOND_BONDED	12	
	* 蓝牙版本	getType()
		* DEVICE_TYPE_CLASSIC	
		* DEVICE_TYPE_DUAL
		* DEVICE_TYPE_LE