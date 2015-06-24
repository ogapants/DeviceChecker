package com.oga.devicechecker;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView mListView = (ListView) findViewById(R.id.list_view);

        list = createList();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        mListView.setAdapter(arrayAdapter);
    }

    private List<String> createList() {
        List<String> list = new ArrayList<>();
        list.add("BOARD:" + Build.BOARD);
        list.add("BOOTLOADER:" + Build.BOOTLOADER);
        list.add("BRAND:" + Build.BRAND);
        list.add("CPU_ABI:" + Build.CPU_ABI);
        list.add("CPU_ABI2:" + Build.CPU_ABI2);
        list.add("DEVICE:" + Build.DEVICE);
        list.add("DISPLAY:" + Build.DISPLAY);
        list.add("FINGERPRINT:" + Build.FINGERPRINT);
        list.add("HARDWARE:" + Build.HARDWARE);
        list.add("HOST:" + Build.HOST);
        list.add("ID:" + Build.ID);
        list.add("MANUFACTURER:" + Build.MANUFACTURER);
        list.add("MODEL:" + Build.MODEL);
        list.add("PRODUCT:" + Build.PRODUCT);
        list.add("RADIO:" + Build.RADIO);
        list.add("TAGS:" + Build.TAGS);
        list.add("TIME:" + Build.TIME);
        list.add("TYPE:" + Build.TYPE);
        list.add("UNKNOWN:" + Build.UNKNOWN);
        list.add("USER:" + Build.USER);
        list.add("VERSION.CODENAME:" + Build.VERSION.CODENAME);
        list.add("VERSION.INCREMENTAL:" + Build.VERSION.INCREMENTAL);
        list.add("VERSION.RELEASE:" + Build.VERSION.RELEASE);
        list.add("VERSION.SDK:" + Build.VERSION.SDK);
        list.add("VERSION.SDK_INT:" + Build.VERSION.SDK_INT);

        // ANDROID_ID
        String udid = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        list.add("ANDROID_ID:" + udid);

        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        // IMEI
        String imei = tm.getDeviceId();
        list.add("getDeviceId:" + imei);

        // IMSI
        String imsi = tm.getSubscriberId();
        list.add("getSubscriberId:" + imsi);

        // Macアドレス
        WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        String mac = wifi.getConnectionInfo().getMacAddress();
        list.add("mac:" + mac);

        //todo more add
        return list;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_send) {
            String msg = null;
            for (String s : list) {
                msg += s + "\n";
            }
            Intent intent = createMailIntent("Android", msg);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private static Intent createMailIntent(String subject, String text) {
        final String address = "";// FIXME
        Intent mail = new Intent();
        mail.setAction(Intent.ACTION_SENDTO);
        mail.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mail.setData(Uri.parse("mailto:" + address));
        mail.putExtra(Intent.EXTRA_SUBJECT, subject);
        mail.putExtra(Intent.EXTRA_TEXT, text);
        return mail;
    }
}
