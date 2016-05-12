package com.example.wifi;

import android.net.wifi.ScanResult;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private WifiAdmin wifiAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        wifiAdmin = WifiAdmin.getInstance(this);
    }

    public void openWifi(View view) {
        wifiAdmin.OpenWifi();
    }

    public void closeWifi(View view) {
        wifiAdmin.closeWifi();
    }

    public void searchWifi(View view) {
        wifiAdmin.startScan();
        wifiAdmin.setWifiList();
        List<ScanResult> wifiList = wifiAdmin.getWifiList();
        e("扫描结果="+wifiAdmin.lookUpScan().toString());

        if (wifiList==null){
            e("未搜索到wifi");
            return;
        }
        for (ScanResult result:wifiList){
            String bssid = result.BSSID;
            String capabilities = result.capabilities;
            CharSequence operatorFriendlyName = result.operatorFriendlyName;
            String ssid = result.SSID;
            CharSequence venueName = result.venueName;
            e("bssid="+bssid+"\tcapabilities="+capabilities+"\toperatorFriendlyName="+operatorFriendlyName+"\tssid="+ssid+"\tvenueName="+venueName);
            e("toString="+result.toString());
        }
    }

    private void e(String text) {
        Log.e("调试",text);
    }
}
