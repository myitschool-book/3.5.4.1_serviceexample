package ru.samsung.itschool.mdev.serviceexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private TextView tempText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tempText = findViewById(R.id.temp_txt);
        registerReceiver(receiver, new IntentFilter(GisService.CHANNEL));
        Intent intent = new Intent(this, GisService.class);
        startService(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent intent = new Intent(this, GisService.class);
        stopService(intent);
    }


    protected BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                JSONObject json = new JSONObject(intent.getStringExtra(GisService.INFO));
                JSONObject result_temp = json.getJSONObject("main");
                JSONObject result_sun = json.getJSONObject("sys");
                tempText.setText(result_sun.toString()+ " " + result_temp.toString());
            } catch (JSONException e) {
                Toast.makeText(MainActivity.this, "Неверный формат JSON", Toast.LENGTH_LONG).show();
            }
        }
    };
}