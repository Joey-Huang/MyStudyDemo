package com.example.myapplication;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ListView lv;
    private TextView tv;
    private List<DeviceInfo> infos;
    private MyAdapter myAdapter;
    private BluetoothAdapter bluetoothAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv = (ListView) findViewById(R.id.lv);
        tv = (TextView) findViewById(R.id.tv);
        Button button = (Button) findViewById(R.id.bt_search);
        button.setOnClickListener(this);

        infos = new ArrayList<DeviceInfo>();
        myAdapter = new MyAdapter();
        lv.setAdapter(myAdapter);
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    @Override
    public void onClick(View v) {
        if (bluetoothAdapter == null) {
            //不具备蓝牙功能
            e("不具备蓝牙功能");
        } else {
            if (!bluetoothAdapter.enable()) {
                e("蓝牙没开");
                Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivity(intent);
            }

            Set<BluetoothDevice> bondedDevices = bluetoothAdapter.getBondedDevices();
            //获取蓝牙设备
            if (bondedDevices.size() > 0) {
                Iterator<BluetoothDevice> iterator = bondedDevices.iterator();
                if (iterator.hasNext()){
                    infos.clear();
                }
                for (; iterator.hasNext(); ) {
                    BluetoothDevice bluetoothDevice = iterator.next();
                    //设备信息
                    String name = bluetoothDevice.getName();
                    String address = bluetoothDevice.getAddress();
                    int bondState = bluetoothDevice.getBondState();
                    int type = bluetoothDevice.getType();
                    e("name=" + name + "\taddress=" + address + "\tbondState=" + bondState + "\ttype=" + type);
                    if (bondState == BluetoothDevice.BOND_BONDED) {
                        e("已经绑定");
                    }
                    DeviceInfo deviceInfo = new DeviceInfo(name, address, bondState, type);
                    infos.add(deviceInfo);
                }
                myAdapter.notifyDataSetChanged();
                tv.setText("一共搜索到"+infos.size()+"个设备");
            }

        }

    }

    private void e(String text) {
        Log.e("调试", text);
    }

    public void openBlue(View view) {
        if (bluetoothAdapter!=null){
            bluetoothAdapter.enable();
        }
    }
    public void closeBlue(View view) {
        if (bluetoothAdapter!=null){
            bluetoothAdapter.disable();
        }
    }
    class DeviceInfo {
        private String name;
        private String address;
        private int bondState;
        private int type;

        public DeviceInfo(String name, String address, int bondState, int type) {
            this.name = name;
            this.address = address;
            this.bondState = bondState;
            this.type = type;
        }

        public DeviceInfo() {
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public int getBondState() {
            return bondState;
        }

        public void setBondState(int bondState) {
            this.bondState = bondState;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }

    private class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return infos.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = View.inflate(MainActivity.this, R.layout.item_list, null);
            TextView tvName = (TextView) view.findViewById(R.id.tv_name);
            TextView tvAddress = (TextView) view.findViewById(R.id.tv_address);
            DeviceInfo deviceInfo = infos.get(position);
            tvName.setText(deviceInfo.getName());
            tvAddress.setText(deviceInfo.getAddress());
            return view;
        }
    }
}
